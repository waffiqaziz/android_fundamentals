package com.dicoding.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.dicoding.mybackgroundthread.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors


//thread
//class MainActivity : AppCompatActivity() {
//
//  private lateinit var binding: ActivityMainBinding
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//
//    binding = ActivityMainBinding.inflate(layoutInflater)
//    setContentView(binding.root)
//
//    val executor = Executors.newSingleThreadExecutor()
//    val handler = Handler(Looper.getMainLooper())
//
//    binding.btnStart.setOnClickListener {
//      executor.execute {
//        try {
//          //simulate process in background thread
//          for (i in 0..10) {
//            Thread.sleep(500)
//            val percentage = i * 10
//            handler.post {
//              //update ui in main thread
//              if (percentage == 100) {
//                binding.tvStatus.setText(R.string.task_completed)
//              } else {
//                binding.tvStatus.text = String.format(resources.getString(R.string.compressing, percentage))
////                binding.tvStatus.text = String.format(getString(R.string.compressing, percentage))
//                binding.tvStatus.text = "Compresssing $percentage%%"
//              }
//            }
//          }
//        } catch (e: InterruptedException) {
//          e.printStackTrace()
//        }
//      }
//    }
//  }
//}

//coroutines
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

        binding.btnStart.setOnClickListener {
          lifecycleScope.launch(Dispatchers.Default) {
            //simulate process in background thread
            for (i in 0..10) {
              delay(500)
              val percentage = i * 10
              withContext(Dispatchers.Main) {
                //update ui in main thread
                if (percentage == 100) {
                  binding.tvStatus.setText(R.string.task_completed)
                } else {
                  binding.tvStatus.text = "Compresssing $percentage%%"
                }
              }
            }
          }
        }
  }
}



//class MainActivity : AppCompatActivity() {
//
//  private lateinit var binding: ActivityMainBinding
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//
//    binding = ActivityMainBinding.inflate(layoutInflater)
//    setContentView(binding.root)
//
//    val executor = Executors.newSingleThreadExecutor()
//    val handler = Handler(Looper.getMainLooper())
//
//    binding.btnStart.setOnClickListener {
//      executor.execute {
//        try {
//          //simulate process in background thread
//          for (i in 0..10) {
//            Thread.sleep(500)
//            val percentage = i * 10
//            handler.post {
//              //update ui in main thread
//              if (percentage == 100) {
//                binding.tvStatus.setText(R.string.task_completed)
//              } else {
//                binding.tvStatus.text = String.format(getString(R.string.compressing, percentage))
//              }
//            }
//          }
//        } catch (e: InterruptedException) {
//          e.printStackTrace()
//        }
//      }
//    }
//  }
//}