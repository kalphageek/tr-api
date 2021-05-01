package me.kalpha.trapi.ehub.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.kalpha.trapi.ehub.entity.Eqp1TrDetDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Eqp1TrDto {
    private String name;
    private Long value;
    private LocalDateTime eventTime;
    private List<Eqp1TrDetDto> eqp1TrDetDtos;

    public List<Eqp1TrDetDto> getEqp1TrDetDtos() {
        if (eqp1TrDetDtos == null)
            eqp1TrDetDtos = new ArrayList<>();
        return eqp1TrDetDtos;
    }
}
