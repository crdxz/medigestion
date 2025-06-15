package com.medigestion.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class ResultsController {
    
    private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);

    @GetMapping("/results")
    public ResponseEntity<List<Result>> getResults() {
        logger.info("Recibida petición GET /api/results");
        try {
            List<Result> results = new ArrayList<>();
            results.add(new Result(1L, "Resultado 1", "Valor 1"));
            results.add(new Result(2L, "Resultado 2", "Valor 2"));
            logger.info("Enviando {} resultados", results.size());
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al procesar la petición", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

class Result {
    private Long id;
    private String name;
    private String value;

    public Result(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
} 