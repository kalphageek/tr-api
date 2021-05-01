package me.kalpha.trapi.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @EntityListeners(AuditingEntityListener.class) 는 cteatedBy, lastModifiedBy를 위해 사용한다.
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass //DB컬럼을 상속할 수 있도록 한다.
public class CreatedBaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
}
