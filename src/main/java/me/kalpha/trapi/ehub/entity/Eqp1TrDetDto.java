package me.kalpha.trapi.ehub.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDet;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Getter
@Builder
public class Eqp1TrDetDto {
    private String col1;
    private Long col2;

    public Eqp1TrDet toEntity(Eqp1Tr eqp1Tr) {
        ModelMapper modelMapper = new ModelMapper();
        Eqp1TrDet eqp1TrDet = modelMapper.map(this, Eqp1TrDet.class);
        eqp1TrDet.assignEqp1Tr(eqp1Tr);
        return eqp1TrDet;
    }
}
