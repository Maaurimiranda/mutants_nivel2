package com.example.demo.Controller;

import com.example.demo.Service.RegistroADNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    private RegistroADNService registroADNService;


    // Metodo para verificar si el ADN ingresado pertenece a un mutante
    @PostMapping("")
    public ResponseEntity<String> isMutant(@RequestBody Map<String, String[]> request) {
        String[] dna = request.get("dna");

        if (dna == null || dna.length == 0) {
            return new ResponseEntity<>("DNA sequence is required", HttpStatus.BAD_REQUEST);
        }

        boolean isMutant = registroADNService.isMutant(dna);

        if (isMutant) {
            return new ResponseEntity<>("Mutant detected", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not a mutant", HttpStatus.FORBIDDEN);
        }
    }
}