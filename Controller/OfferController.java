package com.car.dekho.car.dekho.Controller;

import com.car.dekho.car.dekho.Dto.OfferRequestDto;
import com.car.dekho.car.dekho.Dto.OfferResponseDto;
import com.car.dekho.car.dekho.Entty.CarEntity;
import com.car.dekho.car.dekho.Entty.OfferEntity;
import com.car.dekho.car.dekho.Exeptions.SomethingIsWrongExeption;
import com.car.dekho.car.dekho.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/offer")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferResponseDto> offer(Authentication authentication, @RequestBody OfferRequestDto offerRequstDto) {
        try {
            OfferResponseDto offerResponse = offerService.offerResponse(authentication, offerRequstDto);
            return ResponseEntity.ok(offerResponse);
        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id, Authentication authentication) {
        try {
            offerService.deleteOffer(id);
            return ResponseEntity.ok("Offer deleted successfully");
        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<OfferResponseDto> editOffer(@PathVariable long id, @RequestBody OfferRequestDto offerRequstDto) {
        try {
            OfferResponseDto offerResponseDto = offerService.editOffer(id, offerRequstDto);
            return ResponseEntity.ok(offerResponseDto);
        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{carId}")
    public ResponseEntity<List<OfferResponseDto>> getAll(Authentication authentication, @PathVariable long carId) {
        try {
            List<OfferResponseDto> offerResponseDtos = offerService.findAllOffers(authentication, carId);
            return ResponseEntity.ok(offerResponseDtos);
        } catch (Exception e) {
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<OfferEntity>> searchCars(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<OfferEntity> cars = offerService.search(name, city, minPrice, maxPrice);
        return ResponseEntity.ok(cars);
    }
}
