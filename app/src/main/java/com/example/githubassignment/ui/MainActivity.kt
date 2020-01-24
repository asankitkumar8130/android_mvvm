package com.example.githubassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.githubassignment.R
import com.example.githubassignment.databinding.ActivityMainBinding
import com.example.githubassignment.model.Project

class MainActivity : AppCompatActivity() {
    var databinding : ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             databinding=DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)  as ActivityMainBinding

        if (savedInstanceState == null) {
            databinding?.toolbar?.title = "Issue List"
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, MainListFragment(), MainListFragment.TAG).commit()
        }
    }

    fun showdetailview(project: Project) {
        databinding?.toolbar?.title = "Comments"

        val frag= CommentDetail.newInstance(project.number.toString())
        supportFragmentManager.beginTransaction().addToBackStack("project").replace(
            R.id.fragment_container,frag, null).commit()
    }

    override fun onBackPressed() {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(currentFragment is CommentDetail){
            databinding?.toolbar?.title = "Issue List"
        }

        super.onBackPressed()

    }
}
