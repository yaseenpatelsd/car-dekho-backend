package com.car.dekho.car.dekho.Jwt;

import org.springframework.stereotype.Component;

@Component
public class OtpUtil {

    public String generateOtp(){
        int otp=10000+(int) (Math.random()*90000);
        return String.valueOf(otp);
    }

    public Long expiretime(long minutes){
        return System.currentTimeMillis()+(minutes*60*1000);
    }

}
