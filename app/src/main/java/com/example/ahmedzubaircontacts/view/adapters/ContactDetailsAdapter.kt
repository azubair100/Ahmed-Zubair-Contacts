package com.example.ahmedzubaircontacts.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.RowContactDetailsBinding
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Phone

class ContactDetailsAdapter(private val list: ArrayList<Any>) :
    RecyclerView.Adapter<ContactDetailsAdapter.ContactDetailsViewHolder>() {

    var onPhoneItemClick: ((Phone) -> Unit)? = null
    var onEmailItemClick: ((Email) -> Unit)? = null
    var onAddressItemClick: ((Address) -> Unit)? = null

    var isPhone = false
    var isEmail = false
    var isAddress = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactDetailsViewHolder =
        ContactDetailsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_contact_details, parent, false
            )
        )


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactDetailsViewHolder, position: Int) {
        val obj = list[position]
        var detail = ""
        when (obj) {
            is Phone -> {
                isPhone = true
                detail = obj.type + ": " + obj.number
            }
            is Email -> {
                isEmail = true
                detail = obj.type + ": " + obj.address
            }
            is Address -> {
                isAddress = true
                detail = obj.type + ": " + obj.street + ", " + obj.city +
                        ", " + obj.state + "-" + obj.zip
            }
        }
        holder.view.detail = detail

    }

    fun updateContactDetailsPhone(newList: List<Phone>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateContactDetailsEmail(newList: List<Email>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateContactDetailsAddress(newList: List<Address>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ContactDetailsViewHolder(var view: RowContactDetailsBinding) :
        RecyclerView.ViewHolder(view.root) {
        init {
            itemView.setOnClickListener {

                if (isPhone) {
                    val phones = list as? ArrayList<Phone>
                    onPhoneItemClick?.invoke(phones!![adapterPosition])
                }

                if (isEmail) {
                    val emails = list as? ArrayList<Email>
                    onEmailItemClick?.invoke(emails!![adapterPosition])
                }

                if (isAddress) {
                    val addresses = list as? ArrayList<Address>
                    onAddressItemClick?.invoke(addresses!![adapterPosition])
                }
            }
        }
    }


}