package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person(
    var firstName: String,
    var lastName: String?,
    var birthday: String?
) {

    @PrimaryKey(autoGenerate = true)
    var personId: Long = 0L
}