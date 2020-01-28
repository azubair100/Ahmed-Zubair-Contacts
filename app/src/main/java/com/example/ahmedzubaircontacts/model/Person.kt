package com.example.ahmedzubaircontacts.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "person")
data class Person(
    @ColumnInfo(name = "first_name") val firstName : String,
    @ColumnInfo(name = "last_name") val lastName : String?,
    val birthday : String?): Serializable {

    @PrimaryKey(autoGenerate = true) var personId: Int = 0
}