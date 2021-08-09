package me.kalpha.trapi.common;

import me.kalpha.trapi.accounts.Account;
import me.kalpha.trapi.accounts.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
class AuthServerConfigTest extends BaseControllerTest {
    @Autowired
    AccountService accountService;

    @Test
    void getAuthToken() throws Exception {
        String username = "kalphageek";
        String password = "kalphageek";
        Account account = generateAccount(username, password);

        String clientId = "eqp1Tr";
        String clientSecret = "pass";

        mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(clientId, clientSecret))
                    .param("username", username)
                    .param("password", password)
                    .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
        ;
    }

    private Account generateAccount(String userId, String password) {
        Account account = Account.builder()
                .userId(userId)
                .password(password)
                .build();

        accountService.saveAccount(account);
        return account;
    }
}