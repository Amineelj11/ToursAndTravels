package com.toursandtravel.controller;

import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.TypeResponseDto;
import com.toursandtravel.entity.Type;
import com.toursandtravel.resource.TypeProgResource;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/type")
@CrossOrigin(origins = "http://localhost:3000")
public class TypeProgController {
    @Autowired
    private TypeProgResource transportResource;

    @PostMapping("/add")
    @Operation(summary = "Api to add type")
    public ResponseEntity<CommonApiResponse> addTransport(@RequestBody Type transport) {
        return transportResource.addTransport(transport);
    }

    @PutMapping("/update")
    @Operation(summary = "Api to update type")
    public ResponseEntity<CommonApiResponse> updateTransport(@RequestBody Type transport) {
        return transportResource.updateTransport(transport);
    }

    @GetMapping("/fetch/all")
    @Operation(summary = "Api to fetch all type")
    public ResponseEntity<TypeResponseDto> fetchAllTransport() {
        return transportResource.fetchAllTransport();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Api to delete type all its events")
    public ResponseEntity<CommonApiResponse> deleteTransport(@RequestParam("transportId") int transportId) {
        return transportResource.deleteTransport(transportId);
    }

}
