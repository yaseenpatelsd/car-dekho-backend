package com.car.dekho.car.dekho.Controller;

import com.car.dekho.car.dekho.Dto.CarDto;
import com.car.dekho.car.dekho.Entty.CarEntity;
import com.car.dekho.car.dekho.Exeptions.SomethingIsWrongExeption;
import com.car.dekho.car.dekho.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/car")
public class CarViewerController {

    @Autowired
    private CarService carService;
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findPost(@PathVariable long id){
        try {
            CarDto carUploadDto1=carService.getCar(id);
            return ResponseEntity.ok(carUploadDto1);
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDto>> findAllCars(){
        try {
            List<CarDto> carUploadDtos=carService.findAllCarPost();
            return ResponseEntity.ok(carUploadDtos);
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarEntity>> searchCars(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<CarEntity> cars = carService.findall(name, brand, minPrice, maxPrice);
        return ResponseEntity.ok(cars);
    }

}
