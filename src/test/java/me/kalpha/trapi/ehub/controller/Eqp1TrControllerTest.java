package me.kalpha.trapi.ehub.controller;

import me.kalpha.trapi.common.BaseControllerTest;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDetDto;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import me.kalpha.trapi.ehub.service.Eqp1TrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
public class Eqp1TrControllerTest extends BaseControllerTest {

    @Autowired
    Eqp1TrService eqp1TrService;

    @Transactional
    @Test
    public void createTr() throws Exception {
        Eqp1TrDto eqp1TrDto = generateEqp1TrDto("lot2");

        mockMvc.perform(post("/v1/icems/tr")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eqp1TrDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    public void getTr() throws Exception {
        Eqp1TrDto eqp1TrDto = generateEqp1TrDto("lot2");
        Eqp1Tr eqp1Tr = eqp1TrService.createTr(eqp1TrDto);

        mockMvc.perform(get("/v1/icems/tr/{id}", eqp1Tr.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;

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