package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "address")
data class Address(
    val personCreatorId : Int,
    val type : String,
    val street : String,
    val city : String,
    val state : String,
    val zip : String
    ): Serializable {
    @PrimaryKey(autoGenerate = true) var addressId: Int = 0
}