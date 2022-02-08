package com.ask0n.cloudvault.repositories;

import com.ask0n.cloudvault.entities.Vault;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaultRepository extends CrudRepository<Vault, UUID> {
    Optional<Vault> findByFilenameAndDeletedFalseAndUser_Id(String fileName, UUID user_id);
    Optional<List<Vault>> findAllByUser_IdAndDeletedFalse(UUID user_id);
}
