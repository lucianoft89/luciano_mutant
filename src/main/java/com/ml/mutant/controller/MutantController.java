package com.ml.mutant.controller;

import org.json.JSONException;
import org.json.JSONObject;
import com.ml.mutant.services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MutantController<MutantServices> {

    @Autowired
    private MutantService mutantService;
    HttpHeaders headers = new HttpHeaders();

    @PostMapping("/mutant")
    public ResponseEntity<?> consumirDNA(@RequestBody String body) throws JSONException, JSONException {
        JSONObject jsonBody = new JSONObject(body);
        boolean result = mutantService.validarDNA(jsonBody);
        if (result){
            boolean resultADN = mutantService.isMutant(jsonBody);
            headers.setContentType(MediaType.TEXT_PLAIN);
            if (resultADN ){
                return new ResponseEntity<>("Magneto: Excelentes noticias! Encontramos un mutante",headers, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Magneto: El ADN procesado no es mutante.",headers, HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>("Magneto: El ADN ingresado no es valido.",headers, HttpStatus.FORBIDDEN);
        }
    }
    // boolean result = mutantServices.isMutant(jsonBody);


    // -> Se retorna un JSON con: count_mutant_dna = total ADN humanos evaluados, count_human_dna = total de ADN mutantes evaluados, ratio = ratio entre las dos evaluaciones
    @GetMapping("/stats")
    public ResponseEntity<?> status() throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(mutantService.stats(),headers, HttpStatus.OK);

    }

}
