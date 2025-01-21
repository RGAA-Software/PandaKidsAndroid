package com.tc.reading.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tc.reading.ui.audio.AudioSuitFragment
import com.tc.reading.ui.book.BookFragment
import com.tc.reading.ui.movie.MovieFragment
import com.tc.reading.ui.me.AboutMeFragment
import com.tc.reading.ui.settings.SettingsFragment
import com.tc.reading.ui.cartoon.CartoonSuitFragment
import com.tc.reading.ui.documentary.DocumentaryFragment
import com.tc.reading.ui.science.ScienceFragment

class TabsFragmentPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles = arrayOf("Cartoon",
        "Documentary",
        "Movie",
        "Audio",
        "Science",
        "Book",
        "About Me",
        "Settings")
    private val fragment = arrayOf(CartoonSuitFragment(),
        DocumentaryFragment(),
        MovieFragment(),
        AudioSuitFragment(),
        ScienceFragment(),
        BookFragment(),
        AboutMeFragment(),
        SettingsFragment())

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }
}
