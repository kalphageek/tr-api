package me.kalpha.trapi.common;

import me.kalpha.trapi.accounts.AccountRole;
import me.kalpha.trapi.accounts.AccountService;
import me.kalpha.trapi.accounts.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
    private final ModelMapper modelMapper = new ModelMapper();
    @Bean
    public ModelMapper modelMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT) //정확히 일치하는 속성만 매핑
                .setSkipNullEnabled(true); //값이 Null인 속성은 매핑에서 제외

//        Converter<Eqp1TrDto, Eqp1Tr> converter = new Converter<Eqp1TrDto, Eqp1Tr>() {
//            @Override
//            public Eqp1Tr convert(MappingContext<Eqp1TrDto, Eqp1Tr> context) {
//                Eqp1TrDto trDto = context.getSource();
//                Eqp1Tr tr = context.getDestination();
//                tr.getEqp1TrDets().addAll(trDto.getEqp1TrDetDtos().stream()
//                        .map(o -> new Eqp1TrDet(o.getCol1(), o.getCol2()))
//                        .collect(Collectors.toList())
//                );
//                return null;
//            }
//        };
//        modelMapper.addConverter(converter);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                        .userId("admin")
                        .password("admin")
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .build();

                accountService.saveAccount(account);
            }
        };
    }
}
