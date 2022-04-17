package com.astology.horoscope.horoscope.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "User_fields")
data class UsersModel(@Id @GeneratedValue val id : Int = 0, val name : String = "", val mobile : String = "")