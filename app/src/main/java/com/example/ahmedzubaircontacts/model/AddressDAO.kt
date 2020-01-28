package com.example.ahmedzubaircontacts.model

import androidx.room.*

@Dao
interface AddressDAO {
    @Insert
    suspend fun insertAll(vararg addresses: Address)

    @Update
    suspend fun updateAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("SELECT * FROM address WHERE personCreatorId = :personId")
    suspend fun findAllAddressesByPersonId(personId: Int): List<Address>


}