package com.astology.horoscope.horoscope.securityadmin

import com.astology.horoscope.horoscope.service.UserService
import com.astology.horoscope.horoscope.utility.JWTUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtFilter : OncePerRequestFilter() {

    @Autowired
    private val jwtUtility: JWTUtility? = null

    @Autowired
    private val userService: UserService? = null

    @Throws(ServletException::class, IOException::class)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorization = request.getHeader("Authorization")
        var token: String? = null
        var userName: String? = null
        if (null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7)
            userName = jwtUtility!!.getUsernameFromToken(token)
        }
        if (null != userName && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userService!!.loadUserByUsername(userName)
            if (jwtUtility!!.validateToken(token, userDetails)!!) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details =
                    WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}