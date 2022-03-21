package com.dicoding.mysharedreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.dicoding.mysharedreferences.databinding.ActivityFormUserPreferenceBinding

class FormUserPreferenceActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var userModel: UserModel

  private lateinit var binding: ActivityFormUserPreferenceBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnSave.setOnClickListener(this)

    userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel
    val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

    var actionBarTitle = ""
    var btnTitle = ""

    when (formType) {
      TYPE_ADD -> {
        actionBarTitle = "Add New"
        btnTitle = "Save"
      }
      TYPE_EDIT -> {
        actionBarTitle = "Edit"
        btnTitle = "Update"
        showPreferenceInForm()
      }
    }

    supportActionBar?.title = actionBarTitle
    supportActionBar?.setDisplayShowTitleEnabled(true)
    binding.btnSave.text = btnTitle

  }

  private fun showPreferenceInForm() {
    binding.apply {
      etName.setText(userModel.name)
      etEmail.setText(userModel.email)
      etAge.setText(userModel.age.toString())
      etPhone.setText(userModel.phoneNumber)

      if (userModel.isLove) {
        rbYes.isChecked = true
      } else {
        rbNo.isChecked = false
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onClick(v: View) {
    if (v.id == R.id.btn_save) {
      val name = binding.etName.text.toString().trim()
      val email = binding.etEmail.text.toString().trim()
      val age = binding.etAge.text.toString().trim()
      val phoneNo = binding.etPhone.text.toString().trim()
      val isLoveRM = binding.rgLoveRm.checkedRadioButtonId == R.id.rb_yes

      if (name.isEmpty()) {
        binding.etEmail.error = FIELD_REQUIRED
        return
      }

      if (!isValidEmail(email)) {
        binding.etEmail.error = FIELD_IS_NOT_VLIED
        return
      }

      if (age.isEmpty()) {
        binding.etAge.error = FIELD_REQUIRED
        return
      }

      if (phoneNo.isEmpty()) {
        binding.etPhone.error = FIELD_REQUIRED
        return
      }

      if (!TextUtils.isDigitsOnly((phoneNo))) {
        binding.etPhone.error = FIELD_DIGIT_ONLY
        return
      }

      saveUser(name, email, age, phoneNo, isLoveRM)

      val resultIntent = Intent()
      resultIntent.putExtra(EXTRA_RESULT, userModel)
      setResult(RESULT_CODE, resultIntent)

      finish()
    }
  }

  private fun saveUser(
    name: String,
    email: String,
    age: String,
    phoneNo: String,
    isLoveRM: Boolean
  ) {
    val userPreference = UserPreference(this)

    userModel.name = name
    userModel.email = email
    userModel.age = Integer.parseInt(age)
    userModel.phoneNumber = phoneNo
    userModel.isLove = isLoveRM

    userPreference.setUser(userModel)
    Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
  }

  private fun isValidEmail(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  companion object {
    const val EXTRA_TYPE_FORM = "extra_type_form"
    const val EXTRA_RESULT = "extra_result"
    const val RESULT_CODE = 101

    const val TYPE_ADD = 1
    const val TYPE_EDIT = 2

    private const val FIELD_REQUIRED = "This field must filled"
    private const val FIELD_DIGIT_ONLY = "This field must digit"
    private const val FIELD_IS_NOT_VLIED = "Email not valid"
  }
}