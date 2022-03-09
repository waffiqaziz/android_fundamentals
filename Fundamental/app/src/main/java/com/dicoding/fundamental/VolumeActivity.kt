package com.dicoding.fundamental

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VolumeActivity : AppCompatActivity() {
  private lateinit var etWidth: EditText
  private lateinit var etHeight: EditText
  private lateinit var etLength: EditText
  private lateinit var btnCalculate: Button
  private lateinit var tvResult: TextView

  companion object {
    private const val STATE_RESULT = "state_result"
    const val EXTRA_WIDTH = "width"
    const val EXTRA_HEIGHT = "height"
  }


  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_volume)

    setInit()



    if (savedInstanceState != null) {
      val result = savedInstanceState.getString(STATE_RESULT)
      tvResult.text = result
    }

    setView()

    val widthIntent = intent.getStringExtra(EXTRA_WIDTH)
    val heightIntent = intent.getIntExtra(EXTRA_HEIGHT,0)

    println("CEKKKKKKKKK: $widthIntent")
    println("CEKKKKKKKKK: $heightIntent")
    tvResult.text = "Width: $widthIntent, Height: $heightIntent"

    if (savedInstanceState != null) {
      val result = savedInstanceState.getString(STATE_RESULT)
      tvResult.text = result
    }
  }

  // to save data when activity destroyed
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString(STATE_RESULT, tvResult.text.toString())
  }

  // initialize all component via id
  private fun setInit() {
    etWidth = findViewById(R.id.et_width)
    etHeight = findViewById(R.id.et_height)
    etLength = findViewById(R.id.et_length)
    btnCalculate = findViewById(R.id.btn_calculate)
    tvResult = findViewById(R.id.tv_ressult)
  }

  private fun setView() {

    btnCalculate.setOnClickListener {
      val inputLength = etLength.text.toString()
      val inputWidth = etWidth.text.toString()
      val inputHeight = etHeight.text.toString()

      // if user input isn't
      if (!inputIsEmpty(inputLength, inputWidth, inputHeight)) {
        val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
        tvResult.text = volume.toString()
      } else { // if user input is empty
        Toast.makeText(this, "Please input all form", Toast.LENGTH_SHORT).show()
      }
    }
  }

  //check user input empty/not
  private fun inputIsEmpty(inputLength: String, inputWidth: String, inputHeight: String): Boolean {
    var returnValue = false
    if (inputWidth.isEmpty()) {
      etWidth.error = "Field cannot be blank"
      returnValue = true
    }
    if (inputLength.isEmpty()) {
      etLength.error = "Field cannot be blank"
      returnValue = true
    }
    if (inputHeight.isEmpty()) {
      etHeight.error = "Field cannot be blank"
      returnValue = true
    }
    return returnValue
  }
}