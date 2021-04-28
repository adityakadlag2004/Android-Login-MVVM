package com.android.mvvmdatabind2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.models.SearchBusiness
import com.squareup.picasso.Picasso

class SearchAdapter(var list:ArrayList<SearchBusiness>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.business_name_sea)
        var type: TextView = itemView.findViewById(R.id.business_type_sea)
        var desc: TextView = itemView.findViewById(R.id.desc_business_sea)
        var address: TextView = itemView.findViewById(R.id.business_address_sea)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.type.text = list[position].type
        holder.desc.text = list[position].desc
        holder.address.text = list[position].address
    }

    override fun getItemCount()=list.size
}