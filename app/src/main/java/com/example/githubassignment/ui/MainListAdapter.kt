package com.ecommerce.myapplication.example3.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubassignment.R
import com.example.githubassignment.databinding.ProjectListItemBinding
import com.example.githubassignment.model.Project
import com.example.githubassignment.viewmodel.inderface.ProjectClickCallback

class MainListAdapter(
    private var projectClickCallback: ProjectClickCallback,
    arrayList: ArrayList<Project>
) : RecyclerView.Adapter<MainListAdapter.ProjectViewHolder>() {
    var arraylist = arrayList

    fun setProjectList(projectList: ArrayList<Project>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return this@MainListAdapter.arraylist.size
            }

            override fun getNewListSize(): Int {
                return projectList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@MainListAdapter.arraylist.get(oldItemPosition).id === projectList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val project = projectList[newItemPosition]
                val old = projectList[oldItemPosition]
                return project.id === old.id && project.title == old.title
            }
        })
        this.arraylist = projectList
        result.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ProjectViewHolder {
        val binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.project_list_item,
                        parent, false) as ProjectListItemBinding
        binding.setCallback(projectClickCallback)
        return ProjectViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val catagoryViewModel = arraylist[position]
        holder.bind(catagoryViewModel)
    }

    class ProjectViewHolder(val catagoryBinding: ProjectListItemBinding) : RecyclerView.ViewHolder(catagoryBinding.root) {
        fun bind(catagoryViewModel: Project) {
            this.catagoryBinding.project = catagoryViewModel
            catagoryBinding.executePendingBindings()
        }

    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setAvatar_Url(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrBlank()) {
                Glide.with(view.getContext())
                    .load(imageUrl)
                    .into(view)
            }
        }
    }
}