package com.tc.reading.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.tc.reading.R
import com.tc.reading.ui.BaseFragment

class SettingsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(requireContext(), R.layout.fragment_settings, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serverAddressView = view.findViewById<TextInputEditText>(R.id.id_server_address)
        serverAddressView.setText(appContext.getAppSettings().getServerAddress())
        view.findViewById<Button>(R.id.id_settings_save).setOnClickListener {
            appContext.getAppSettings().setServerAddress(serverAddressView.text.toString())
        }
    }

}