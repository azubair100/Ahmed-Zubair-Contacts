package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "address",
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = Person::class,
            parentColumns = ["personId"],
            childColumns = ["personCreatorId"]
        )
    ])
data class Address(
    val personCreatorId : Long,
    val type : String,
    val street : String,
    val city : String,
    val state : String,
    val zip : String
    ): Serializable {
    @PrimaryKey(autoGenerate = true) var addressId: Long = 0
}