package com.dicoding.navigation.actionbar

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import com.dicoding.navigation.R
import com.dicoding.navigation.databinding.ActivityActionBarMainBinding

class ActionBarMainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityActionBarMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityActionBarMainBinding.inflate(layoutInflater)
    setContentView(R.layout.activity_action_bar_main)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.option_menu, menu)

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    val searchView = menu.findItem(R.id.search).actionView as SearchView

    searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
    searchView.queryHint = resources.getString(R.string.search_hint)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      /*
      Gunakan method ini ketika search selesai atau OK
       */
      override fun onQueryTextSubmit(query: String): Boolean {
        Toast.makeText(this@ActionBarMainActivity, query, Toast.LENGTH_SHORT).show()
        searchView.clearFocus()
        return true
      }

      /*
      Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
       */
      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu1 -> {
        supportFragmentManager.beginTransaction()
          .replace(R.id.fragment_container, MenuFragment())
          .addToBackStack(null)
          .commit()
        return true
      }
      R.id.menu2 -> {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        return true
      }
      else -> return true
    }
  }

}