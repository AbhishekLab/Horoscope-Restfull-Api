package com.astology.horoscope.horoscope.controller

import com.astology.horoscope.horoscope.model.ApiResponse
import com.astology.horoscope.horoscope.model.LoginModel
import com.astology.horoscope.horoscope.model.UserRegistration
import com.astology.horoscope.horoscope.repo.UserRegistrationRepo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*

@Api(value = "Horoscope Information related interface", tags = ["Horoscope"])
@RestController
@RequestMapping("/v1")
class UserController {

    //REGISTRATION
    @Autowired
    lateinit var userRegistrationRepo : UserRegistrationRepo

    //Email
    @Autowired
    private val mailSender: JavaMailSender? = null


    //dummy home page for testing
    @ApiOperation(value = "Dummy page")
    @GetMapping("/home")
    fun home():String{
        return "Hi this is Home Page"
    }



    //REGISTRATION -> User Registration
    @ApiOperation(value = "User Registration")
    @PostMapping("/registration")
    fun userRegistration(@RequestBody userRegistrationDetails : UserRegistration) : ApiResponse{
        val response : ApiResponse? = try {
            userRegistrationRepo.save(userRegistrationDetails)
            ApiResponse(statusCode = 200, statusMessage = "Congrats!!, Registration Successfully.", statusType = true)
        }catch (e : Exception){
            println(e)
            ApiResponse(statusCode = 404, statusMessage = "Registration Failed!! \n Due to : $e", statusType = false)
        }
        return response!!
    }

    //LOGIN -> User Login
    @ApiOperation(value = "User Login")
    @PostMapping("/login")
    fun userLogin(@RequestBody userLoginDetails : LoginModel) : ApiResponse{
        val response : ApiResponse? = try {
            val result = userRegistrationRepo.login(userLoginDetails.email, userLoginDetails.password)
            ApiResponse(statusCode = 200, statusMessage = "Login Successfully", statusType = true)
        }catch (e : Exception){
            println(e)
            ApiResponse(statusCode = 404, statusMessage = "Login Failed!! \n Due to :  $e)}", statusType = false)
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