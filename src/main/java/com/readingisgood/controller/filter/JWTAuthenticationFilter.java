//package com.readingisgood.controller.filter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.readingisgood.entity.Customer;
//
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
//{
//    private static final String SECRET = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDCK+E0g5QcFTwJCzHU/Ku4IMjh3gf3Tug7lbKkY77wtOlHetwH2RykAB/yhb4NPicDYXIGI6nTO3i6eH0grTzD9Y4pxB2VnfbtwgLEoUqZKewD3Z2jKl8gK7M5eJXsN9AHj+X2e8+7bt6JKHm/LbZ+QERBi5nnvSnmg4ufIfEIBYWE8wTBVgzMamJYgZte/xGvYtN3QoYQi17NPFuhUUkgXuJDclrW23281omZSwmmh227iMnG5iujXucGvRs4KtgJ45o5HCBOTkOrcjmuR9Lnwr3mvLnP3dJ8D10qP9dXs8p18/3U4puKHrr0dS0Tyqb/KNrehzXQnJzgMWa8D66zAgMBAAECggEAVLq9HSNFoDOL7Q5bgmt3q3Ny9UzkAQEHyeh6+c0k5viqyyxLk4kkPFRpnHTMCA9VCt6c2Rz64FgVSJmHUJkH6SLgFE2l80HpHLsKZi9T1B49BTrRrP+yXuFy5eM+2MNKHeKnILXKYinxJbzp04jpF5KHtmpqkHs1K4e/h4NSIIM0isRXeeaqZOSTbfNseS3dr7vEMke9cIVnY5iqwEMJS5tXgQ4Vw82zu4IytwjoyycQxEjRuIBAWldRf9l+T4bcto+8tennUdRb4rLTtO/Lt05IAcaUsHVxRHcVrdzLhgCZG4x+AT6R/ZAmsBjL+NJSeqtsd+mPEFORt7PmuqFvIQKBgQD48SOYeCKwze8SocQPO0xQBVRywUdB4GQqOBU4B8meslgKj1GFBSOXKK7LenytQ+X3KbfA2gbk6gS4ixhLhA4qOIkUq57OeXuC9IeCZz7Ss+L0K8YS70ect+6avL3X2K2o2takSSUUG/xeuxcWKS8ITERTAe+pL6Nr44a7tCNNCwKBgQDHrTUKTHnFRQxKMePSLAUxlOrN8FQ/ntL4j8gIihlik/C41a7NLEKn42VmWNokj+RC43EOEdMvEYLlLRxeVJZP630Lvc4f1GV9vxw3Dkk22gkB9thNMKrRuRiqdvBJxSHjsd+WVIDchg1EklDk5dxh8aB83/5OSlVbfv0Sxq6d+QKBgQC968KbXPQibUo6rb2VXUkQN7DAx+IkSRxB9X2CvLY+Y5O1orevw0bu2LPaOz2JNqbolSRCdlQSKtOlM0E6w6nG3lD+uxwyLA5lz0be3wPX3jOE0yyPX4wD7XOVC+/OD9oVW7erqL4oBF3aq+OmHY9rRjxOE8kTC2wjMHenFnxkFwKBgCUebsi+VLi2UEWOprJQWprP+yJTdeXpyYBJgj8MJRHtPIevuXIMs0twe04eYFcBpMZGb6LU73RFhhbCXw6uHM84kKNZInNKUZ+hokYd0sBuodQKpkUbMfYQuaQeviaIjfc8/rp8dQQJq3e+12jlziUq/NGZ9jVpnhqHLdaKtrVJAoGAAMiMQYupIhPTXLjbEInhNyQAgvAPtJCDD4ZgxarQgcqKqm9tXlSquNySg7IJS3E52/pjWWGUrR7L/rkA9FfR44Evs7jyC37Plwd/RzZ2cd3xBxkDcHbNOumAtp/eZlAdF/1HUmatS80AsvwCQaotuLMcJG2nRnPa7aKmh2wj4KY=";
//    
//    private static final long EXPIRATION_TIME = 900000l; // 15 mins
//    
//    private AuthenticationManager authenticationManager;
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
//    {
//        this.authenticationManager = authenticationManager;
//
//        setFilterProcessesUrl("/api/customer/token");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//            throws AuthenticationException
//    {
//        try
//        {
//            Customer creds = new ObjectMapper().readValue(req.getInputStream(), Customer.class);
//
//            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),
//                    creds.getPassword(), new ArrayList<>()));
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
//            Authentication auth) throws IOException
//    {
//        String token = JWT.create().withSubject(((Customer) auth.getPrincipal()).getEmail())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(SECRET.getBytes()));
//
//        String body = ((Customer) auth.getPrincipal()).getEmail() + " " + token;
//
//        res.getWriter().write(body);
//        res.getWriter().flush();
//    }
//}