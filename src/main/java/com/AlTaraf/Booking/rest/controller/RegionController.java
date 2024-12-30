package com.AlTaraf.Booking.rest.controller;


import com.AlTaraf.Booking.rest.dto.RegionDto;
import com.AlTaraf.Booking.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/byCity/{Id}")
    public ResponseEntity<List<RegionDto>> getRegionsByCityId(@PathVariable Long Id) {
        List<RegionDto> RegionDtos = regionService.getRegionsByCityId(Id);
        return new ResponseEntity<>(RegionDtos, HttpStatus.OK);
    }
}