package com.car.dekho.car.dekho.Service;

import com.car.dekho.car.dekho.Dto.CarDto;
import com.car.dekho.car.dekho.Entty.CarEntity;
import com.car.dekho.car.dekho.Entty.UserEntity;
import com.car.dekho.car.dekho.Entty.UserPersonalDetailEntity;
import com.car.dekho.car.dekho.Exeptions.ResourceNotFoundExeption;
import com.car.dekho.car.dekho.Exeptions.UserNotFoundExeption;
import com.car.dekho.car.dekho.Mapping.CarMapping;
import com.car.dekho.car.dekho.Repository.CarRepository;
import com.car.dekho.car.dekho.Repository.UserPersonalDetaialsRepository;
import com.car.dekho.car.dekho.Repository.UserRepository;
import com.car.dekho.car.dekho.Specification.CarSpecification;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;




import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarMapping carMapping;
    @Autowired
    private UserPersonalDetaialsRepository userPersonalDetaialsRepository;

    public List<CarEntity> findall(String name, String brand, Double minPrice, Double maxPrice) {

        Specification<CarEntity> spec = null;

        if (name != null && !name.isEmpty()) {
            spec = (spec == null) ? CarSpecification.findByName(name) : spec.and(CarSpecification.findByName(name));
        }

        if (brand != null && !brand.isEmpty()) {
            spec = (spec == null) ? CarSpecification.findByBrand(brand) : spec.and(CarSpecification.findByBrand(brand));
        }

        if (minPrice != null || maxPrice != null) {
            spec = (spec == null) ? CarSpecification.findByBetweenPrice(minPrice, maxPrice)
                    : spec.and(CarSpecification.findByBetweenPrice(minPrice, maxPrice));
        }

        // If no filters provided, return all cars
        return (spec == null) ? carRepository.findAll() : carRepository.findAll(spec);
    }



    public CarDto AddCarPost(CarDto carUploadDto){
        System.out.println("Received DTO: " + carUploadDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        UserPersonalDetailEntity userPersonalDetailEntity=userPersonalDetaialsRepository.findByUser_Username(authentication.getName())
                .orElseThrow(()-> new UserNotFoundExeption("UserPersonal detail not found"));
        CarEntity carEntity= carMapping.toEntity(carUploadDto);
        carEntity.setPostBy(userPersonalDetailEntity.getFullName());
        carEntity.setMobileNo(userPersonalDetailEntity.getMobileNo());
        System.out.println("Mapped Entity: " + carEntity);
        CarEntity saved= carRepository.save(carEntity);
        return carMapping.toDto(saved);
    }

    public CarDto getCar(long id){
        CarEntity carEntity=carRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption("Car not found with id: " + id));

        return carMapping.toDto(carEntity);
    }

    public List<CarDto> findAllCarPost(){
        List<CarEntity> getAllCars=carRepository.findAll();

        return getAllCars.stream().map(carMapping::toDto).collect(Collectors.toList());
    }
    public CarDto carUpdate(Long id,CarDto carUploadDto){
        CarEntity carEntity=carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Car not found with id: " + id));

        if (carUploadDto.getFullName()!=null){
            carEntity.setName(carUploadDto.getFullName());
        }
        if (carUploadDto.getYear()!=null){
            carEntity.setYear(carUploadDto.getYear());
        }
        if (carUploadDto.getModel()!=null){
            carEntity.setModel(carUploadDto.getModel());
        }
        if (carUploadDto.getPrice()!=null){
            carEntity.setPrice(carUploadDto.getPrice());
        }

        CarEntity saved= carRepository.save(carEntity);

        return carMapping.toDto(saved);
    }

    public String removePost(long id,Authentication authentication){
        UserEntity user=userRepository.findByUsername(authentication.getName())
                        .orElseThrow(()-> new UserNotFoundExeption("Username not found"));

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "Not Authorized to delete post";
        }
       CarEntity car=carRepository.findById(id)
               .orElseThrow(()-> new ResourceNotFoundExeption("Post not found"));

        carRepository.deleteById(car.getId());

        return "Deleted Successfully";
    }


}
