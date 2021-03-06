package com.example.ahmedzubaircontacts.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.RowAddNewContactDetailsBinding
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Phone

class NewContactAdapter(var list: ArrayList<Any>) :
    RecyclerView.Adapter<NewContactAdapter.ContactDetailsViewHolder>() {

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
                R.layout.row_add_new_contact_details, parent, false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactDetailsViewHolder, position: Int) {
        val obj = list[position]
        var detail = ""

        when (obj) {
            is Phone -> detail = obj.type + ": " + obj.number
            is Email -> detail = obj.type + ": " + obj.address
            is Address -> detail =
                obj.type + ": " + obj.street + ", " + obj.city + ", " + obj.state + "-" + obj.zip
        }
        holder.view.contactDetail = detail


        holder.view.clearRowBtn.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
            holder.itemView.visibility = View.GONE
        }

    }

    class ContactDetailsViewHolder(var view: RowAddNewContactDetailsBinding) :
        RecyclerView.ViewHolder(view.root)

}