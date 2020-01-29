package com.example.ahmedzubaircontacts.model

import androidx.room.*

@Dao
interface PhoneDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg phones: Phone)

    @Update
    suspend fun updatePhone(phone: Phone)

    @Delete
    suspend fun deletePhone(phone: Phone)

    @Query("SELECT * FROM phone WHERE personCreatorId = :personId")
    suspend fun findAllPhonesByPersonId(personId: Long): List<Phone>

    @Query("DELETE FROM phone WHERE personCreatorId = :personId")
    suspend fun deleteOldPhones(personId: Long)
}