package com.example.ahmedzubaircontacts.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "phone",
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = Person::class,
            parentColumns = ["personId"],
            childColumns = ["personCreatorId"]
        )
    ])
data class Phone(
    val personCreatorId : Long,
    val type : String,
    val number : String
): Serializable {
    @PrimaryKey(autoGenerate = true) var phoneId: Long = 0
}