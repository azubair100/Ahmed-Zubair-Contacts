package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "person")
data class Person(
    val firstName : String,
    val lastName : String?,
    val birthday : String?): Serializable {

    @PrimaryKey(autoGenerate = true) var personId: Long = 0L
}