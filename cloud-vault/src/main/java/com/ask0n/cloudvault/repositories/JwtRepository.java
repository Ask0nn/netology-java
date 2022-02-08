package com.ask0n.cloudvault.repositories;

import com.ask0n.cloudvault.entities.JwtTokens;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JwtRepository extends CrudRepository<JwtTokens, UUID> {
    Optional<JwtTokens> findByUser_Id(UUID id);
    Optional<JwtTokens> findByToken(String findByToken);
    void deleteByToken(String token);
}
