package me.kalpha.trapi.ehub.controller;

import lombok.extern.slf4j.Slf4j;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import me.kalpha.trapi.ehub.service.Eqp1TrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/icems")
@Slf4j
public class Eqp1TrController {
    @Autowired
    Eqp1TrService eqp1TrService;

    @PostMapping("/tr")
    public ResponseEntity<Eqp1Tr> createTr(@RequestBody Eqp1TrDto eqp1TrDto) {
        Eqp1Tr eqp1Tr = eqp1TrService.createTr(eqp1TrDto);
        return ResponseEntity.ok().body(eqp1Tr);
    }
}
