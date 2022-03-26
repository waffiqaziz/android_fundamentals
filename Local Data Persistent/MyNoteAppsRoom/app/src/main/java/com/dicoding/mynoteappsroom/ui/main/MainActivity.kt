package com.dicoding.mynoteappsroom.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mynoteappsroom.R
import com.dicoding.mynoteappsroom.databinding.ActivityMainBinding
import com.dicoding.mynoteappsroom.helper.ViewModelFactory
import com.dicoding.mynoteappsroom.ui.insert.NoteAddUpdateActivity

class MainActivity : AppCompatActivity() {

  private var _activityMainBinding: ActivityMainBinding? = null
  private val binding get() = _activityMainBinding

  private lateinit var adapter: NoteAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding?.root)

    val mainViewModel = obtainViewModel(this@MainActivity)
    mainViewModel.getAllNotes().observe(this) {
      if (it != null) {
        adapter.setListNotes(it)
      }
    }

    adapter = NoteAdapter()

    binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
    binding?.rvNotes?.setHasFixedSize(true)
    binding?.rvNotes?.adapter = adapter

    binding?.fabAdd?.setOnClickListener {
      if (it.id == R.id.fab_add) {
        val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
        startActivity(intent)
      }
    }
  }

  private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[MainViewModel::class.java]
  }

  override fun onDestroy() {
    super.onDestroy()
    _activityMainBinding = null
  }

}