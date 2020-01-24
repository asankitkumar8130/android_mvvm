package com.example.githubassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.ecommerce.myapplication.example3.ui.MainListAdapter
import com.example.githubassignment.model.Project
import com.example.githubassignment.viewmodel.inderface.ProjectClickCallback
import com.example.githubassignment.viewmodel.ProjectListViewModel
import com.example.githubassignment.R
import com.example.githubassignment.databinding.MainlistfragmentBinding

class MainListFragment : Fragment() {
    private var mainadapter: MainListAdapter?=null
    private var arraylist=ArrayList<Project>()
    private var dataBindingUtil : MainlistfragmentBinding?=null

    companion object {
        @JvmStatic
         var TAG: String = ""
    }

    init {
        TAG = "ProjectListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         dataBindingUtil=
             DataBindingUtil.inflate(inflater, R.layout.mainlistfragment,container,false) as MainlistfragmentBinding

        mainadapter= MainListAdapter(projectClickCallback, arraylist)
        dataBindingUtil!!.projectList.setItemAnimator(DefaultItemAnimator())
        dataBindingUtil!!.projectList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        dataBindingUtil!!.projectList.adapter = mainadapter
        dataBindingUtil!!.isLoading=true

        return dataBindingUtil!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val projectListViewModel= ViewModelProviders.of(activity!!).get(ProjectListViewModel::class.java)

        projectListViewModel.getdata().observe(this,
            Observer<List<Project>> { projects ->
                if (projects != null) {
                    dataBindingUtil!!.isLoading=false
                    mainadapter!!.setProjectList(projects as ArrayList<Project>)
                }
            })

    }

    private val projectClickCallback = object : ProjectClickCallback {
        override fun onClick(project: Project) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).showdetailview(project)
            }
        }
    }

}