package com.esphereal.bair.funloot

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.Callable

import java.util.concurrent.TimeUnit
//deprecated
class TrackerSingleton private constructor() {
    companion object {
        private val TAG = TrackerSingleton::class.java.simpleName

        //singletone
        @Volatile
        private var instance: TrackerSingleton? = null

        fun getInstance(): TrackerSingleton? {
            var localInstance = instance
            if (localInstance == null) {
                synchronized(TrackerSingleton::class.java) {
                    localInstance = instance
                    if (localInstance == null) {
                        localInstance = TrackerSingleton()
                        instance = localInstance
                    }
                }
            }
            return localInstance
        }

        fun getDateDiff(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
            val diffInMillies = date2.time - date1.time
            return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS)
        }
    }

    private var secondsRemaining: Long = 0
    private var inited: Boolean = false
    private var currentLocation: Location? = null

    private var mLastGpsChangeTime: Date? = null

    private var mGpslocationListener: LocationListener? = null
    private var mNetworklocationListener: LocationListener? = null
    private var mLocationManager: LocationManager? = null
    private var mLocationChangeCallback: LocationChangeCallback? = null

    private val latDohsun = 62.038687
    private val lotDohsun = 129.712351

    private val radius = 0.10648897070222839

    private lateinit var timer: CountDownTimer

    enum class TimerState {
        Stopped, Running,Paused
    }

    private var timerState = TimerState.Stopped

    @SuppressLint("MissingPermission")
    fun GetCurrentLocation(): Location? {
        if (currentLocation == null) {
            currentLocation = mLocationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (currentLocation == null) {
                currentLocation = mLocationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        }
        return currentLocation
    }

    fun GetLastGpsChangeTime(): Date {
        if (mLastGpsChangeTime == null)
            mLastGpsChangeTime = Date()
        return mLastGpsChangeTime as Date
    }

    fun SetCurrentLocation(location: Location, itsGps: Boolean) {
        val nowTime = Date()
        if (getDateDiff(GetLastGpsChangeTime(), nowTime, TimeUnit.SECONDS) > 60 || itsGps) {
            if (itsGps)
                mLastGpsChangeTime = nowTime

            currentLocation = location
            mLocationChangeCallback!!.OnChange(location);
        }
    }


    fun Init(context: Context, callback: LocationChangeCallback): Observable<Boolean> {

        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {

                if (inited)
                    return true
                mLocationChangeCallback = callback
                val permission = ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION)

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context as Activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1)
                    return false
                }
                mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                mGpslocationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        SetCurrentLocation(location, true)
                    }

                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                    }

                    override fun onProviderEnabled(provider: String) {

                    }

                    override fun onProviderDisabled(provider: String) {

                    }
                }
                mNetworklocationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        SetCurrentLocation(location, false)
                    }

                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                    }

                    override fun onProviderEnabled(provider: String) {

                    }

                    override fun onProviderDisabled(provider: String) {

                    }
                }
                mLocationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0,
                        0f,
                        mNetworklocationListener)

                mLocationManager!!.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0f,
                        mGpslocationListener)

                inited = true




                return true
            }

        })


    }

    /**
     * function for getting distance
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    private fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val latA = Math.toRadians(lat1)
        val lonA = Math.toRadians(lon1)
        val latB = Math.toRadians(lat2)
        val lonB = Math.toRadians(lon2)
        val cosAng = Math.cos(latA) * Math.cos(latB) * Math.cos(lonB - lonA) + Math.sin(latA) * Math.sin(latB)
        val ang = Math.acos(cosAng)
        return ang * 6371
    }

    fun CheckDistance(): Boolean {
        val loc = GetCurrentLocation()
        val lat1 = loc!!.latitude
        val lot1 = loc.longitude

        val dist = getDistance(lat1, lot1, latDohsun, lotDohsun)
        Log.d(TAG, dist.toString() + "")
        return if (radius < dist)
            true
        else
            false
    }

    fun ItsInited(): Boolean {
        return inited
    }

    fun InitTimer(callback: CountDownTimerCallback) {
        secondsRemaining = 60 * 60

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() {
                onTimerFinished()
                callback.onFinish()
            }

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                callback.onTick(millisUntilFinished)
            }
        }
    }

    fun StartTimer() {
        timerState=TimerState.Running
        timer.start()

    }

    fun PauseTimer() {
        timerState=TimerState.Paused
        timer.cancel()
    }

    fun StopTimer() {
        timerState=TimerState.Stopped

        timer.cancel()
    }

    private fun onTimerFinished() {
    }

    interface LocationChangeCallback {
        fun OnChange(location: Location)
    }

    interface CountDownTimerCallback {
        fun onTick(millisUntilFinished: Long)
        fun onFinish()
    }


}
