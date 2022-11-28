package com.btoff

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

class Receiver : BroadcastReceiver() {
    private val TAG = "BToff"
    private val DISABLE_BT_ACTION = "com.btoff.disable"
    private val DISABLE_BT_DELAY : Long  = 30 * 1000 // 30 seconds
    private lateinit var mContext: Context

    override fun onReceive(context: Context, intent: Intent) {
        Log.v(TAG, "onReceive() : $intent")
        mContext = context.applicationContext

        when(intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> sendDelayedIntent()
            DISABLE_BT_ACTION -> disableBT()
        }
    }

    private fun sendDelayedIntent() {
        val intent = Intent(mContext, Receiver::class.java)
        intent.action = DISABLE_BT_ACTION
        Handler(Looper.getMainLooper()).postDelayed({ mContext.sendBroadcast(intent) },
            DISABLE_BT_DELAY)
    }

    private fun disableBT() {
        Log.v(TAG, "disableBT()")
        android.bluetooth.BluetoothAdapter.getDefaultAdapter().disable()
        Toast.makeText(mContext, "BT Disabled", Toast.LENGTH_LONG).show()
    }
}
