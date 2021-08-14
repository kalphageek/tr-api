package me.kalpha.trapi.ehub.controller;

import lombok.extern.slf4j.Slf4j;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import me.kalpha.trapi.ehub.service.Eqp1TrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/v1/icems/tr")
@Slf4j
public class Eqp1TrController {
    @Autowired
    Eqp1TrService eqp1TrService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Eqp1Tr> createTr(@RequestBody Eqp1TrDto eqp1TrDto, @AuthenticationPrincipal OAuth2Authentication authentication) {
        String username = authentication.getUserAuthentication().getPrincipal().toString();
        Set<String> scopes = authentication.getOAuth2Request().getScope();
        log.info("Account name = {}", username);
        log.info("Client scope info = {}", scopes);
        Eqp1Tr eqp1Tr = eqp1TrService.createTr(eqp1TrDto);
        return ResponseEntity.ok().body(eqp1Tr);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eqp1Tr> getTr(@PathVariable Long id) {
        Eqp1Tr eqp1Tr = eqp1TrService.getTr(id);
        return ResponseEntity.ok().body(eqp1Tr);
    }
}
