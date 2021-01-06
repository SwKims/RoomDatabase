package com.ksw.roomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by KSW on 2021-01-06
 */

@Entity(tableName = "user_table")
data class User(
     @PrimaryKey(autoGenerate = true)
     val id : Int,
     val firstName : String,
     val lastName : String,
     val age: Int
)