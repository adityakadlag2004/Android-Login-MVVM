package com.android.mvvmdatabind2.retrofit

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvmdatabind2.R

class UserAdapter(private val userList:List<User>) :RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val email: TextView = itemView.findViewById(R.id.txv_email)
        val id:TextView=itemView.findViewById(R.id.txv_id)
        val first:TextView=itemView.findViewById(R.id.txv_first)
        val last:TextView=itemView.findViewById(R.id.txv_last)
        val img:ImageView=itemView.findViewById(R.id.img_main)

        var user: User? = null


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.email.text=userList[position].email.toString()
        holder.first.text=userList[position].first_name.toString()
        holder.last.text=userList[position].last_name.toString()
        holder.id.text=userList[position].id.toString()
        holder.user=userList[position]
        holder.img.setImageURI(Uri.parse(userList[position].avatar.toString()))
    }

    override fun getItemCount()=userList.size
}