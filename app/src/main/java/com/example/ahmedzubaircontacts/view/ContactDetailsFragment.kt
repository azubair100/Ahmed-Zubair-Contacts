package com.example.ahmedzubaircontacts.view


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.FragmentContactDetailsBinding
import com.example.ahmedzubaircontacts.view.adapters.ContactDetailsAdapter
import com.example.ahmedzubaircontacts.viewmodel.ContactDetailsViewModel
import kotlinx.android.synthetic.main.edit_contact_address.*
import kotlinx.android.synthetic.main.edit_contact_email.*
import kotlinx.android.synthetic.main.edit_contact_phone.*

class ContactDetailsFragment : Fragment() {
    private lateinit var detailViewModel: ContactDetailsViewModel
    private var personId = 0L
    private lateinit var dataBinding: FragmentContactDetailsBinding
    private lateinit var phonesAdapter: ContactDetailsAdapter
    private lateinit var emailsAdapter: ContactDetailsAdapter
    private lateinit var addressesAdapter: ContactDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact_details, container, false)
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this).get(ContactDetailsViewModel::class.java)
        retainInstance = true
        setUpAdapters()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { personId = ContactDetailsFragmentArgs.fromBundle(it).personId }
        removeCreateNewButtons()
        setUpViews()
        observeViewModel()
        detailViewModel.getFullContact(personId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding.unbind()
    }
    
    private fun setUpAdapters(){
        phonesAdapter = ContactDetailsAdapter(arrayListOf())
        emailsAdapter = ContactDetailsAdapter(arrayListOf())
        addressesAdapter = ContactDetailsAdapter(arrayListOf())
    }

    private fun observeViewModel(){
        detailViewModel.personLiveData.observe(viewLifecycleOwner, Observer{
            it?.let { person -> dataBinding.person = person }
        })

        detailViewModel.phonesLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()) phonesAdapter.updateContactDetailsPhone(it)
            else editContactPhoneContainer.visibility = View.GONE
        })

        detailViewModel.emailsLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()) emailsAdapter.updateContactDetailsEmail(it)
            else editContactEmailContainer.visibility = View.GONE
        })
        detailViewModel.addressesLiveData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()) addressesAdapter.updateContactDetailsAddress(it)
            else editContactAddressContainer.visibility = View.GONE
        })
    }

    private fun setUpViews(){
        dataBinding.apply {
            phoneNumberRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = phonesAdapter
            }
            emailRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = emailsAdapter
            }
            addressRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = addressesAdapter
            }

            editContactsBtn.setOnClickListener {

            }

            deleteContactsBtn.setOnClickListener {
                detailViewModel.deleteContact(personId)
                it.findNavController().navigate(R.id.action_contactDetailsFragment_to_listFragment)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun removeCreateNewButtons(){
        createNewPhoneBtn.visibility = View.GONE
        createNewEmailBtn.visibility = View.GONE
        createNewAddressBtn.visibility = View.GONE
    }
}
