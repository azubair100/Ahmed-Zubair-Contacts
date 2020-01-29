package com.example.ahmedzubaircontacts.view

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.model.BusEvent
import com.example.ahmedzubaircontacts.model.Person
import com.example.ahmedzubaircontacts.util.AlertUtil
import com.example.ahmedzubaircontacts.view.adapters.NewContactAdapter
import com.example.ahmedzubaircontacts.viewmodel.NewContactViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.edit_contact_address.*
import kotlinx.android.synthetic.main.edit_contact_basic_info.*
import kotlinx.android.synthetic.main.edit_contact_email.*
import kotlinx.android.synthetic.main.edit_contact_phone.*
import kotlinx.android.synthetic.main.fragment_new_edit_contact.*


class NewEditContactFragment : Fragment() {

    private var personId : Long = 0L
    private lateinit var newContactViewModel: NewContactViewModel
    private lateinit var newContactAdapterPhone: NewContactAdapter
    private lateinit var newContactAdapterEmail: NewContactAdapter
    private lateinit var newContactAdapterAddress: NewContactAdapter
    private var phoneTextDisplay = arrayListOf<String>()
    private var emailTextDisplay = arrayListOf<String>()
    private var addressTextDisplay = arrayListOf<String>()
    private var bus = Bus()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newContactViewModel = ViewModelProviders.of(this).get(NewContactViewModel::class.java)
        retainInstance = true
        newContactAdapterPhone = NewContactAdapter(arrayListOf())
        newContactAdapterEmail = NewContactAdapter(arrayListOf())
        newContactAdapterAddress = NewContactAdapter(arrayListOf())
    }

    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onStop() {
        super.onStop()
        bus.unregister(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_new_edit_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { personId = NewEditContactFragmentArgs.fromBundle(it).personId }
        observeViewModel()
        setUpEditText()
        setUpButtons()
        setUpRecyclerView()
    }

    private fun observeViewModel(){
        newContactViewModel.contactId.observe(viewLifecycleOwner, Observer {
            if(it != null || it != 0L){
                findNavController().navigate(R.id.action_newEditContactFragment_to_listFragment)
            }
            else{
                Snackbar.make(it, "An error occurred! Contact could not be saved. Please try again.", Snackbar.LENGTH_LONG).show()
                newContactPB.visibility = View.GONE
                nestedScrollView.visibility = View.VISIBLE
                cancelBtn.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
            }
        })
    }

    @Subscribe
    fun getNewPhone(phone: BusEvent){
        if(phone.dataType == "phone") {
            phoneTextDisplay.add(phone.data)
            newContactAdapterPhone.updateContactDetailsList(phoneTextDisplay)
        }
    }

    @Subscribe
    fun getNewEmail(email: BusEvent){
        if(email.dataType == "email")
        emailTextDisplay.add(email.data)
        newContactAdapterEmail.updateContactDetailsList(emailTextDisplay)
    }

    @Subscribe
    fun getNewAddress(address: BusEvent){
        if(address.dataType == "address"){
            addressTextDisplay.add(address.data)
            newContactAdapterAddress.updateContactDetailsList(addressTextDisplay)
        }
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

    private fun setUpButtons(){
        
        createNewEmailBtn.setOnClickListener{
            AlertUtil.newEmailAlert(context!!, bus)
        }
        createNewPhoneBtn.setOnClickListener {
            AlertUtil.newPhoneAlert(context!!, bus)
        }
        createNewAddressBtn.setOnClickListener {
            AlertUtil.newAddressAlert(context!!, bus)
        }

        cancelBtn.setOnClickListener{
            it.findNavController().navigate(R.id.action_newEditContactFragment_to_listFragment)
        }

        saveBtn.setOnClickListener {
            hideKeyboard()
            val person = Person(firstNameETI.text.toString(), lastNameETI.text.toString(), birthdayETI.text.toString())

            newContactViewModel.saveContact(person, newContactAdapterPhone.list, newContactAdapterEmail.list, newContactAdapterAddress.list)
            newContactPB.visibility = View.VISIBLE
            nestedScrollView.visibility = View.INVISIBLE
            cancelBtn.visibility = View.INVISIBLE
            saveBtn.visibility = View.INVISIBLE
        }
    }

    private fun setUpEditText(){
        saveBtn.isEnabled = false
        if(personId == 0L){
        firstNameETI.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().trim().isNotEmpty()) firstNameETI.error = null
                else firstNameETI.error = getString(R.string.field_required)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveBtn.isEnabled = s.toString().trim().isNotEmpty()
                if(!saveBtn.isEnabled) saveBtn.setTextColor(resources.getColor(R.color.design_default_color_primary_dark))
            }
        })
    }
    }


    private fun hideKeyboard() {
        val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
