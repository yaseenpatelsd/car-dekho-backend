package com.car.dekho.car.dekho.Config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RazorPayConfig {
    @Value("${razorpay.key_secret}")
    private String secret;
    @Value("${razorpay.key_id}")
    private String key;


    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient(key, secret);
    }
}
