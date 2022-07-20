package com.scribd.app

import android.app.Application

class ScribdApp: Application() {

    companion object {
        fun getInstance() = ScribdApp()
    }

}
