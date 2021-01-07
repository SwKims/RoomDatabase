package com.ksw.roomdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by KSW on 2021-01-06
 */

@Parcelize
@Entity(tableName = "user_table")
data class User(
     @PrimaryKey(autoGenerate = true)
     val id : Int,
     val firstName : String,
     val lastName : String,
     val age: Int
) : Parcelable