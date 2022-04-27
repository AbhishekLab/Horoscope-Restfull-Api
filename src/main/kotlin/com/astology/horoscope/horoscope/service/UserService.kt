package com.astology.horoscope.horoscope.service

import com.astology.horoscope.horoscope.model.UserRegistration
import com.astology.horoscope.horoscope.repo.UserRegistrationRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService : UserDetailsService {

    @Autowired
    lateinit var adminDetails : UserRegistrationRepo

    final var encoder: BCryptPasswordEncoder = passwordEncoder()
    override fun loadUserByUsername(username: String): UserDetails {
       //Get Admin data from database and send into user details
       val user : UserRegistration =  adminDetails.findByUserEmail(username)
        return  User(user.email, encoder.encode(user.password), ArrayList())
    }

    @Bean
    final fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}