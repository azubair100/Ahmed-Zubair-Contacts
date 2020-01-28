package com.example.ahmedzubaircontacts.view

import android.app.Activity
import android.app.Dialog
import android.opengl.Visibility
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
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Person
import com.example.ahmedzubaircontacts.model.Phone
import com.example.ahmedzubaircontacts.viewmodel.NewContactViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.edit_contact_address.*
import kotlinx.android.synthetic.main.edit_contact_basic_info.*
import kotlinx.android.synthetic.main.edit_contact_email.*
import kotlinx.android.synthetic.main.edit_contact_phone.*
import kotlinx.android.synthetic.main.fragment_new_edit_contact.*


class NewEditContactFragment : Fragment() {

    private var personId : Long = 0L
    private lateinit var newContactViewModel: NewContactViewModel
    private lateinit var contactDetailsAdapterPhone: ContactDetailsAdapter
    private lateinit var contactDetailsAdapterEmail: ContactDetailsAdapter
    private lateinit var contactDetailsAdapterAddress: ContactDetailsAdapter
    private var phones = arrayListOf<Phone>()
    private var emails = arrayListOf<Email>()
    private var addresses = arrayListOf<Address>()
    private var phoneTextDisplay = arrayListOf<String>()
    private var emailTextDisplay = arrayListOf<String>()
    private var addressTextDisplay = arrayListOf<String>()
    /*private lateinit var phones: ArrayList<Phone>
    private lateinit var emails: ArrayList<Email>
    private lateinit var addresses: ArrayList<Address>*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newContactViewModel = ViewModelProviders.of(this).get(NewContactViewModel::class.java)
        retainInstance = true
        contactDetailsAdapterPhone = ContactDetailsAdapter(arrayListOf())
        contactDetailsAdapterEmail = ContactDetailsAdapter(arrayListOf())
        contactDetailsAdapterAddress = ContactDetailsAdapter(arrayListOf())
        /*phones = arrayListOf()
        emails = arrayListOf()
        addresses = arrayListOf()*/
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

    private fun setUpRecyclerView(){
        phoneNumberRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactDetailsAdapterPhone
        }
        emailRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactDetailsAdapterEmail
        }
        addressRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactDetailsAdapterAddress
        }
    }

    private fun setUpButtons(){
        
        createNewEmailBtn.setOnClickListener{
            newEmailAlert()
        }
        createNewPhoneBtn.setOnClickListener {
            newPhoneAlert()
        }
        createNewAddressBtn.setOnClickListener {
            newAddressAlert()
        }

        cancelBtn.setOnClickListener{
            it.findNavController().navigate(R.id.action_newEditContactFragment_to_listFragment)
        }

        saveBtn.setOnClickListener {
            hideKeyboard()
            val person = Person(firstNameETI.text.toString(), lastNameETI.text.toString(), birthdayETI.text.toString())

//            todo progress bar viewmodel
            newContactViewModel.saveContact(person, phoneTextDisplay, emailTextDisplay, addressTextDisplay)
            newContactPB.visibility = View.VISIBLE
            nestedScrollView.visibility = View.INVISIBLE
            cancelBtn.visibility = View.INVISIBLE
            saveBtn.visibility = View.INVISIBLE
        }
    }

    private fun setUpEditText(){
        saveBtn.isEnabled = false
        birthdayETI.setOnFocusChangeListener{ _, b -> if (!b) hideKeyboard() }

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

    private fun newPhoneAlert(){
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.new_phone_number_alert_layout)

        val phoneType = dialog.findViewById<TextInputEditText>(R.id.phoneTypeTIET)
        val phoneNumber = dialog.findViewById<TextInputEditText>(R.id.numberTIET)
        val saveBtn = dialog.findViewById<Button>(R.id.savePhoneBtn)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)

        phoneNumber?.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().trim().isNotEmpty()) phoneNumber.error = null
                else phoneNumber.error = getString(R.string.field_required)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveBtn?.isEnabled = s.toString().trim().isNotEmpty()
                if(!saveBtn?.isEnabled!!) saveBtn.setTextColor(resources.getColor(R.color.design_default_color_primary_dark))
            }
        })

        cancelBtn?.setOnClickListener{ dialog.dismiss() }
        saveBtn?.setOnClickListener {
            val type = phoneType?.text.toString()
            val number = phoneNumber?.text.toString()
//            phones.add(Phone(personId, type, number))
            phoneTextDisplay.add("$type: $number")
            contactDetailsAdapterPhone.updateContactDetailsList(phoneTextDisplay)
            hideKeyboard()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun newEmailAlert(){
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.new_email_alert_layout)

        val emailType = dialog.findViewById<TextInputEditText>(R.id.emailTypeTIET)
        val emailAddress = dialog.findViewById<TextInputEditText>(R.id.emailTIET)
        val saveBtn = dialog.findViewById<Button>(R.id.saveEmailBtn)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)

        emailAddress?.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().trim().isNotEmpty()) emailAddress.error = null
                else emailAddress.error = getString(R.string.field_required)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveBtn?.isEnabled = s.toString().trim().isNotEmpty()
                if(!saveBtn?.isEnabled!!) saveBtn.setTextColor(resources.getColor(R.color.design_default_color_primary_dark))
            }
        })

        cancelBtn?.setOnClickListener{ dialog.dismiss() }
        saveBtn?.setOnClickListener {
            val type = emailType?.text.toString()
            val email = emailAddress?.text.toString()
            emailTextDisplay.add("$type: $email")
            contactDetailsAdapterEmail.updateContactDetailsList(emailTextDisplay)
            hideKeyboard()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun newAddressAlert(){
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.new_address_alert_layout)

        val saveBtn = dialog.findViewById<Button>(R.id.saveAddressBtn)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)

        val addressType = dialog.findViewById<TextInputEditText>(R.id.addressTypeTIET)
        val street = dialog.findViewById<TextInputEditText>(R.id.streetTIET)
        val city = dialog.findViewById<TextInputEditText>(R.id.cityTIET)
        val state = dialog.findViewById<TextInputEditText>(R.id.stateTIET)
        val zip = dialog.findViewById<TextInputEditText>(R.id.zipTIET)

        street?.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().trim().isNotEmpty()) street.error = null
                else street.error = getString(R.string.field_required)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveBtn?.isEnabled = s.toString().trim().isNotEmpty()
                if(!saveBtn?.isEnabled!!) saveBtn.setTextColor(resources.getColor(R.color.design_default_color_primary_dark))
            }
        })

        cancelBtn?.setOnClickListener{ dialog.dismiss() }
        saveBtn?.setOnClickListener {
            val type = addressType.text.toString()
            val streetName = street?.text.toString()
            val cityName = city?.text.toString()
            val stateName = state?.text.toString()
            val zipName = zip?.text.toString()
            addressTextDisplay.add("$type: $streetName, $cityName, $stateName $zipName")
            contactDetailsAdapterAddress.updateContactDetailsList(addressTextDisplay)
            hideKeyboard()
            dialog.dismiss()

        }
        dialog.show()
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
