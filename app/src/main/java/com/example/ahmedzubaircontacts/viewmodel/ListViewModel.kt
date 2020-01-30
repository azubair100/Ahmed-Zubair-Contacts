package com.example.ahmedzubaircontacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ahmedzubaircontacts.model.ContactDatabase
import com.example.ahmedzubaircontacts.model.Person
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {
    val personList: MutableLiveData<List<Person>> = MutableLiveData()
    val listLLoadError: MutableLiveData<Boolean> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllPersons() {
        loading.value = true
        launch {
            personsRetrieved(ContactDatabase(getApplication()).personDAO().getAllPersons())
        }
    }

    fun getPersonsByQuery(query: String?) {
        loading.value = true
        launch {
            if (!query.isNullOrEmpty()) {
                personsRetrieved(
                    ContactDatabase(getApplication()).personDAO().searchPersons("%$query%")
                )
            }
        }
    }

    private fun personsRetrieved(persons: List<Person>) {
        personList.value = persons
        listLLoadError.value = false
        loading.value = false
    }


}