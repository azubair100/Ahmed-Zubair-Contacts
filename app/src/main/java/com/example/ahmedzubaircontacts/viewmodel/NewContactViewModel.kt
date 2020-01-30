package com.example.ahmedzubaircontacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ahmedzubaircontacts.model.*
import kotlinx.coroutines.launch

class NewContactViewModel(application: Application) : BaseViewModel(application) {
    var contactId: MutableLiveData<Long> = MutableLiveData()

    fun saveContact(
        person: Person,
        phones: ArrayList<Phone>?,
        emails: ArrayList<Email>?,
        addresses: ArrayList<Address>?,
        isUpdate: Boolean
    ) {
        launch {
            val personDao = ContactDatabase(getApplication()).personDAO()
            val phoneDao = ContactDatabase(getApplication()).phoneDAO()
            val emailDao = ContactDatabase(getApplication()).emailDAO()
            val addressDao = ContactDatabase(getApplication()).addressDAO()

            if (isUpdate) {
                phoneDao.deleteOldPhones(person.personId)
                personDao.deletePerson(person.personId)
                emailDao.deleteOldEmail(person.personId)
                addressDao.deleteOldAddress(person.personId)
            }
            val personId = personDao.insert(person)

            personId.let {
                if (!phones.isNullOrEmpty()) {
                    val idAdjustedPhones = assignPersonIdToPhones(phones, personId)
                    phoneDao.insertAll(*idAdjustedPhones.toTypedArray())
                }
                if (!emails.isNullOrEmpty()) {
                    val idAdjustedEmails = assignPersonIdToEmails(emails, personId)
                    emailDao.insertAll(*idAdjustedEmails.toTypedArray())
                }
                if (!addresses.isNullOrEmpty()) {
                    val idAdjustedAddresses = assignPersonToAddresses(addresses, personId)
                    addressDao.insertAll(*idAdjustedAddresses.toTypedArray())
                }
                contactId.value = personId
            }
        }
    }

    private fun assignPersonIdToPhones(list: ArrayList<Phone>, id: Long): ArrayList<Phone> {
        val fixedId = ArrayList<Phone>()
        for (phone in list) {
            fixedId.add(Phone(id, phone.type, phone.number))
        }
        return fixedId
    }

    private fun assignPersonIdToEmails(list: ArrayList<Email>, id: Long): ArrayList<Email> {
        val fixedId = ArrayList<Email>()
        for (email in list) {
            fixedId.add(Email(id, email.type, email.address))
        }
        return fixedId
    }

    private fun assignPersonToAddresses(list: ArrayList<Address>, id: Long): ArrayList<Address> {
        val fixedId = ArrayList<Address>()
        for (address in list) {
            fixedId.add(
                Address(
                    id,
                    address.type,
                    address.street,
                    address.city,
                    address.state,
                    address.zip
                )
            )
        }
        return fixedId
    }


}