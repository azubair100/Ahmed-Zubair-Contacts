package com.example.ahmedzubaircontacts.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class, Phone::class, Email::class, Address::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun personDAO(): PersonDAO
    abstract fun phoneDAO(): PhoneDAO
    abstract fun emailDAO(): EmailDAO
    abstract fun addressDAO(): AddressDAO

    companion object{
        @Volatile private var instance: ContactDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{ instance = it}
        }

        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext, ContactDatabase::class.java, "contact_database"
        ).build()
    }
}