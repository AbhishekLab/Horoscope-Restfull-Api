package com.astology.horoscope.horoscope.repo

import com.astology.horoscope.horoscope.model.UserRegistration
import com.astology.horoscope.horoscope.model.UsersModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface UserRepo : JpaRepository<UsersModel, Int>

interface UserRegistrationRepo : JpaRepository<UserRegistration, Long> {

    @Query("select c From UserRegistration c Where c.email = :e and c.password = :p")
    fun login(@Param("e") email : String, @Param("p") password : String) : List<UserRegistration>
}
