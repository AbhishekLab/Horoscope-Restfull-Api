package com.astology.horoscope.horoscope.controller

import com.astology.horoscope.horoscope.model.ApiResponse
import com.astology.horoscope.horoscope.model.LoginModel
import com.astology.horoscope.horoscope.model.UserRegistration
import com.astology.horoscope.horoscope.repo.UserRegistrationRepo
import com.astology.horoscope.horoscope.service.UserService
import com.astology.horoscope.horoscope.utility.JWTUtility
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@Api(value = "Horoscope Information related interface", tags = ["Horoscope"])
@RestController
class UserController {

    //REGISTRATION
    @Autowired
    lateinit var userRegistrationRepo : UserRegistrationRepo

    //Email
    @Autowired
    private val mailSender: JavaMailSender? = null

    @Autowired
    lateinit var authManager : AuthenticationManager
    @Autowired
    lateinit var jwtUtility: JWTUtility
    @Autowired
    lateinit var servive: UserService

    @GetMapping("/api/dashboard") // -> api nahi  chalega (auth chalega)
    fun dashboard():String{
        return "Hi this is dashboard Page"
    }

    @GetMapping("/auth/setting")
    fun setting():String{
        return "Hi this is setting Page"
    }

    //REGISTRATION -> User Registration
    @ApiOperation(value = "User Registration")
    @PostMapping("/auth/registration")
    fun userRegistration(@RequestBody userRegistrationDetails : UserRegistration) : ApiResponse{
        val response : ApiResponse? = try {
            userRegistrationRepo.save(userRegistrationDetails)
            ApiResponse(statusCode = 200, statusMessage = "Congrats!!, Registration Successfully.", statusType = true, token = "")
        }catch (e : Exception){
            println(e)
            ApiResponse(statusCode = 404, statusMessage = "Registration Failed!! \n Due to : $e", statusType = false, token = "")
        }
        return response!!
    }

    //LOGIN -> User Login
    @ApiOperation(value = "User Login")
    @PostMapping("/auth/login")
    fun userLogin(@RequestBody userLoginDetails : LoginModel) : ApiResponse{

        var response : ApiResponse? = null
        response = try{
            authManager.authenticate(UsernamePasswordAuthenticationToken(
                userLoginDetails.email, userLoginDetails.password
            ))
            val userDetails: UserDetails = servive.loadUserByUsername(userLoginDetails.email)
            val token = jwtUtility.generateToken(userDetails)!!

            print("USER DETAIL ${userDetails.username}")
            System.out.println("USER DETAIL ${userDetails.username}")
            ApiResponse(statusCode = 200, statusMessage = "Login Successfully", statusType = true, token = token)

        }catch (e : Exception){
            print(e.toString())
            ApiResponse(statusCode = 400, statusMessage = "User not found. Please check your credential and try again", statusType = false, token = "")
        }

        return response!!

    }

    @ApiOperation(value = "send email to client")
    @PostMapping("/sendEmail")
    fun sendEmail(@RequestParam("toEmail", required = true) toEmail:String, @RequestParam("body", required = true) body:String, @RequestParam("subject", required = true) subject:String) : String {

        val message = SimpleMailMessage()

        message.setFrom("spring.email.from@gmail.com")
        message.setTo(toEmail)
        message.setText(body)
        message.setSubject(subject)

        mailSender?.send(message)

        return "Email send.."
    }
}