package com.car.dekho.car.dekho.Controller;

import com.car.dekho.car.dekho.Dto.CarDto;
import com.car.dekho.car.dekho.Entty.CarEntity;
import com.car.dekho.car.dekho.Exeptions.SomethingIsWrongExeption;
import com.car.dekho.car.dekho.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CarController {
    @Autowired
    private CarService carService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/post")
    public ResponseEntity<CarDto> post(@RequestBody CarDto dto){
        try {
            CarDto carUploadDto=carService.AddCarPost(dto);
            return ResponseEntity.ok(carUploadDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("edit/{id}")
    public ResponseEntity<CarDto> editPost(@PathVariable long id, @RequestBody CarDto carUploadDto){
        try {
            CarDto carUploadDto1=carService.carUpdate(id,carUploadDto);
            return ResponseEntity.ok(carUploadDto1);
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarPost(@PathVariable long id, Authentication authentication){
        try {
            carService.removePost(id, authentication);
            return ResponseEntity.ok("Deleted Successfuly");
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }

    }
}
