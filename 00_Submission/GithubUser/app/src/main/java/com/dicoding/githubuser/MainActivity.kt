package com.dicoding.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  private lateinit var rvUsers: RecyclerView
  private val list = ArrayList<User>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportActionBar?.title = getString(R.string.github_users)

    rvUsers = findViewById(R.id.rv_users)
    rvUsers.setHasFixedSize(true)

    list.addAll(listHeroes)
    showRecyclerList()
  }

  private val listHeroes: ArrayList<User>
    get() {
      val dataUsername = resources.getStringArray(R.array.username)
      val dataName = resources.getStringArray(R.array.name)
      val dataAvatar = resources.obtainTypedArray(R.array.avatar)
      val dataFollowers = resources.getStringArray(R.array.followers)
      val dataFollowing = resources.getStringArray(R.array.following)
      val dataCompany = resources.getStringArray(R.array.company)
      val dataLocation = resources.getStringArray(R.array.location)
      val dataRepository = resources.getStringArray(R.array.repository)
      val listUser = ArrayList<User>()
      for (i in dataName.indices) {
        val user = User(
          dataUsername[i],
          dataName[i],
          dataAvatar.getResourceId(i, -1),
          dataFollowers[i],
          dataFollowing[i],
          dataCompany[i],
          dataLocation[i],
          dataRepository[i]
        )
        listUser.add(user)
      }
      dataAvatar.recycle()
      return listUser
    }

  private fun showRecyclerList() {
    rvUsers.layoutManager = LinearLayoutManager(this)
    val listUserAdapter = ListUserAdapter(list)
    rvUsers.adapter = listUserAdapter

    listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
      override fun onItemClicked(data: User) {

        val moveUserDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveUserDetail.putExtra(DetailUserActivity.EXTRA_USER, data)
        startActivity(moveUserDetail)
      }
    })
  }
}