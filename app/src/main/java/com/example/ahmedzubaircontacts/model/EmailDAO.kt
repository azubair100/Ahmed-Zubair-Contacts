package com.example.ahmedzubaircontacts.model

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface EmailDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg emails: Email)

    @Update
    suspend fun updateEmail(email: Email)

    @Delete
    suspend fun deleteEmail(email: Email)

    @Query("SELECT * FROM email WHERE personCreatorId = :personId")
    suspend fun findAllEmailsByPersonId(personId: Long): List<Email>

    @Query("DELETE FROM email WHERE personCreatorId = :personId")
    suspend fun deleteOldEmail(personId: Long)
}