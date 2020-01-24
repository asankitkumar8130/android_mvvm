package com.ecommerce.myapplication.example3.ui

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubassignment.R
import com.example.githubassignment.databinding.CommentListItemBinding
import com.example.githubassignment.model.Project
import java.text.SimpleDateFormat
import java.util.*


class MainCommentListAdapter(arrayList: ArrayList<Project>) :
    RecyclerView.Adapter<MainCommentListAdapter.ProjectViewHolder>() {
    var arraylist = arrayList

    fun setProjectList(projectList: ArrayList<Project>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return this@MainCommentListAdapter.arraylist.size
            }

            override fun getNewListSize(): Int {
                return projectList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@MainCommentListAdapter.arraylist.get(oldItemPosition).id === projectList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val project = projectList[newItemPosition]
                val old = projectList[oldItemPosition]
                return project.id === old.id
            }
        })
        this.arraylist = projectList
        result.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ProjectViewHolder {
        val binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.comment_list_item,
                        parent, false) as CommentListItemBinding
        return ProjectViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val catagoryViewModel = arraylist[position]
        holder.bind(catagoryViewModel)
    }

    class ProjectViewHolder(val catagoryBinding: CommentListItemBinding) : RecyclerView.ViewHolder(catagoryBinding.root) {
        fun bind(catagoryViewModel: Project) {
            this.catagoryBinding.project = catagoryViewModel
            catagoryBinding.executePendingBindings()
        }
    }

    companion object {
        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        @BindingAdapter("android:time")
        fun setDate(stamp_view: TextView, stamp_time: String?) {
            if (!stamp_time.isNullOrBlank()) {
                val date1 = SimpleDateFormat("yyyy-mm-dd").parse(stamp_time)
                stamp_view.text=DateUtils.getRelativeTimeSpanString(date1.time.toLong())
            }

        }
    }
}