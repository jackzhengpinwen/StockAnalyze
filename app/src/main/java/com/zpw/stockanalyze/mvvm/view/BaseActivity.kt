package com.zpw.stockanalyze.mvvm.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

abstract class BaseActivity: AppCompatActivity() {
    private val TAG: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(TAG, "[onCreate]")
    }

    override fun onStart() {
        super.onStart()
        Timber.d(TAG, "[onStart]")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d(TAG, "[onRestart]")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d(TAG, "[onSaveInstanceState]")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            Timber.d(TAG, "[onNewIntent]$intent")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d(
            TAG, "[onActivityResult] requestCode:" + requestCode + ";"
                    + "resultCode:" + requestCode
        )
    }

    override fun onResume() {
        super.onResume()
        Timber.d(TAG, "[onResume]")
    }

    override fun onPause() {
        super.onPause()
        Timber.d(TAG, "[onPause]")
    }

    override fun onStop() {
        super.onStop()
        Timber.d(TAG, "[onStop]")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d(TAG, "[onDestroy]")
    }
}