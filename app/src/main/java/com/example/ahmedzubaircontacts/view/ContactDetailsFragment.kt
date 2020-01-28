package com.example.ahmedzubaircontacts.view


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.FragmentContactDetailsBinding
import com.example.ahmedzubaircontacts.model.Person
import com.example.ahmedzubaircontacts.viewmodel.ContactDetailsViewModel
import kotlinx.android.synthetic.main.edit_contact_address.*
import kotlinx.android.synthetic.main.edit_contact_email.*
import kotlinx.android.synthetic.main.edit_contact_phone.*

class ContactDetailsFragment : Fragment() {
    private lateinit var detailViewModel: ContactDetailsViewModel
    private var personId = 0L
    private lateinit var dataBinding: FragmentContactDetailsBinding
    private var currentPerson: Person? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false)
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this).get(ContactDetailsViewModel::class.java)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { personId = ContactDetailsFragmentArgs.fromBundle(it).personId }
        disableCreateNewButtons()
    }

    private fun observeViewModel(){

    }

    @SuppressLint("RestrictedApi")
    private fun disableCreateNewButtons(){
        createNewPhoneBtn.visibility = View.GONE
        createNewEmailBtn.visibility = View.GONE
        createNewAddressBtn.visibility = View.GONE
    }
}
