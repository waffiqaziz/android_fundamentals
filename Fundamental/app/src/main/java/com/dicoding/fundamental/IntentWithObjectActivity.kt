package com.dicoding.fundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class IntentWithObjectActivity : AppCompatActivity() {

  companion object{
    const val EXTRA_PERSON = "extra_person"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_intent_with_object)

    val tvObject: TextView = findViewById(R.id.parcelable)

    val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as Person
    val text = """
      Name: ${person.name.toString()}
      Email: ${person.email.toString()}
      Age: ${person.age}
      Location: ${person.city.toString()}
      """.trimIndent()

    tvObject.text = text
  }
}