package com.ksw.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ksw.roomdatabase.model.User

/**
 * Created by KSW on 2021-01-06
 */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user : User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<User>>


}