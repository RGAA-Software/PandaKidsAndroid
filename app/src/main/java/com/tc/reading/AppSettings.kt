package com.tc.reading

class AppSettings(private var appCtx: AppContext) {

    companion object {
        const val KEY_SERVER_ADDRESS = "Key_ServerAddress";
    }

    fun getServerAddress(): String {
        return appCtx.getSpUtils().getString(KEY_SERVER_ADDRESS)
    }

    fun setServerAddress(address: String) {
        appCtx.getSpUtils().put(KEY_SERVER_ADDRESS, address)
    }

}