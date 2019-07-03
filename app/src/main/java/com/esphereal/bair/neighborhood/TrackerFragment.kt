package com.esphereal.bair.funloot

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton
import com.esphereal.bair.funloot.retrofit.TrainBody
import com.esphereal.bair.funloot.util.PrefUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.DateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TrackerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TrackerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackerFragment : Fragment(), OnMapReadyCallback {
    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        private val TAG = TrackerFragment::class.java.simpleName as String
        //private val mDohsun = LatLng(62.038649, 129.712501) // dohsun
        //private val mDohsun = LatLng(62.032549, 129.750124) //itpark
        private val mDohsun = LatLng(62.026391, 129.730845) //dp

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrackerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): TrackerFragment {
            val fragment = TrackerFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    enum class TrainingState {
        Loading,
        Running,
        Paused,
        OnMap,
        OnObject
    }

    private var trainingState: TrainingState = TrainingState.Loading
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mMap: GoogleMap? = null
    private var mZoomLevel = 15.0f

    private var mListener: OnFragmentInteractionListener? = null
    private var mProgessBar: ProgressBar? = null
    private var mMapFragment: Fragment? = null
    private var mStartButton: Button? = null
    private var mEndButton: Button? = null
    private var mCountDownTimerTextView: TextView? = null
    private var mPauseTimerTextView: TextView? = null
    private var mContainerPauseTimer: ConstraintLayout? = null


    private var mFragmentManager: FragmentManager? = null

    private var mUserMarker: Marker? = null
    private var mCircle: Circle? = null

    private var startedStatus: Boolean = false

    private var lastLocation: Boolean = false


    //new
    private var secondsRemaining: Long = 0
    private var pauseSecondsRemaining: Long = 0
    private var locationInited: Boolean = false
    private var currentLocation: Location? = null
    private var mGpslocationListener: LocationListener? = null
    private var mLocationManager: LocationManager? = null
    private lateinit var timer: CountDownTimer
    private lateinit var pauseTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tracker, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map_tracker) as SupportMapFragment
        mFragmentManager = mapFragment.fragmentManager

        mapFragment.getMapAsync((this))

        mProgessBar = view.findViewById(R.id.progressBar_tracker)
        mStartButton = view.findViewById(R.id.button_start_tracker)
        mEndButton = view.findViewById(R.id.button_end_tracker)
        mCountDownTimerTextView = view.findViewById(R.id.textView_timer_tracker)
        mPauseTimerTextView = view.findViewById(R.id.textView_pauseTimer_tracker)
        mContainerPauseTimer = view.findViewById(R.id.container_pauseTimer_tracker)
        mMapFragment = mapFragment
        changeUi(TrainingState.Loading)

        mStartButton!!.setOnClickListener {
            Log.d(TAG, "onClick start")
            startTraining()


        }
        mEndButton!!.setOnClickListener {
            Log.d(TAG, "onStop")

            pauseTraining()
        }
        view.findViewById<Button>(R.id.test_button_tracker)!!.setOnClickListener {


            val idToken = PrefUtil.getIdToken(activity!! as Context)

            RetrofitSingleton.getInstance().GetApi().postTrain(idToken, createTrainBody())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        Log.d(TAG, result.toString())


                    }
        }

        return view

    }

    override fun onMapReady(map: GoogleMap?) {
        Log.d(TAG, "map ready")
        mMap = map
        //map!!.addMarker(MarkerOptions().position(youHere).title("ты тут"))

        mCircle = mMap!!.addCircle(CircleOptions()
                .center(mDohsun)
                .radius(100.0)
                .strokeColor(Color.parseColor("#2271cce7"))
                .fillColor(Color.parseColor("#2271cce7")));

        mMap!!.setMaxZoomPreference(mZoomLevel)
        mMap!!.setMinZoomPreference(mZoomLevel)
        mMap!!.uiSettings.isScrollGesturesEnabled = true

        // map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(youHere, zoomLevel))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    override fun onResume() {
        Log.d(TAG, "onResume of " + TAG)
        super.onResume()
    }

    // called when the fragment is visible
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            Log.d(TAG, TAG + " visible")

            if (initLocation())
                (firstInit())
            /*   TrackerSingleton.getInstance()?.Init(activity as Context, mOnLocationChangeCallback)!!
                       .subscribeOn(AndroidSchedulers.mainThread()).subscribe(fun(result) {
                           Log.d(TAG, "tracker locationInited")
                           if (result) {
                               firstInit()
                           }
                       }, fun(error) {
                           Log.d(TAG, "error tracker init: " + error.message)
                       })*/


        } else {
            Log.d(TAG, TAG + " not visible")
        }
    }

    private fun pauseTraining() {
        pauseTimer()
        changeUi(TrainingState.Paused)
    }


    private fun startTraining() {
        Log.d(TAG, "startTraining")

        startTimer()
        startedStatus = true
        changeUi(TrainingState.Running)

    }

    private fun onObject() {
        changeUi(TrainingState.OnObject)
    }

    private fun onMap() {
        changeUi(TrainingState.OnMap)

    }

    private fun stopTraining() {
        startedStatus = false
        stopTimer()
        initTimer()
        if (checkLocation())
            changeUi(TrainingState.OnObject)
        else
            changeUi(TrainingState.OnMap)
    }

    private fun finishTraining() {
        onTimerFinished()
        val idToken = PrefUtil.getIdToken(activity!! as Context)

        RetrofitSingleton.getInstance().GetApi().postTrain(idToken, createTrainBody())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    Log.d(TAG, result.toString())


                }
    }

    private fun updateCountdownTimerUi() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        mCountDownTimerTextView!!.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
    }

    private fun updateCountdownPauseTimerUi() {
        val minutesUntilFinished = pauseSecondsRemaining / 60
        val secondsInMinuteUntilFinished = pauseSecondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        mPauseTimerTextView!!.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
    }

    private fun initTimer() {
        secondsRemaining = 60 * 60


    }

    private fun startTimer() {
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() {
                finishTraining()
            }

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownTimerUi()
            }
        }
        timer.start()
    }

    private fun pauseTimer() {
        timer.cancel()
        pauseSecondsRemaining = 60 * 5
        pauseTimer = object : CountDownTimer(pauseSecondsRemaining * 1000, 1000) {
            override fun onFinish() {
                stopTraining()
            }

            override fun onTick(millisUntilFinished: Long) {
                pauseSecondsRemaining = millisUntilFinished / 1000
                updateCountdownPauseTimerUi()
            }
        }.start()
    }

    private fun stopTimer() {
        timer.cancel()
    }

    private fun onTimerFinished() {
    }

    private fun locationChange(location: Location) {
        currentLocation = location
        val loc: LatLng = LatLng(location.latitude, location.longitude)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(loc))
        if (mUserMarker == null)
            mUserMarker = mMap!!.addMarker(MarkerOptions().title("Вы здесь").position(loc))
        else
            mUserMarker!!.position = loc

        if (checkLocation() != lastLocation) {
            when (trainingState) {
                TrainingState.Running -> pauseTraining()
                TrainingState.Paused -> startTraining()
                TrainingState.OnMap -> onObject()
                TrainingState.OnObject -> onMap()
                TrainingState.Loading -> TODO()
            }
        }
        lastLocation = checkLocation()
    }

    private fun initLocation(): Boolean {
        if (locationInited)
            return true
        val permission1 = ContextCompat.checkSelfPermission(activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION)
        val permission2 = ContextCompat.checkSelfPermission(activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    1)
            return false
        }
        mLocationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mGpslocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                //Log.d(TAG, location.toString())
                locationChange(location)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                Log.d(TAG, "onStatusChanged" + provider)

            }

            override fun onProviderEnabled(provider: String) {
                Log.d(TAG, "onProviderEnabled" + provider)
            }

            override fun onProviderDisabled(provider: String) {
                Log.d(TAG, "onProviderDisabled" + provider)
            }
        }
        mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                mGpslocationListener)
        mLocationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0f,
                mGpslocationListener)


        locationInited = true
        return true
    }

    private fun checkLocation(): Boolean {
        val distance = FloatArray(1)
        Location.distanceBetween(mUserMarker!!.position.latitude, mUserMarker!!.position.longitude,
                mCircle!!.center.latitude, mCircle!!.center.longitude, distance)

        // Log.d(TAG, "distance " + distance[0])
        //Log.d(TAG, "radius " + mCircle!!.radius)
        if (distance[0] < mCircle!!.radius) {
            //Log.d(TAG, "in Dohsun")
            return true
        } else {
            //Log.d(TAG, "out Dohsun")
            return false
        }

    }


    private fun createTrainBody(): TrainBody {
        val trainBody: TrainBody = TrainBody()
        trainBody.objectId = "dohsun"
        val calendar: Calendar = Calendar.getInstance()
        val currentDate: String = DateFormat.getDateInstance().format(calendar.time)

        trainBody.date = currentDate
        return trainBody
    }

    private fun firstInit() {
        initTimer()
        changeUi(TrainingState.OnMap)


    }

    private fun changeUi(state: TrainingState) {
        when (state) {
            TrainingState.Loading -> {
                mFragmentManager!!.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .hide(mMapFragment!!)
                        .commit()
                mStartButton!!.visibility = View.GONE
                mProgessBar!!.visibility = View.VISIBLE
                mEndButton!!.visibility = View.GONE
                mCountDownTimerTextView!!.visibility = View.GONE
                mContainerPauseTimer!!.visibility = View.GONE
                //mMapFragment!!.
            }
            TrainingState.Running -> {
                mFragmentManager!!.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .hide(mMapFragment!!)
                        .commit()
                mStartButton!!.visibility = View.GONE
                mProgessBar!!.visibility = View.GONE
                mEndButton!!.visibility = View.GONE
                mCountDownTimerTextView!!.visibility = View.VISIBLE
                mContainerPauseTimer!!.visibility = View.GONE


            }
            TrainingState.OnMap -> {
                mFragmentManager!!.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .show(mMapFragment!!)
                        .commit()
                mEndButton!!.visibility = View.GONE

                mStartButton!!.visibility = View.GONE
                mProgessBar!!.visibility = View.GONE
                mCountDownTimerTextView!!.visibility = View.GONE
                mContainerPauseTimer!!.visibility = View.GONE


            }
            TrainingState.OnObject -> {
                mProgessBar!!.visibility = View.GONE
                mFragmentManager!!.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .show(mMapFragment!!)
                        .commit()

                mEndButton!!.visibility = View.GONE
                mStartButton!!.visibility = View.VISIBLE
                mCountDownTimerTextView!!.visibility = View.GONE
                mContainerPauseTimer!!.visibility = View.GONE


            }
            TrainingState.Paused -> {
                mFragmentManager!!.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .show(mMapFragment!!)
                        .commit()
                mEndButton!!.visibility = View.GONE
                mStartButton!!.visibility = View.GONE
                mCountDownTimerTextView!!.visibility = View.GONE
                mContainerPauseTimer!!.visibility = View.VISIBLE

            }
        }
        trainingState = state

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


}// Required empty public constructor
