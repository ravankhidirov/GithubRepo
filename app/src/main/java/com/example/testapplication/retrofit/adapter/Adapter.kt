package com.example.testapplication.retrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.retrofit.Repository

class Adapter(list1: List<Repository>,context:Context): RecyclerView.Adapter<Adapter.ViewHolder>() {
    var list:List<Repository>
    val mycontext:Context
    init{
        list = list1
        mycontext = context
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var profile = itemView.findViewById<ImageView>(R.id.profileImage)
        var name = itemView.findViewById<TextView>(R.id.name)
        var forks = itemView.findViewById<TextView>(R.id.forks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.repo_item,parent,false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = list[position]

        Glide.with(mycontext)
            .load(repo.owner.avatar_url)
            .into(holder.profile)
        holder.name.text = repo.name
        holder.forks.text = repo.forks.toString()

    }
}