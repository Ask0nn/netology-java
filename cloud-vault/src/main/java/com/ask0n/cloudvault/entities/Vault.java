package com.ask0n.cloudvault.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "vault")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Vault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String filename;

    @JsonIgnore
    private String contentType;

    private long size;

    @JsonIgnore
    private byte[] data;

    @JsonIgnore
    private boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vault vault = (Vault) o;
        return size == vault.size && deleted == vault.deleted && Objects.equals(id, vault.id) && Objects.equals(user, vault.user) && Objects.equals(filename, vault.filename) && Objects.equals(contentType, vault.contentType) && Arrays.equals(data, vault.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, user, filename, contentType, size, deleted);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}