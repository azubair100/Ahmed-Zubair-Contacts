package com.example.ahmedzubaircontacts.model

import androidx.room.*

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: Person): Long

    @Query("SELECT * FROM person")
    suspend fun getAllPersons(): List<Person>

    @Query("SELECT * FROM person WHERE personId = :id")
    suspend fun getPersonById(id: Long): Person

    @Query("SELECT * FROM person WHERE firstName LIKE :search OR lastName LIKE :search")
    suspend fun searchPersons(search: String): List<Person>

    @Query("DELETE FROM person WHERE personId = :personId ")
    suspend fun deletePerson(personId: Long)
}