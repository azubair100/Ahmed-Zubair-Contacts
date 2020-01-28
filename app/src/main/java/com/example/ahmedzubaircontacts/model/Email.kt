package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "email",
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = Person::class,
            parentColumns = ["personId"],
            childColumns = ["personCreatorId"]
        )
    ])
data class Email(
    val personCreatorId : Long,
    val type : String,
    val address : String
    ): Serializable {
    @PrimaryKey(autoGenerate = true) var emailId: Long = 0
}