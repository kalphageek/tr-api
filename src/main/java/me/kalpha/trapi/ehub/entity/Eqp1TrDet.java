package me.kalpha.trapi.ehub.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import me.kalpha.trapi.common.CreatedBaseEntity;

import javax.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "eqp1TrDet")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","col1","col2"})
public class Eqp1TrDet extends CreatedBaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String col1;
    private Long col2;

    @ManyToOne
    @JoinColumn(name = "tr_id")
    private Eqp1Tr eqp1Tr;

    public void assignEqp1Tr(Eqp1Tr eqp1Tr) {
        this.eqp1Tr = eqp1Tr;
        this.eqp1Tr.getEqp1TrDets().add(this);
    }
}
