package me.kalpha.trapi.ehub.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import me.kalpha.trapi.common.CreatedBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "eqp1tr")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name","value","eventTime"})
public class Eqp1Tr extends CreatedBaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private Long value;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "event_time")
    private Date eventTime;

    public List<Eqp1TrDet> getEqp1TrDets() {
        if (eqp1TrDets == null)
            eqp1TrDets = new ArrayList<>();
        return eqp1TrDets;
    }

    @OneToMany(mappedBy = "eqp1Tr", cascade = CascadeType.ALL)
    List<Eqp1TrDet> eqp1TrDets;
}
