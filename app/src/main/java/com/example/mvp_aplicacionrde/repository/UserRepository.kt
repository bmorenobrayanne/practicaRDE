package com.example.mvp_aplicacionrde.repository

import com.example.mvp_aplicacionrde.data.local.dao.UserDao
import com.example.mvp_aplicacionrde.data.local.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(userId: String): UserEntity? {
        return userDao.getUserById(userId)
    }
}