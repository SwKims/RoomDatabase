package com.ksw.roomdatabase.data

import androidx.lifecycle.LiveData

/**
 * Created by KSW on 2021-01-06
 */
class UserRepository(private val userDao : UserDao) {

    val readAllData : LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user : User) {
        userDao.addUser(user)
    }
}