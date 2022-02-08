package com.ask0n.repositories;

import com.ask0n.models.Transfer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransfersRepository {
    List<Transfer> getAll();
    Optional<Transfer> getById(UUID id);
    Transfer save(Transfer transfer);
    void delete(UUID id);
}
