package com.android.mvvmdatabind2.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.models.Membership
import com.squareup.picasso.Picasso

class MembershipsAdapter : RecyclerView.Adapter<MembershipsAdapter.ViewHolder>() {

    var membershipList = ArrayList<Membership>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.item_imageView)
        var title: TextView = itemView.findViewById(R.id.item_title)
        var bookedDate: TextView = itemView.findViewById(R.id.item_BookedDate)
        var endDate: TextView = itemView.findViewById(R.id.item_EndDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_membership, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.image.setImageURI(membershipList[position].image.toUri())
        Picasso.get().load(membershipList[position].image.toUri()).noPlaceholder()
            .into(holder.image)
        holder.title.text = membershipList[position].title
        holder.bookedDate.text = membershipList[position].bookeddate
        holder.endDate.text = membershipList[position].enddate
    }

    override fun getItemCount() = membershipList.size
}