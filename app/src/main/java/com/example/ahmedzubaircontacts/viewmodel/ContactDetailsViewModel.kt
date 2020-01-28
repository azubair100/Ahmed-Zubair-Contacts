package com.example.ahmedzubaircontacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ahmedzubaircontacts.model.*
import kotlinx.coroutines.launch

class ContactDetailsViewModel(application: Application) : BaseViewModel(application){
    val personLiveData: MutableLiveData<Person> = MutableLiveData()
    val phonesLiveData: MutableLiveData<List<Phone>> = MutableLiveData()
    val emailsLiveData: MutableLiveData<List<Email>> = MutableLiveData()
    val addressesLiveData: MutableLiveData<List<Address>> = MutableLiveData()

    fun getFullContact(personId: Long){
        launch {
            personLiveData.value = ContactDatabase(getApplication()).personDAO().getPersonById(personId)
            phonesLiveData.value = ContactDatabase(getApplication()).phoneDAO().findAllPhonesByPersonId(personId)
            emailsLiveData.value = ContactDatabase(getApplication()).emailDAO().findAllEmailsByPersonId(personId)
            addressesLiveData.value = ContactDatabase(getApplication()).addressDAO().findAllAddressesByPersonId(personId)
        }
    }
}