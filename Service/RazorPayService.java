package com.car.dekho.car.dekho.Service;

import com.car.dekho.car.dekho.Dto.RazorPayDto;
import com.car.dekho.car.dekho.Entty.RazorPayEntity;
import com.car.dekho.car.dekho.Entty.UserEntity;
import com.car.dekho.car.dekho.Repository.RazorPayRepository;
import com.car.dekho.car.dekho.Repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RazorPayService {

    @Autowired
    private RazorPayRepository razorPayRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RazorpayClient razorpayClient;

    private final long price=100*500;

    public RazorPayDto subscription(Authentication authentication) throws RazorpayException {
        UserEntity user=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist"));

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("amount",price);
        jsonObject.put("currency","INR");
        jsonObject.put("receipt", "txn_" + System.currentTimeMillis());
        jsonObject.put("payment_capture", 1);

        Order order=razorpayClient.orders.create(jsonObject);

        String id=order.get("id").toString();
        String amount=order.get("amount").toString();
        String currency=order.get("currency").toString();
        String status=order.get("status").toString();

        RazorPayEntity razorPayEntity=RazorPayEntity.builder()
                .amount(Long.parseLong(amount))
                .currency(currency)
                .status(status)
                .user(user)
                .build();

        razorPayRepository.save(razorPayEntity);

        user.setRole("SUBSCRIBER");
        userRepository.save(user);

        return new RazorPayDto(id,amount,currency,status);
    }
}

