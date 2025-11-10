package com.car.dekho.car.dekho.Service;

import com.car.dekho.car.dekho.Dto.ProfileDto;
import com.car.dekho.car.dekho.Entty.UserEntity;
import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import com.car.dekho.car.dekho.Exeptions.UserNotFoundExeption;
import com.car.dekho.car.dekho.Repository.UserPersonalDetaialsRepository;
import com.car.dekho.car.dekho.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPersonalDetaialsRepository personalDetaialsRepository;

    public ProfileDto getPersonalDetails(Authentication authentication) {
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundExeption("Username not exist"));

        UserPersonalDetailEntity details = personalDetaialsRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new UserNotFoundExeption("User doesn't have personal info. Please create it first."));

        return new ProfileDto(
              details.getFullName(),
                details.getAge(),
                details.getMobileNo(),
                details.getAddress(),
                details.getCity(),
                details.getState(),
                details.getCountry(),
                user.getSubscriber(),
                user.getRole()
        );
    }
}
