package com.dicoding.mysharedreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.mysharedreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var mUserPreference: UserPreference

  private var isPreferenceEmpty = false
  private lateinit var userModel: UserModel

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = "My User Preference"

    mUserPreference = UserPreference(this)

    showExisitngPreference()

    binding.btnSave.setOnClickListener(this)
  }

  private fun showExisitngPreference() {
    userModel = mUserPreference.getUser()
    populateView(userModel)
    checkForm(userModel)
  }

  private fun populateView(userModel: UserModel) {
    binding.apply {
      tvName.text = if (userModel.name.toString().isEmpty()) "Nothing" else userModel.name
      tvAge.text = userModel.age.toString().ifEmpty { "Nothing" }
      tvLoveRm.text = if (userModel.isLove) "Yes" else "No"
      tvEmail.text = if (userModel.email.toString().isEmpty()) "Nothing" else userModel.email
      tvPhone.text =
        if (userModel.phoneNumber.toString().isEmpty()) "Nothing" else userModel.phoneNumber
    }
  }

  private fun checkForm(userModel: UserModel) {
    when {
      userModel.name.toString().isEmpty() -> {
        binding.btnSave.text = getString(R.string.change)
        isPreferenceEmpty = false
      }
      else -> {
        binding.btnSave.text = getString(R.string.save)
        isPreferenceEmpty = true
      }
    }
  }

  private val resultLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) {
    if (it.data != null && it.resultCode == FormUserPreferenceActivity.RESULT_CODE) {
      userModel =
        it.data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
      populateView(userModel)
      checkForm(userModel)
    }
  }

  override fun onClick(view: View) {
    if (view.id == R.id.btn_save) {
      val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
      when {
        isPreferenceEmpty -> {  // jika data kosong
          intent.putExtra(
            FormUserPreferenceActivity.EXTRA_TYPE_FORM,
            FormUserPreferenceActivity.TYPE_ADD
          )
          intent.putExtra("USER", userModel)
        }
        else -> {
          intent.putExtra(
            FormUserPreferenceActivity.EXTRA_TYPE_FORM,
            FormUserPreferenceActivity.TYPE_EDIT
          )
          intent.putExtra("USER", userModel)
        }
      }
      resultLauncher.launch(intent)
    }
  }
}