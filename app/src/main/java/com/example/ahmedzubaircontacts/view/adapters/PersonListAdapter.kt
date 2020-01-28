package com.example.ahmedzubaircontacts.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.RvContactRowBinding
import com.example.ahmedzubaircontacts.model.Person
import com.example.ahmedzubaircontacts.view.ListFragmentDirections
import com.example.ahmedzubaircontacts.view.clicklisteners.PersonClickListener
import kotlinx.android.synthetic.main.rv_contact_row.view.*

class PersonListAdapter(private val personList: ArrayList<Person>):
    RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>(), PersonClickListener{

    fun updatePersonList(newList: List<Person>){
        personList.clear()
        personList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder =
        PersonViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
                R.layout.rv_contact_row, parent, false)
        )


    override fun getItemCount(): Int = personList.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.view.person = personList[position]
        holder.view.listener = this
    }

    override fun onPersonClicked(view: View) {
        val action = ListFragmentDirections.actionListFragmentToContactDetailsFragment()
        action.personId = view.personId.toString().toLong()
        Navigation.findNavController(view).navigate(action)
    }

    class PersonViewHolder(var view: RvContactRowBinding) : RecyclerView.ViewHolder(view.root)

}