package com.tc.reading.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tc.reading.ui.audio.AudioSuitFragment
import com.tc.reading.ui.book.BookFragment
import com.tc.reading.ui.day.DayFragment
import com.tc.reading.ui.me.AboutMeFragment
import com.tc.reading.ui.video.VideoSuitFragment

public class SimpleFragmentPagerAdapter constructor(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles = arrayOf("Cartoon", "Documentary", "Movie", "Comic", "Audio", "Science", "Book")
    private val fragment = arrayOf(VideoSuitFragment(), DayFragment(), BookFragment(), BookFragment(), AudioSuitFragment(), BookFragment(), AboutMeFragment())

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
