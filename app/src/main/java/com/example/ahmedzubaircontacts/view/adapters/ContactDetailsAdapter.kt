package com.example.ahmedzubaircontacts.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.RvDisplayContactsGenericRowBinding
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Phone
import com.example.ahmedzubaircontacts.view.clicklisteners.AddressClickListener
import com.example.ahmedzubaircontacts.view.clicklisteners.EmailClickListener
import com.example.ahmedzubaircontacts.view.clicklisteners.PhoneClickListener

class ContactDetailsAdapter(private val list: ArrayList<Any>):
    RecyclerView.Adapter<ContactDetailsAdapter.ContactDetailsViewHolder>(),
    PhoneClickListener, EmailClickListener, AddressClickListener{

    private var isPhone = false
    private var isEmail = false
    private var isAddress = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactDetailsViewHolder =
        ContactDetailsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_display_contacts_generic_row, parent, false
            )
        )


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactDetailsViewHolder, position: Int) {
        val obj = list[position]
        var detail = ""
        when(obj){
            is Phone -> {
                isPhone = true
                detail = obj.type + ": " + obj.number
                holder.view.phoneListener = this
            }
            is Email -> {
                isEmail = true
                detail = obj.type + ": " + obj.address
                holder.view.emailListener = this
            }
            is Address -> {
                isAddress = true
                detail = obj.type + ": " + obj.street + ", " + obj.city +
                        ", " + obj.state + "-" + obj.zip
                holder.view.addressListener = this
            }
        }
        holder.view.detail = detail

    }

    override fun onPhoneClicked(view: View) {
        if(isPhone){

        }
    }

    override fun onEmailClicked(view: View) {
        if(isEmail){

        }
    }

    override fun onAddressClicked(view: View) {
        if(isAddress){

        }
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

    class ContactDetailsViewHolder(var view: RvDisplayContactsGenericRowBinding): RecyclerView.ViewHolder(view.root)


}