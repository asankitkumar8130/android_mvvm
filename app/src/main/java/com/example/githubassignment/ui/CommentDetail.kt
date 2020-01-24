package com.example.githubassignment.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.ecommerce.myapplication.example3.ui.MainCommentListAdapter
import com.ecommerce.myapplication.example3.viewmodel.factory.DetailProjectFactory
import com.example.githubassignment.R
import com.example.githubassignment.databinding.DetailcommentfragmentBinding
import com.example.githubassignment.model.Project
import com.example.githubassignment.viewmodel.ProjectViewModel


class CommentDetail : Fragment(){
    var dataBindingUtil: DetailcommentfragmentBinding?=null


    private var mainadapter: MainCommentListAdapter?=null
    private var arraylist=ArrayList<Project>()


    companion object{
        @JvmStatic
        var TAG:String = ""

        @JvmStatic
        fun newInstance(projectid: String) = CommentDetail().apply {
            arguments = Bundle().apply {
                putString("project_id", projectid)
            }
        }

    }
    init {
        TAG ="DetailViewFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBindingUtil= DataBindingUtil.inflate(inflater, R.layout.detailcommentfragment,container,false) as DetailcommentfragmentBinding


        mainadapter = MainCommentListAdapter(arraylist)
        dataBindingUtil!!.projectList.setItemAnimator(DefaultItemAnimator())
        dataBindingUtil!!.projectList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        dataBindingUtil!!.projectList.adapter = mainadapter
        dataBindingUtil!!.isLoading=true

        return dataBindingUtil!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory= DetailProjectFactory(activity!!.application,
            arguments!!.getString("project_id")!!
        )

        val projectViewModel= ViewModelProviders.of(this,factory).get(ProjectViewModel::class.java)


        projectViewModel.getdata().observe(this,  object : Observer<List<Project>> {
            override fun onChanged(projects: List<Project>?) {
                if (projects != null && projects.isNotEmpty()) {
                    dataBindingUtil!!.isLoading=false
                    mainadapter!!.setProjectList(projects as ArrayList<Project>)
                }else if(projects?.isEmpty()!!){
                    dataBindingUtil!!.isLoading=false

                    AlertDialog.Builder(context)
                        .setTitle("Alert")
                        .setMessage("No Comment Found")
                        .setPositiveButton("Ok", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0?.dismiss()
                                activity?.onBackPressed()
                            }
                        })
                        .show()
                }
            }


        })
    }
}