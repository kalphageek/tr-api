package me.kalpha.trapi.common;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
