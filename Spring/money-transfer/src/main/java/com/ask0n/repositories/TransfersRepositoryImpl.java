package com.ask0n.repositories;

import com.ask0n.exceptions.ErrorTransferException;
import com.ask0n.models.Transfer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransfersRepositoryImpl implements TransfersRepository {
    private static final List<Transfer> transfers = new ArrayList<>();

    @Override
    public List<Transfer> getAll() {
        return transfers;
    }

    @Override
    public Optional<Transfer> getById(UUID id) {
        return transfers.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public Transfer save(Transfer transfer) {
        if (transfer.getId() == null) {
            transfer.setId();
            synchronized (transfers) {
                transfers.add(transfer);
            }
        } else throw new ErrorTransferException("Transfer contains uuid. But for save need empty uuid field.");
        return transfer;
    }

    @Override
    public synchronized void delete(UUID id) {
        transfers.stream()
                .filter(p -> p.getId() == id)
                .findAny()
                .ifPresentOrElse(transfers::remove, () -> { throw new ErrorTransferException("Transfer not found"); });
    }
}
