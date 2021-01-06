package com.ksw.roomdatabase.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksw.roomdatabase.R
import com.ksw.roomdatabase.data.User
import kotlinx.android.synthetic.main.custom_row.view.*

/**
 * Created by KSW on 2021-01-06
 */

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var userList = emptyList<User>()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.tv_txt.text = currentItem.id.toString()
        holder.itemView.tv_firstname.text = currentItem.firstName
        holder.itemView.tv_lastname.text = currentItem.lastName
        holder.itemView.tv_age.text = currentItem.age.toString()
    }

    fun setData(user : List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }

}