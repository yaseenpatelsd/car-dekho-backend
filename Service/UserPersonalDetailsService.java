package com.car.dekho.car.dekho.Service;

import com.car.dekho.car.dekho.Dto.UserPersonalDetailDto;
import com.car.dekho.car.dekho.Entty.UserEntity;
import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import com.car.dekho.car.dekho.Exeptions.ResourceNotFoundExeption;
import com.car.dekho.car.dekho.Exeptions.UserNotFoundExeption;
import com.car.dekho.car.dekho.Mapping.PersonalDetailsMapping;
import com.car.dekho.car.dekho.Repository.UserPersonalDetaialsRepository;
import com.car.dekho.car.dekho.Repository.UserRepository;
import com.car.dekho.car.dekho.Specification.PersonalDetailSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPersonalDetailsService {
    @Autowired
    private UserPersonalDetaialsRepository personalDetaialsRepository;
    @Autowired
    private PersonalDetailsMapping personalDetailsMapping;
    @Autowired
    private UserRepository userRepository;

   public List<UserPersonalDetailEntity> getUser(String fullName,Long mobileNo) {
       Specification<UserPersonalDetailEntity> spec = Specification.allOf();

       if (fullName != null) {
           spec = spec.and(PersonalDetailSpecification.findByName(fullName));
       }
       if (mobileNo != null) {
           spec = spec.and(PersonalDetailSpecification.findByMobileNo(mobileNo));
       }
       return personalDetaialsRepository.findAll(spec);
   }

    public UserPersonalDetailDto addUserPersonalDetails(Authentication authentication, UserPersonalDetailDto dto){
        String Username=authentication.getName();
        UserEntity user=userRepository.findByUsername(Username).orElseThrow(()-> new UserNotFoundExeption("Username not found"));
        UserPersonalDetailEntity entity = personalDetailsMapping.toEntity(dto);
        entity.setUser(user);
        UserPersonalDetailEntity saved = personalDetaialsRepository.save(entity);
        return personalDetailsMapping.toDto(saved);
    }

    public UserPersonalDetailDto getPersonalDetails(Authentication authentication){
        UserEntity user=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundExeption("Username not found"));

        UserPersonalDetailEntity userPersonalDetails=personalDetaialsRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new ResourceNotFoundExeption("Car not found with id: " + user));

        return personalDetailsMapping.toDto(userPersonalDetails);
    }

    public UserPersonalDetailDto editPersonalDetails(Authentication authentication, UserPersonalDetailDto userPersonalDetailsDto){
        UserEntity user=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundExeption("Username not found"));

        UserPersonalDetailEntity userPersonalDetails=personalDetaialsRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new ResourceNotFoundExeption("Car not found with id: " + user));

        if (userPersonalDetailsDto.getFullName()!=null){
            userPersonalDetails.setFullName(userPersonalDetails.getFullName());
        }
        if (userPersonalDetailsDto.getAge()!=null){
            userPersonalDetails.setAge(userPersonalDetails.getAge());
        }  if (userPersonalDetailsDto.getMobileNo()!=null){
            userPersonalDetails.setMobileNo(userPersonalDetails.getMobileNo());
        }  if (userPersonalDetailsDto.getAddress()!=null){
            userPersonalDetails.setAddress(userPersonalDetails.getAddress());
        } if (userPersonalDetailsDto.getCity()!=null){
            userPersonalDetails.setCity(userPersonalDetails.getCity());
        } if (userPersonalDetailsDto.getCountry()!=null){
            userPersonalDetails.setCountry(userPersonalDetails.getCountry());
        }

        UserPersonalDetailEntity userPersonalDetails1=personalDetaialsRepository.save(userPersonalDetails);

        return personalDetailsMapping.toDto(userPersonalDetails1);
    }

    public void deleteUserPersonalInfo(Authentication authentication){
        UserEntity user=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundExeption("Cant find username"));
        personalDetaialsRepository.deleteByUser_Username(user.getUsername());
    }

}
