package com.dicoding.fundamental.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import com.dicoding.fundamental.R
import timber.log.Timber

class MainFragmentActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_fragment)

    // activity menggunakan supportFragmentManager
    val mFragmentManager = supportFragmentManager
    val mHomeFragment = HomeFragment()
    val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

    // cara biasa
      if (fragment !is HomeFragment){
        Timber.d("Fragment Name : " + HomeFragment::class.java.simpleName)
        mFragmentManager
          .beginTransaction()
          .add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
          .commit()
      }

    //dengan android KTX
      if (fragment !is HomeFragment) {
        Timber.d("Fragment Name :" + HomeFragment::class.java.simpleName)
        mFragmentManager.commit {
          add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
        }
      }
  }
}