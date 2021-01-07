package com.ksw.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.ksw.roomdatabase.data.UserDao
import com.ksw.roomdatabase.model.User

/**
 * Created by KSW on 2021-01-06
 */
class UserRepository(private val userDao : UserDao) {

    val readAllData : LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user : User) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}