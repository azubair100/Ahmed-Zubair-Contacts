package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "phone")
data class Phone(
    val personCreatorId : Int,
    val type : String,
    val number : String
): Serializable {
    @PrimaryKey(autoGenerate = true) var phoneId: Int = 0
}