package com.dicoding.fundamental

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.fundamental.fragment.MainFragmentActivity
import timber.log.Timber

class MainMenuActivity : AppCompatActivity(), View.OnClickListener {

  private lateinit var tvResult: TextView
  private lateinit var btnSetValue: Button
  private lateinit var tvText: TextView

  private var names = ArrayList<String>()

  @SuppressLint("SetTextI18n")
  private val resultLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) { result ->
    if (result.resultCode == IntentResultActivity.RESULT_CODE && result.data != null) {
      val selectedValue =
        result.data?.getIntExtra(IntentResultActivity.EXTRA_SELECTED_VALUE, 0)
      tvResult.text = "Hasil : $selectedValue"
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_menu)

    setInit()

    //implementasi Timber
    Timber.d("Data yang ingin ditampilkan")

    //for setValue,debug modul
    names.add("Narenda Wicaksono")
    names.add("Kevin")
    names.add("Yoza")

  }

  private fun setInit() {
    val btnWithoutValue: Button = findViewById(R.id.btn_nvalue)
    btnWithoutValue.setOnClickListener(this)

    val btnWithValue: Button = findViewById(R.id.btn_value)
    btnWithValue.setOnClickListener(this)

    val btnWithObject: Button = findViewById(R.id.btn_object)
    btnWithObject.setOnClickListener(this)

    val btnDialNo: Button = findViewById(R.id.btn_dial)
    btnDialNo.setOnClickListener(this)

    val btnFragment: Button = findViewById(R.id.btn_fragment)
    btnFragment.setOnClickListener(this)

    val btnResult: Button = findViewById(R.id.btn_result)
    btnResult.setOnClickListener(this)
    tvResult = findViewById(R.id.tv_result_activity)

    btnSetValue = findViewById(R.id.btn_set_value)
    btnSetValue.setOnClickListener(this)
    tvText = findViewById(R.id.tv_text)

  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn_nvalue -> {
        val moveIntent = Intent(this, IntentWithoutValueActivity::class.java)
        startActivity(moveIntent)
      }
      R.id.btn_value -> {
        val moveIntent = Intent(this@MainMenuActivity, VolumeActivity::class.java)
        moveIntent.putExtra(VolumeActivity.EXTRA_WIDTH, "Hai")
        moveIntent.putExtra(VolumeActivity.EXTRA_HEIGHT, 4)
        startActivity(moveIntent)
      }
      R.id.btn_object -> {
        val person = Person(
          "Waffiq Aziz",
          20,
          "waffiq.a2002@gmail.com",
          "Purworejo"
        )

        val moveWithObjectIntent =
          Intent(this@MainMenuActivity, IntentWithObjectActivity::class.java)
        moveWithObjectIntent.putExtra(IntentWithObjectActivity.EXTRA_PERSON, person)
        startActivity(moveWithObjectIntent)
      }
      R.id.btn_result -> {
        val moveForResultIntent = Intent(this@MainMenuActivity, IntentResultActivity::class.java)
        resultLauncher.launch(moveForResultIntent)
      }

      R.id.btn_dial -> {
        val phoneNumber = "081234567890"
        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialPhoneIntent)
      }
      R.id.btn_set_value -> {
        Timber.d(names.toString())
        val name = StringBuilder()

//        for (i in 0..3) name.append(names[i]).append("\n")
        for (i in 0..2) name.append(names[i]).append("\n")

        tvText.text = name.toString()
      }
      R.id.btn_fragment -> {
        val moveToFragment = Intent(this, MainFragmentActivity::class.java)
        startActivity(moveToFragment)
      }
    }
  }

}