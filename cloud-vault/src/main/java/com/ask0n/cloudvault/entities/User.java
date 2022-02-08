package com.ask0n.cloudvault.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SELECT)
    private Set<UserRole> userRoles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SELECT)
    private Set<Vault> vault;

    @OneToOne(mappedBy = "user")
    private JwtTokens token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}