package me.kalpha.trapi.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findByUserId() {
        String userId = "kalphageek";
        String password = "kalphageek";

        generateAccount(userId, password);

        UserDetailsService userDetailsService = (UserDetailsService) this.accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        // 입력받은 값, Encode된 값 순서으로 비교
        assertTrue(passwordEncoder.matches(password, userDetails.getPassword()));
    }

    @Test
    void findByUserIdFail() {
        String userId = "kalphageek1";

//        generateAccount(userId, password);

        try {
            accountService.loadUserByUsername(userId);
            fail("Test가 실패했습니다");
        } catch (UsernameNotFoundException e) {
            assertTrue(e.getMessage().contains(userId));
        }
    }

    private void generateAccount(String userId, String password) {
        Account account = Account.builder()
                .userId(userId)
                .password(password)
                .build();

        accountService.saveAccount(account);
    }
}