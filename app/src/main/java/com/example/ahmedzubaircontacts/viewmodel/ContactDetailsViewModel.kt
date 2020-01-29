package com.example.ahmedzubaircontacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ahmedzubaircontacts.model.*
import kotlinx.coroutines.launch

class ContactDetailsViewModel(application: Application) : BaseViewModel(application){
    val personLiveData: MutableLiveData<Person> = MutableLiveData()
    val phonesLiveData: MutableLiveData<ArrayList<Phone>> = MutableLiveData()
    val emailsLiveData: MutableLiveData<ArrayList<Email>> = MutableLiveData()
    val addressesLiveData: MutableLiveData<ArrayList<Address>> = MutableLiveData()

    fun getFullContact(personId: Long){
        launch {
            personLiveData.value = ContactDatabase(getApplication()).personDAO().getPersonById(personId)
            phonesLiveData.value = ArrayList(ContactDatabase(getApplication()).phoneDAO().findAllPhonesByPersonId(personId))
            emailsLiveData.value = ArrayList(ContactDatabase(getApplication()).emailDAO().findAllEmailsByPersonId(personId))
            addressesLiveData.value = ArrayList(ContactDatabase(getApplication()).addressDAO().findAllAddressesByPersonId(personId))
        }
    }

    fun deleteContact(personId: Long){
        launch {
            ContactDatabase(getApplication()).personDAO().deletePerson(personId)
        }
    }
}