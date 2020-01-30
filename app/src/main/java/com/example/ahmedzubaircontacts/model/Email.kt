package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "email",
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = Person::class,
            parentColumns = ["personId"],
            childColumns = ["personCreatorId"]
        )
    ]
)
data class Email(
    val personCreatorId: Long,
    val type: String,
    val address: String
) {
    @PrimaryKey(autoGenerate = true)
    var emailId: Long = 0
}