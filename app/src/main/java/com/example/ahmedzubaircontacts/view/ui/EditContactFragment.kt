package com.example.ahmedzubaircontacts.view.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.FragmentNewContactBinding
import com.example.ahmedzubaircontacts.model.Address
import com.example.ahmedzubaircontacts.model.Email
import com.example.ahmedzubaircontacts.model.Person
import com.example.ahmedzubaircontacts.model.Phone
import com.example.ahmedzubaircontacts.util.Util
import com.example.ahmedzubaircontacts.view.adapters.NewContactAdapter
import com.example.ahmedzubaircontacts.viewmodel.ContactDetailsViewModel
import com.example.ahmedzubaircontacts.viewmodel.NewContactViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_new_contact.*
import kotlinx.android.synthetic.main.section_person.*
import kotlinx.android.synthetic.main.section_person_address.*
import kotlinx.android.synthetic.main.section_person_email.*
import kotlinx.android.synthetic.main.section_person_phone.*
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST")
class EditContactFragment : Fragment() {
    private var personId: Long = 0L
    private lateinit var detailViewModel: ContactDetailsViewModel
    private lateinit var newContactViewModel: NewContactViewModel
    private lateinit var dataBinding: FragmentNewContactBinding
    private lateinit var newContactAdapterPhone: NewContactAdapter
    private lateinit var newContactAdapterEmail: NewContactAdapter
    private lateinit var newContactAdapterAddress: NewContactAdapter
    private lateinit var currentPerson: Person
    private var bus = Bus()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newContactViewModel = ViewModelProviders.of(this).get(NewContactViewModel::class.java)
        detailViewModel = ViewModelProviders.of(this).get(ContactDetailsViewModel::class.java)
        retainInstance = true
        setUpAdapters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_contact, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { personId = EditContactFragmentArgs.fromBundle(it).personId }
        setUpViews()
        setUpRecyclerView()
        observeDetailViewModel()
        observeNewContactViewModel()
        detailViewModel.getFullContact(personId)
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

    private fun observeNewContactViewModel() {
        newContactViewModel.contactId.observe(viewLifecycleOwner, Observer {
            if (it != null || it != 0L) {
                val action =
                    EditContactFragmentDirections.actionEditContactFragmentToContactDetailsFragment()
                action.personId = personId
                findNavController().navigate(action)
            } else {
                Snackbar.make(it, getString(R.string.error_contact_saving), Snackbar.LENGTH_LONG)
                    .show()
                newContactPB.visibility = View.GONE
                nestedScrollView.visibility = View.VISIBLE
                cancelBtn.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
            }
        })
    }

    private fun setUpAdapters() {
        newContactAdapterPhone = NewContactAdapter(arrayListOf())
        newContactAdapterEmail = NewContactAdapter(arrayListOf())
        newContactAdapterAddress = NewContactAdapter(arrayListOf())
    }

    private fun observeDetailViewModel() {
        detailViewModel.personLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { person ->
                currentPerson = person
                dataBinding.person = currentPerson
            }
        })

        detailViewModel.phonesLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                newContactAdapterPhone.updateNewContactPhone(it)
            }
        })

        detailViewModel.emailsLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) newContactAdapterEmail.updateNewContactDetailEmail(it)
        })
        detailViewModel.addressesLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) newContactAdapterAddress.updateNewContactDetailAddress(it)
        })
    }

    private fun setUpViews() {
        dataBinding.apply {
            setUpButtons()
            setUpToolBar()
            setUpDatePicker()
        }
    }

    private fun setUpDatePicker(){
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        birthdayPick.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH),
            DatePicker.OnDateChangedListener { datePicker, year, month, day->
                birthdayTV.text = "${month + 1}-$day-$year"
            })
    }

    private fun setUpToolBar() {
        newFragmentTB.apply {
            inflateMenu(R.menu.help)
            setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {

                        R.id.goToHelpFragment -> {
                            view?. let{
                                Navigation.findNavController(it).navigate(EditContactFragmentDirections.actionEditContactFragmentToHelpFragment())
                            }
                        }
                    }
                    return false
                }
            })
        }
    }

    private fun setUpButtons() {
        saveBtn.isEnabled = true
        createNewEmailBtn.setOnClickListener {
            Util.newEmailAlert(context!!, bus)
        }
        createNewPhoneBtn.setOnClickListener {
            Util.newPhoneAlert(context!!, bus)
        }
        createNewAddressBtn.setOnClickListener {
            Util.newAddressAlert(context!!, bus)
        }

        cancelBtn.setOnClickListener {
            val action =
                EditContactFragmentDirections.actionEditContactFragmentToContactDetailsFragment()
            action.personId = personId
            findNavController().navigate(action)
        }

        saveBtn.setOnClickListener {
            if (currentPerson == null) {
                currentPerson = Person(
                    firstNameETI.text.toString(),
                    lastNameETI.text.toString(),
                    birthdayTV.text.toString()
                )
            } else {
                currentPerson.firstName = firstNameETI.text.toString()
                currentPerson.lastName = lastNameETI.text.toString()
                currentPerson.birthday = birthdayTV.text.toString()
            }

            val phones = newContactAdapterPhone.list as? ArrayList<Phone>
            val emails = newContactAdapterEmail.list as? ArrayList<Email>
            val addresses = newContactAdapterAddress.list as? ArrayList<Address>

            newContactViewModel.saveContact(currentPerson, phones, emails, addresses, true)
            newContactPB.visibility = View.VISIBLE
            nestedScrollView.visibility = View.INVISIBLE
            cancelBtn.visibility = View.INVISIBLE
            saveBtn.visibility = View.INVISIBLE
        }
    }

    private fun setUpRecyclerView() {
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

    @Subscribe
    fun getNewPhone(phone: Phone) {
        var phones = newContactAdapterPhone.list as? ArrayList<Phone>
        phones?.add(phone)
        newContactAdapterPhone.notifyDataSetChanged()
    }

    @Subscribe
    fun getNewEmail(email: Email) {
        var emails = newContactAdapterEmail.list as? ArrayList<Email>
        emails?.add(email)
        newContactAdapterPhone.notifyDataSetChanged()
    }

    @Subscribe
    fun getNewAddress(address: Address) {
        var addresses = newContactAdapterAddress.list as? ArrayList<Address>
        addresses?.add(address)
        newContactAdapterAddress.notifyDataSetChanged()
    }

}
