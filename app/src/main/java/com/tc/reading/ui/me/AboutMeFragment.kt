package com.tc.reading.ui.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tc.reading.AppContext
import com.tc.reading.databinding.FragmentAboutmeBinding
import com.tc.reading.ui.BaseFragment

class AboutMeFragment(appContext: AppContext) : BaseFragment(appContext) {

    private lateinit var binding: FragmentAboutmeBinding;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutmeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}