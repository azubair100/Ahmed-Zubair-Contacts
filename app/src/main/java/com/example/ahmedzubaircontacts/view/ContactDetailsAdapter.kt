package com.example.ahmedzubaircontacts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.NewContactDetailRowBinding
import com.example.ahmedzubaircontacts.view.clicklisteners.ContactDetailsListener

class ContactDetailsAdapter(private val list: ArrayList<String>):
RecyclerView.Adapter<ContactDetailsAdapter.ContactDetailsViewHolder>(), ContactDetailsListener{

    fun updateContactDetailsList(newList: List<String>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactDetailsViewHolder =
        ContactDetailsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.new_contact_detail_row, parent, false))

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

    override fun onContactDetailsClicked(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ContactDetailsViewHolder(var view: NewContactDetailRowBinding): RecyclerView.ViewHolder(view.root)

}