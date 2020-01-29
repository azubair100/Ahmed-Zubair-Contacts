package com.example.ahmedzubaircontacts.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.NewContactDetailRowBinding
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Phone

class NewContactAdapter(var list: ArrayList<Any>):
    RecyclerView.Adapter<NewContactAdapter.ContactDetailsViewHolder>(){

    fun updateNewContactPhone(newList: ArrayList<Phone>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateNewContactDetailEmail(newList: ArrayList<Email>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateNewContactDetailAddress(newList: ArrayList<Address>) {
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
        val obj = list[position]
        var detail = ""

        when(obj){
            is Phone -> {
//                isPhone = true
                detail = obj.type + " " + obj.number
//                holder.view.phoneListener = this
            }
            is Email -> {
                detail = obj.type + " " + obj.address
            }
            is Address -> {
                detail = obj.type + " " + obj.street + ", " + obj.city +
                        ", " + obj.state + "-" + obj.zip
            }
        }
        holder.view.contactDetail = detail


        holder.view.clearRowBtn.setOnClickListener{
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
            holder.itemView.visibility = View.GONE
        }

    }

    class ContactDetailsViewHolder(var view: NewContactDetailRowBinding): RecyclerView.ViewHolder(view.root)

}