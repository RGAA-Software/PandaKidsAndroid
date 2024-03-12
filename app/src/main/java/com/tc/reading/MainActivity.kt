package com.tc.reading

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.tc.reading.databinding.ActivityMainBinding
import com.tc.reading.ui.dashboard.DashboardFragment
import com.tc.reading.ui.home.HomeFragment
import com.tc.reading.ui.me.AboutMeFragment
import com.tc.reading.ui.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val ID_BOOK = 1
        private const val ID_MOVIE = 2
        private const val ID_DAY = 3
        private const val ID_ME = 4
    }

    private lateinit var bookFragment: HomeFragment
    private lateinit var videoFragment: DashboardFragment
    private lateinit var dayFragment: NotificationsFragment
    private lateinit var aboutMeFragment: AboutMeFragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        window.statusBarColor = this.resources.getColor(com.rajat.pdfviewer.R.color.colorPrimary);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookFragment = HomeFragment();
        videoFragment = DashboardFragment();
        dayFragment = NotificationsFragment();
        aboutMeFragment = AboutMeFragment();

        supportActionBar?.title = "Books";
        val fragmentHost = binding.root.findViewById<RelativeLayout>(R.id.fragment_host);


        binding.bottomBar.apply {
            add(MeowBottomNavigation.Model(ID_BOOK, R.drawable.ic_book))
            add(MeowBottomNavigation.Model(ID_MOVIE, R.drawable.ic_movie))
            add(MeowBottomNavigation.Model(ID_DAY, R.drawable.ic_sun))
            add(MeowBottomNavigation.Model(ID_ME, R.drawable.ic_account))

            //setCount(ID_NOTIFICATION, "15")

            setOnShowListener {
                when (it.id) {
                    ID_BOOK -> {
                        switchFragment(bookFragment)
                    }
                    ID_MOVIE -> {
                        switchFragment(videoFragment)
                    }
                    ID_DAY -> {
                        switchFragment(dayFragment)
                    }
                    ID_ME -> {
                        switchFragment(aboutMeFragment)
                    }
                }
            }

            setOnClickMenuListener {

            }

            setOnReselectListener {
                Toast.makeText(context, "item ${it.id} is reselected.", Toast.LENGTH_LONG).show()
            }

            show(ID_BOOK)

        }

        //switchFragment(bookFragment)
    }

    private fun switchFragment(to: Fragment) {
        val transaction = supportFragmentManager.beginTransaction();
        if (currentFragment == null) {
            if (!to.isAdded) {
                transaction.add(R.id.fragment_host, to).commit();
            } else {
                transaction.show(to).commit();
            }
            currentFragment = to;
        } else {
            if (currentFragment == to) {
                if (!to.isAdded) {
                    transaction.add(R.id.fragment_host, to).commit();
                } else {
                    transaction.show(to).commit();
                }
            } else {
                if (!to.isAdded) {
                    transaction.hide(currentFragment!!).add(R.id.fragment_host, to).commit();
                } else {
                    transaction.hide(currentFragment!!).show(to).commit();
                }
            }
            currentFragment = to;
        }
    }

}