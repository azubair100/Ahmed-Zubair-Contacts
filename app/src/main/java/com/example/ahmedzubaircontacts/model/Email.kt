package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "email")
data class Email(
    val personCreatorId : Int,
    val type : String,
    val address : String
    ): Serializable {
    @PrimaryKey(autoGenerate = true) var emailId: Int = 0
}