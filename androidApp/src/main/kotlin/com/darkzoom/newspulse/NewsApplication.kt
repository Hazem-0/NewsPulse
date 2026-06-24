package com.darkzoom.newspulse

import android.app.Application
import org.koin.android.ext.koin.androidContext
import com.darkzoom.newspulse.di.initKoin
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@NewsApplication)
        }
    }
}