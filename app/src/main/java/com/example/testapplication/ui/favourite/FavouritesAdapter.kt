package com.example.testapplication.ui.favourite

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.room.repository.RepositoryEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavouritesAdapter(context:Context,list1: List<RepositoryEntity>): RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {
    private var list:List<RepositoryEntity>
    private var mycontext:Context

    init{
        list = list1
        mycontext = context
    }

    private var onItemClickListener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }


    private var onRemoveButtonClickListener:((RepositoryEntity)->Unit)? = null
    fun setOnRemoveButtonClickListener(listener: (RepositoryEntity) -> Unit){
        onRemoveButtonClickListener = listener
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var profile = itemView.findViewById<ImageView>(R.id.profileImage)
        var name = itemView.findViewById<TextView>(R.id.name)
        var language = itemView.findViewById<TextView>(R.id.language)
        var fab = itemView.findViewById<FloatingActionButton>(R.id.fab)

        var forks = itemView.findViewById<TextView>(R.id.forks)
        var description = itemView.findViewById<TextView>(R.id.description)
        var html_url = itemView.findViewById<TextView>(R.id.html_url)
        var watch = itemView.findViewById<TextView>(R.id.watch)

        init{
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(position)
                }
            }

            fab.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRemoveButtonClickListener?.invoke(list[position])
                }

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.fav_item,parent,false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = list[position]

        Glide.with(mycontext)
            .load(repo.avatar_url)
            .into(holder.profile)
        holder.name.text = repo.name
        holder.language.text = repo.language
        holder.fab.setImageResource(R.drawable.ic_remove)

        holder.forks.text = repo.forks.toString()
        if (repo.description!=null){
            if (repo.description.length > 60){
                holder.description.text = "${repo.description.substring(0,60)}..."
            }else{
                holder.description.text = "${repo.description}..."
            }
        }
        holder.html_url.text = "${repo.html_url.substring(0,25)}..."
        holder.watch.text = repo.watch.toString()

    }

}