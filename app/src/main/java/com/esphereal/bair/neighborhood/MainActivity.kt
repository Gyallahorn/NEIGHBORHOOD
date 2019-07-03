package com.esphereal.bair.funloot

import android.location.Location
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import java.util.Date
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.esphereal.bair.funloot.NEIGHBORHOOD.NibbaProblemsActivity
import com.esphereal.bair.funloot.jsoup.JsoupSingleton
import com.esphereal.bair.funloot.profile.ProfileActivity
import com.esphereal.bair.funloot.profile.ProfileCallBack
import com.esphereal.bair.funloot.profile.ProfileSingletone
import com.esphereal.bair.funloot.retrofit.FunlootUser
import com.esphereal.bair.funloot.dummy.DiscountDummyContent
import com.esphereal.bair.funloot.dummyAdding.AddingActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), TrackerFragment.OnFragmentInteractionListener, DiscountFragment.OnListFragmentInteractionListener, NewsListFragment.OnListFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    override fun onFragmentInteraction(uri: Uri) {

    }
    override fun onListFragmentInteraction(item: DiscountDummyContent.DiscountDummyItem?) {

    }

    companion object {
        // won't compile
        const val TAG = "MainActivity"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        var viewPager: ViewPager = findViewById(R.id.pager) as ViewPager
        when (id) {
            R.id.nav_news -> {
                viewPager.currentItem = 0
            }
            R.id.nav_track -> {
                viewPager.currentItem = 1
            }
            R.id.nav_profile -> {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                logOut()
            }
            R.id.nav_loot -> {
                viewPager.currentItem = 2
            }
            R.id.nav_about_app -> {
                val intent = Intent(this@MainActivity, AboutAppActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_ivent -> {
                val intent = Intent(this@MainActivity, AddingActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_problems-> {
                val intent = Intent(this@MainActivity, NibbaProblemsActivity::class.java)
                startActivity(intent)
            }

        }

        val drawer = findViewById(R.id.drawer_layout_main) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true;
    }

    fun logOut() {
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        var googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this
        ) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onListFragmentInteraction(item: JsoupSingleton.NewsItem, loader: ProgressBar) {

        Log.d("DEBUG", "onclick")
        //loader.setVisibility(View.VISIBLE)
        val i = Intent(this, NewsDetails::class.java)
        i.putExtra("url", item.href)
        startActivity(i)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ///RetrofitSingleton.getInstance().GetApi().postTrain(PrefUtil.getIdToken(this),)

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        (findViewById<Button>(R.id.menu_toggle)).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val drawer = findViewById(R.id.drawer_layout_main) as DrawerLayout
                drawer.openDrawer(GravityCompat.START)
            }
        }
        )

        val tabLayout = findViewById(R.id.tablayout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Новости"))
        tabLayout.addTab(tabLayout.newTab().setText("Трекер"))
        tabLayout.addTab(tabLayout.newTab().setText("Лут"))
        /*        tabLayout.addTab(tabLayout.newTab().setText("#каналы"));
        tabLayout.addTab(tabLayout.newTab().setText("Мой профиль"));*/
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        var viewPager: ViewPager = findViewById(R.id.pager) as ViewPager
        val adapter = MainActivityPagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        viewPager.offscreenPageLimit = 3;

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(pageNumber: Int) {
                val tab: TabLayout.Tab? = tabLayout.getTabAt(pageNumber);
                if (tab != null) {
                    val text = tab.getText()
                    Log.d(TAG, "" + text)
                    findViewById<TextView>(R.id.title_top_panel).setText(text)
                };
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }
        });

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        val headerLayout = navigationView.getHeaderView(0)

        val userTextView = headerLayout.findViewById<TextView>(R.id.user_name_nav_header)
        ProfileSingletone.getInstance().GetUser(object : ProfileCallBack {
            override fun onBackData(user: FunlootUser?) {

                if (user != null)
                    userTextView.setText(user.displayName)
            }
        })
    }

    private fun formatLocation(location: Location?): String {
        return if (location == null) "" else String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3\$tF %3\$tT",
                location.latitude, location.longitude, Date(
                location.time))

        }

    }

