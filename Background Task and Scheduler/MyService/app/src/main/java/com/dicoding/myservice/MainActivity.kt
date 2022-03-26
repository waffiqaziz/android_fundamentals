package com.dicoding.myservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.dicoding.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  lateinit var  binding: ActivityMainBinding

  private var mServiceBound = false
  private lateinit var mBoundService: MyBoundService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnStartService.setOnClickListener{
      val mStartServiceIntent = Intent(this, MyService::class.java)
      startService(mStartServiceIntent)
    }
    binding.btnStartJobIntentService.setOnClickListener{
      val mStartIntentService = Intent(this, MyJobIntentService::class.java)
      mStartIntentService.putExtra(MyJobIntentService.EXTRA_DURATION, 5000L)
      MyJobIntentService.enqueueWork(this, mStartIntentService)
    }

    binding.btnStartBoundService.setOnClickListener{
      val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
      bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
    }
    binding.btnStopBoundService.setOnClickListener{
      unbindService(mServiceConnection)
    }
  }

  private val mServiceConnection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName) {
      mServiceBound = false
    }
    override fun onServiceConnected(name: ComponentName, service: IBinder) {
      val myBinder = service as MyBoundService.MyBinder
      mBoundService = myBinder.getService
      mServiceBound = true
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    if (mServiceBound) {
      unbindService(mServiceConnection)
    }
  }
}