package com.example.ahmedzubaircontacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ahmedzubaircontacts.model.*
import kotlinx.coroutines.launch

class NewContactViewModel(application: Application) : BaseViewModel(application) {
    var contactId: MutableLiveData<Long> = MutableLiveData()

    fun saveContact(person: Person, phones: ArrayList<String>, emails: ArrayList<String>, addresses: ArrayList<String>){
        launch {
            val personDao = ContactDatabase(getApplication()).personDAO()
            val personId = personDao.insert(person)

            personId.let {
                if(phones.size > 0){
                    val phoneDao = ContactDatabase(getApplication()).phoneDAO()
                    val phoneList = returnPhoneList(phones, personId)
                    phoneDao.insertAll(*phoneList.toTypedArray())
                }
                if(emails.size > 0){
                    val emailDao = ContactDatabase(getApplication()).emailDAO()
                    val emailList = returnEmailList(emails, personId)
                    emailDao.insertAll(*emailList.toTypedArray())
                }
                if(addresses.size > 0){
                    val addressDao = ContactDatabase(getApplication()).addressDAO()
                    val addressList = returnAddressList(addresses, personId)
                    addressDao.insertAll(*addressList.toTypedArray())
                }
                contactId.value = personId
            }
        }
    }

    private fun returnPhoneList(list: ArrayList<String>, personId: Long): ArrayList<Phone>{
        val phones = arrayListOf<Phone>()
        for(phoneString in list){
            val phoneDetail: List<String> = phoneString.split(" ".toRegex())
            phones.add(Phone(personId, phoneDetail[0], phoneDetail[1]))
        }
        return phones
    }

    private fun returnEmailList(list: ArrayList<String>, personId: Long): ArrayList<Email>{
        val emails = arrayListOf<Email>()
        for(emailString in list){
            val emailDetail: List<String> = emailString.split(" ".toRegex())
            emails.add(Email(personId, emailDetail[0], emailDetail[1]))
        }
        return emails
    }

    private fun returnAddressList(list: ArrayList<String>, personId: Long): ArrayList<Address>{
        val addresses = arrayListOf<Address>()
        for(addressString in list){
            val addressDetail: List<String> = addressString.split(" ".toRegex())
            addresses.add(Address(personId, addressDetail[0], addressDetail[1], addressDetail[2], addressDetail[3], addressDetail[4]))
        }
        return addresses
    }


}