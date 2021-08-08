package me.kalpha.trapi.ehub.controller;

import lombok.extern.slf4j.Slf4j;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import me.kalpha.trapi.ehub.service.Eqp1TrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/icems/tr")
@Slf4j
public class Eqp1TrController {
    @Autowired
    Eqp1TrService eqp1TrService;

    @PostMapping
    public ResponseEntity<Eqp1Tr> createTr(@RequestBody Eqp1TrDto eqp1TrDto) {
        Eqp1Tr eqp1Tr = eqp1TrService.createTr(eqp1TrDto);
        return ResponseEntity.ok().body(eqp1Tr);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eqp1Tr> getTr(@PathVariable Long id) {
        Eqp1Tr eqp1Tr = eqp1TrService.getTr(id);
        return ResponseEntity.ok().body(eqp1Tr);
    }
}
