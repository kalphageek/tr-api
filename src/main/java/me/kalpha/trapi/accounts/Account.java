package me.kalpha.trapi.accounts;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@EqualsAndHashCode(of = "userId")
@NoArgsConstructor @AllArgsConstructor
public class Account {
    @Id
    private String userId;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;
}
