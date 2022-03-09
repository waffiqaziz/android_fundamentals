package com.dicoding.layout.recycleview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.layout.R

class RecycleViewActivity : AppCompatActivity() {
  private lateinit var rvHeroes: RecyclerView
  private val list = ArrayList<Hero>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recycle_view)

    rvHeroes = findViewById(R.id.rv_heroes)
    rvHeroes.setHasFixedSize(true)

    list.addAll(listHeroes)
    showRecyclerList()
  }

  private val listHeroes: ArrayList<Hero>
    @SuppressLint("Recycle")
    get() {
      val dataName = resources.getStringArray(R.array.data_name)
      val dataDescription = resources.getStringArray(R.array.data_description)
      val dataPhoto = resources.getStringArray(R.array.data_photo)
      val listHero = ArrayList<Hero>()
      for (i in dataName.indices) {
        val hero = Hero(dataName[i],dataDescription[i], dataPhoto[i])
        listHero.add(hero)
      }
      return listHero
    }

  private fun showRecyclerList() {
    rvHeroes.layoutManager = LinearLayoutManager(this)
    val listHeroAdapter = ListHeroAdapter(list)
    rvHeroes.adapter = listHeroAdapter

    // set to grid layout
//    rvHeroes.layoutManager = GridLayoutManager(this, 2)

    listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
      override fun onItemClicked(data: Hero) {
        showSelectedHero(data)
      }
    })
  }

  private fun showSelectedHero(hero: Hero) {
    Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
  }
}