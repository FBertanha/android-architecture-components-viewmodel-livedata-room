package br.com.alura.technews.app

import android.app.Application
import br.com.alura.technews.di.viewModelModule
import br.com.alura.technews.di.persistenceModule
import br.com.alura.technews.di.webApiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by felipebertanha on 25/May/2020
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            // declare modules
            modules(
                viewModelModule,
                webApiModule,
                persistenceModule
            )
        }

    }
}