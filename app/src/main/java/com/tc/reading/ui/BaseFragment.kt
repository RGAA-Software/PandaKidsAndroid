package com.tc.reading.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tc.reading.App
import com.tc.reading.AppContext

open class BaseFragment(): Fragment() {

    protected lateinit var appContext: AppContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = activity?.application as App
        appContext = app.getAppContext()
    }

}