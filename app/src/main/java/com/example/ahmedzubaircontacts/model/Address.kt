package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "address",
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = Person::class,
            parentColumns = ["personId"],
            childColumns = ["personCreatorId"]
        )
    ]
)
data class Address(
    val personCreatorId: Long,
    val type: String,
    val street: String,
    val city: String,
    val state: String,
    val zip: String
) {
    @PrimaryKey(autoGenerate = true)
    var addressId: Long = 0
}