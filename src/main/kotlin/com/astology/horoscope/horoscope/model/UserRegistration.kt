package com.astology.horoscope.horoscope.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USER_REGISTRATION")
data class UserRegistration(@Id @GeneratedValue val id : Long = 0,
                            val name : String = "",
                            @Column(unique = true)
                            val email : String = "",
                            val password : String = "",
                            val maritalStatus : String = "",
                            val dob : String = "",
                            val tob : String = "",
                            val address : String = "",
                            val zodiacSign : String = "")
