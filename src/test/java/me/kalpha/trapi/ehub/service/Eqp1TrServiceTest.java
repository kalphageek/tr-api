package me.kalpha.trapi.ehub.service;

import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDetDto;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class Eqp1TrServiceTest {
    @Autowired
    Eqp1TrService eqp1TrService;

    @Transactional
    @Test
    public void createTr() {
        String trName = "lot1";
        Eqp1TrDto eqp1TrDto = generateEqp1TrDto(trName);

        Eqp1Tr eqp1Tr = eqp1TrService.createTr(eqp1TrDto);

        assertTrue(eqp1Tr.getEqp1TrDets().stream().count() == 2);
    }

    private Eqp1TrDto generateEqp1TrDto(String trName) {
        Eqp1TrDto eqp1TrDto = Eqp1TrDto.builder()
                .name(trName).value(123454l).eventTime(new Date())
                .build();
        Eqp1TrDetDto eqp1TrDetDto1 = Eqp1TrDetDto.builder()
                .col1("col1").col2(837466l)
                .build();
        Eqp1TrDetDto eqp1TrDetDto2 = Eqp1TrDetDto.builder()
                .col1("col2").col2(66l)
                .build();
        eqp1TrDto.getEqp1TrDetDtos().add(eqp1TrDetDto1);
        eqp1TrDto.getEqp1TrDetDtos().add(eqp1TrDetDto2);

        return eqp1TrDto;
    }
}