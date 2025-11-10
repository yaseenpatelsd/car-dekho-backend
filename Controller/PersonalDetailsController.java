package com.car.dekho.car.dekho.Controller;

import com.car.dekho.car.dekho.Dto.UserPersonalDetailDto;
import com.car.dekho.car.dekho.Entty.OfferEntity;
import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import com.car.dekho.car.dekho.Exeptions.SomethingIsWrongExeption;
import com.car.dekho.car.dekho.Mapping.PersonalDetailsMapping;
import com.car.dekho.car.dekho.Repository.UserRepository;
import com.car.dekho.car.dekho.Service.UserPersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/personal-details")
public class PersonalDetailsController {
    @Autowired
    private UserPersonalDetailsService personalDetailsService;
    @Autowired
    private PersonalDetailsMapping userPersonalDetailMapping;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<UserPersonalDetailDto> addPersonalDetails(Authentication authentication, @RequestBody UserPersonalDetailDto userPersonalDetailsDto){
        try {

            UserPersonalDetailDto userPersonalDetails=personalDetailsService.addUserPersonalDetails(authentication,userPersonalDetailsDto);

            return ResponseEntity.ok(userPersonalDetails);

        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<UserPersonalDetailDto> editPersonalDetails(Authentication authentication, @RequestBody UserPersonalDetailDto userPersonalDetailsDto){
        try {
            UserPersonalDetailDto userPersonalDetails=personalDetailsService.editPersonalDetails(authentication,userPersonalDetailsDto);

            return ResponseEntity.ok(userPersonalDetails);

        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<UserPersonalDetailDto> getPersonalDetails(Authentication authentication){
        try {
            UserPersonalDetailDto userPersonalDetails=personalDetailsService.getPersonalDetails(authentication);

            return ResponseEntity.ok(userPersonalDetails);

        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePersonalDetails(Authentication authentication){
        try {
            personalDetailsService.deleteUserPersonalInfo(authentication);

            return ResponseEntity.ok("Delete info successfully");

        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserPersonalDetailEntity>> searchCars(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) Long MobileNo
    ) {
        List<UserPersonalDetailEntity> cars = personalDetailsService.getUser(fullName,MobileNo);
        return ResponseEntity.ok(cars);
    }
}
