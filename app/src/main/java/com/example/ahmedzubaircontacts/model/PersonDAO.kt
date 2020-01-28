package com.example.ahmedzubaircontacts.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDAO {

    @Insert
    suspend fun insert(person: Person)

    @Query("SELECT * FROM person")
    suspend fun getAllPersons(): List<Person>

    @Query("SELECT * FROM person WHERE personId = :id")
    suspend fun getPersonById(id: Int): Person
}