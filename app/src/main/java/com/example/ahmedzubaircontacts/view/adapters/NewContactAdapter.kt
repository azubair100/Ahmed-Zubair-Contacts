package com.example.ahmedzubaircontacts.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.NewContactDetailRowBinding

class NewContactAdapter(private val list: ArrayList<String>):
RecyclerView.Adapter<NewContactAdapter.ContactDetailsViewHolder>(){

    fun updateContactDetailsList(newList: List<String>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactDetailsViewHolder =
        ContactDetailsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.new_contact_detail_row, parent, false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactDetailsViewHolder, position: Int) {
        holder.view.contactDetail = list[position]
        holder.view.clearRowBtn.setOnClickListener{
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
            holder.itemView.visibility = View.GONE
        }

    }

    class ContactDetailsViewHolder(var view: NewContactDetailRowBinding): RecyclerView.ViewHolder(view.root)

}