package com.car.dekho.car.dekho.Service;

import com.car.dekho.car.dekho.Dto.OfferRequestDto;
import com.car.dekho.car.dekho.Dto.OfferResponseDto;
import com.car.dekho.car.dekho.Entty.CarEntity;
import com.car.dekho.car.dekho.Entty.OfferEntity;
import com.car.dekho.car.dekho.Entty.UserEntity;
import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import com.car.dekho.car.dekho.Exeptions.ResourceNotFoundExeption;
import com.car.dekho.car.dekho.Exeptions.UserNotFoundExeption;
import com.car.dekho.car.dekho.Mapping.OfferMapping;
import com.car.dekho.car.dekho.Mapping.PersonalDetailsMapping;
import com.car.dekho.car.dekho.Repository.CarRepository;
import com.car.dekho.car.dekho.Repository.OfferRepository;
import com.car.dekho.car.dekho.Repository.UserPersonalDetaialsRepository;
import com.car.dekho.car.dekho.Repository.UserRepository;
import com.car.dekho.car.dekho.Specification.OfferSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferSpecification offerSpecification;

    @Autowired
    private OfferMapping offerMapping;
    @Autowired
    private UserPersonalDetaialsRepository personalDetaialsRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;


    public List<OfferEntity> search(String city, String offererName, Double minPrice , Double maxPrice){
        Specification<OfferEntity> spec= Specification.allOf();

        if (city!=null) {
            spec=spec.and(offerSpecification.findCity(city));
        }

        if (offererName!=null) {
            spec=spec.and(offerSpecification.hasOfferName(offererName));
        }
        if (minPrice!=null || maxPrice!=null) {
            spec=spec.and(offerSpecification.hasPriceBetween(minPrice,maxPrice));
        }

         return offerRepository.findAll(spec);
        }

    public OfferResponseDto offerResponse(Authentication authentication, OfferRequestDto offerRequstDto){
        UserEntity user=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundExeption("Username cant be found "));

        UserPersonalDetailEntity userPersonalDetails=personalDetaialsRepository.findByUser_Username(authentication.getName())
                .orElseThrow(()-> new ResourceNotFoundExeption("Personal details not be found "));

        CarEntity car=carRepository.findById(offerRequstDto.getCarId())
                .orElseThrow(()-> new ResourceNotFoundExeption("Post cant be found"));

        boolean alreadyOffer=offerRepository.findByUser_UsernameAndUserPersonalDetails_Id(authentication.getName(), userPersonalDetails.getId()).isPresent();

        if (alreadyOffer){
            throw new ResourceNotFoundExeption("Already Offors ones");
        }

        OfferEntity offerEntity = OfferEntity.builder()
                .price(offerRequstDto.getPrice())
                .note(offerRequstDto.getNote())
                .offererName(userPersonalDetails.getFullName())
                .city(userPersonalDetails.getCity())
                .user(user)
                .userPersonalDetails(userPersonalDetails)
                .car(car)
                .build();


        offerRepository.save(offerEntity);

        return new OfferResponseDto(offerEntity.getPrice(), offerEntity.getNote(), offerEntity.getOffererName(), offerEntity.getCity());
    }

    public List<OfferResponseDto> findAllOffers(Authentication authentication,long carId){
        if (authentication==null || !authentication.isAuthenticated()){
            throw new AuthenticationException("NotAuthorized") {
            };
        }
        List<OfferEntity> offer=offerRepository.findByCar_Id(carId);
        if (offer.isEmpty()){
            throw new ResourceNotFoundExeption("Offer not found");
        }
        return offer.stream().map(offerMapping::toDto).collect(Collectors.toList());
    }

    public void deleteOffer(long offerId){

        OfferEntity offerEntity=offerRepository.findById(offerId)
                .orElseThrow(()-> new UserNotFoundExeption("Not found"));
        offerRepository.delete(offerEntity);
    }

    public OfferResponseDto editOffer(long id,OfferRequestDto offerRequstDto){
        OfferEntity offerEntity=offerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundExeption("not found"));

        if (offerRequstDto.getPrice()!=null){
            offerEntity.setPrice(offerRequstDto.getPrice());
        }
        if (offerRequstDto.getNote()!=null){
            offerEntity.setNote(offerRequstDto.getNote());
        }

        OfferEntity saved=offerRepository.save(offerEntity);
        return offerMapping.toDto(saved);
    }
    }
