package com.example.ahmedzubaircontacts.view.ui

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.FragmentNewContactBinding
import com.example.ahmedzubaircontacts.model.*
import com.example.ahmedzubaircontacts.util.Util
import com.example.ahmedzubaircontacts.view.adapters.NewContactAdapter
import com.example.ahmedzubaircontacts.viewmodel.NewContactViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.edit_contact_address.*
import kotlinx.android.synthetic.main.edit_contact_basic_info.*
import kotlinx.android.synthetic.main.edit_contact_email.*
import kotlinx.android.synthetic.main.edit_contact_phone.*
import kotlinx.android.synthetic.main.fragment_new_contact.*


class NewContactFragment : Fragment() {

    private var personId : Long = 0L
    private lateinit var newContactViewModel: NewContactViewModel
    private lateinit var newContactAdapterPhone: NewContactAdapter
    private lateinit var newContactAdapterEmail: NewContactAdapter
    private lateinit var newContactAdapterAddress: NewContactAdapter
    private lateinit var dataBinding: FragmentNewContactBinding
    private var phonesDisplay = arrayListOf<Phone>()
    private var emailsDisplay = arrayListOf<Email>()
    private var addressesDisplay = arrayListOf<Address>()
    private var bus = Bus()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newContactViewModel = ViewModelProviders.of(this).get(NewContactViewModel::class.java)
        retainInstance = true
        setUpAdapters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_contact, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { personId = NewContactFragmentArgs.fromBundle(it).personId }
        setUpViews()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onStop() {
        super.onStop()
        bus.unregister(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding.unbind()
    }

    private fun setUpViews(){
        dataBinding.apply {
            makeFirstNameMandatory()
            setUpButtons()
            setUpRecyclerView()
        }
    }

    private fun setUpAdapters() {
        newContactAdapterPhone = NewContactAdapter(arrayListOf())
        newContactAdapterEmail = NewContactAdapter(arrayListOf())
        newContactAdapterAddress = NewContactAdapter(arrayListOf())
    }

    private fun observeViewModel(){
        newContactViewModel.contactId.observe(viewLifecycleOwner, Observer {
            if(it != null || it != 0L){
                findNavController().navigate(R.id.action_newEditContactFragment_to_listFragment)
            }
            else{
                Snackbar.make(it, getString(R.string.error_contact_saving), Snackbar.LENGTH_LONG).show()
                newContactPB.visibility = View.GONE
                nestedScrollView.visibility = View.VISIBLE
                cancelBtn.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
            }
        })
    }
    
    private fun setUpRecyclerView(){
        phoneNumberRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newContactAdapterPhone
        }
        emailRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newContactAdapterEmail
        }
        addressRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newContactAdapterAddress
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setUpButtons(){
        createNewEmailBtn.setOnClickListener{
            Util.newEmailAlert(context!!, bus)
        }
        createNewPhoneBtn.setOnClickListener {
            Util.newPhoneAlert(context!!, bus)
        }
        createNewAddressBtn.setOnClickListener {
            Util.newAddressAlert(context!!, bus)
        }

        cancelBtn.setOnClickListener{
            it.findNavController().navigate(R.id.action_newEditContactFragment_to_listFragment)
        }

        saveBtn.setOnClickListener {
            hideKeyboard()
            val person = Person(firstNameETI.text.toString(), lastNameETI.text.toString(), birthdayETI.text.toString())

            val phones = newContactAdapterPhone.list as? ArrayList<Phone>
            val emails = newContactAdapterEmail.list as? ArrayList<Email>
            val addresses = newContactAdapterAddress.list as? ArrayList<Address>
            newContactViewModel.saveContact(person, phones, emails, addresses, false)
            newContactPB.visibility = View.VISIBLE
            nestedScrollView.visibility = View.INVISIBLE
            cancelBtn.visibility = View.INVISIBLE
            saveBtn.visibility = View.INVISIBLE
        }
    }

    private fun makeFirstNameMandatory(){
        firstNameETI.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().trim().isNotEmpty()) firstNameETI.error = null
                else firstNameETI.error = getString(R.string.field_required)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveBtn.isEnabled = s.toString().trim().isNotEmpty()
            }
        })
    }

    private fun hideKeyboard() {
        val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    @Subscribe
    fun getNewPhone(phone: Phone){
        var phones = newContactAdapterPhone.list as? ArrayList<Phone>
        phones?.add(phone)
        newContactAdapterPhone.notifyDataSetChanged()
    }

    @Subscribe
    fun getNewEmail(email: Email){
        var emails = newContactAdapterEmail.list as? ArrayList<Email>
        emails?.add(email)
        newContactAdapterPhone.notifyDataSetChanged()
    }

    @Subscribe
    fun getNewAddress(address: Address){
        var addresses = newContactAdapterAddress.list as? ArrayList<Address>
        addresses?.add(address)
        newContactAdapterAddress.notifyDataSetChanged()
    }


}
