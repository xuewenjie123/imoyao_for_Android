package com.beecredit.moyao

import android.content.Context
import android.location.LocationManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
//import com.imoyao.lib.imoyao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import com.beecredit.moyao.R.id.ssid
import kotlinx.android.synthetic.main.activity_main.*
import com.imoyao.lib.imoyao
import com.imoyao.lib.moyaoCallback


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mApPasswd: EditText = findViewById<EditText>(R.id.passwd)
        val mConfirmBtn = findViewById<Button>(R.id.wifibtn)

        val my = imoyao(this)

        ssid.setText("ssid:" + my.getSSID())
        bssid.setText("bssid:" + my.getBSSID())

        val callback = moyaoCallback { isSucc, msg ->
            Log.e("#######", "结果 ---> $isSucc$msg")
            if (isSucc) {
                resultText.setText("配网成功" + msg)
            } else {
                resultText.setText("配网失败" + msg)
            }
        }


        if (mConfirmBtn != null) {
            mConfirmBtn.setOnClickListener ({
                Log.e("begin", "#######")
                my.Connect(mApPasswd.text.toString(), callback)
            })
        }

    }

}

