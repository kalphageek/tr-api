package me.kalpha.trapi.ehub.controller;

import me.kalpha.trapi.common.BaseControllerTest;
import me.kalpha.trapi.ehub.entity.Eqp1TrDetDto;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Eqp1TrControllerTest extends BaseControllerTest {

    @Transactional
    @Test
    public void createTr() throws Exception {
        Eqp1TrDto eqp1TrDto = generateEqp1TrDto("lot2");

        mockMvc.perform(post("/icems/tr")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eqp1TrDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
    }

    private Eqp1TrDto generateEqp1TrDto(String trName) {
        Eqp1TrDto eqp1TrDto = Eqp1TrDto.builder()
                .name(trName).value(123454l).eventTime(LocalDateTime.now())
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