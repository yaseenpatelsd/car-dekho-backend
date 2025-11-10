package com.car.dekho.car.dekho.Controller;

import com.car.dekho.car.dekho.Dto.RazorPayDto;
import com.car.dekho.car.dekho.Exeptions.NotAuthorizedExeptions;
import com.car.dekho.car.dekho.Exeptions.SomethingIsWrongExeption;
import com.car.dekho.car.dekho.Repository.RazorPayRepository;
import com.car.dekho.car.dekho.Service.RazorPayService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubribingController {
    @Autowired
    private RazorPayRepository razorPayRepository;

    @Autowired
    private RazorPayService razorPayService;
    @PostMapping("/buying/subscription")
    public ResponseEntity<?> buyingSubscription(Authentication authentication){
        try {
            RazorPayDto razorPayResponseDto=razorPayService.subscription(authentication);
            return ResponseEntity.ok(razorPayResponseDto);
        } catch (RazorpayException e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('SUBSCRIBER')")
    @GetMapping("/about-subscriber")
    public ResponseEntity<?> aboutme(Authentication authentication){
        if (authentication.isAuthenticated()){
            return ResponseEntity.ok("Thanks for subscribing "+ authentication.getName());
        }else {
            throw new NotAuthorizedExeptions("Something is wrong");
        }
    }
}
