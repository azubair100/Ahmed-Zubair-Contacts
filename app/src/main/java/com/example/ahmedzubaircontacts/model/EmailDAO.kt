package com.example.ahmedzubaircontacts.model

import androidx.room.*

@Dao
interface EmailDAO {
    @Insert
    suspend fun insertAll(vararg emails: Email)

    @Update
    suspend fun updateEmail(email: Email)

    @Delete
    suspend fun deleteEmail(email: Email)

    @Query("SELECT * FROM email WHERE personCreatorId = :personId")
    suspend fun findAllEmailsByPersonId(personId: Long): List<Email>
}