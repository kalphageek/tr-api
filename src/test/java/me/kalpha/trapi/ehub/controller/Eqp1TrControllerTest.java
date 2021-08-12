package me.kalpha.trapi.ehub.controller;

import me.kalpha.trapi.accounts.Account;
import me.kalpha.trapi.accounts.AccountRepository;
import me.kalpha.trapi.accounts.AccountRole;
import me.kalpha.trapi.accounts.AccountService;
import me.kalpha.trapi.common.BaseControllerTest;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDetDto;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import me.kalpha.trapi.ehub.repository.Eqp1TrRepository;
import me.kalpha.trapi.ehub.service.Eqp1TrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
public class Eqp1TrControllerTest extends BaseControllerTest {

    @Autowired
    Eqp1TrService eqp1TrService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    Eqp1TrRepository eqp1TrRepository;

//    @BeforeEach
//    void setUp() {
//        accountRepository.deleteAll();
//        eqp1TrRepository.deleteAll();
//    }

    @Transactional
    @Test
    public void createTr() throws Exception {
        Eqp1TrDto eqp1TrDto = generateEqp1TrDto("lot2");

        mockMvc.perform(post("/v1/icems/tr")
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken())
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

    private String getBearerToken() throws Exception {
        return "Bearer " + getAccessToken();
    }

    private String getAccessToken() throws Exception {
        String username = "admin";
        String password = "admin";
        Account account = Account.builder()
                .userId(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        accountService.saveAccount(account);

        String clientId = "eqp1Tr";
        String clientSecret = "pass";

        ResultActions perform = mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"))
                ;

        String responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
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