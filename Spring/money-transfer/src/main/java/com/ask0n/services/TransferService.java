package com.ask0n.services;

import com.ask0n.exceptions.ErrorConfirmationException;
import com.ask0n.exceptions.ErrorTransferException;
import com.ask0n.models.Card;
import com.ask0n.models.Operation;
import com.ask0n.models.Transfer;
import com.ask0n.repositories.CardsRepository;
import com.ask0n.repositories.TransfersRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransferService {
    private final TransfersRepository transfersRepository;
    private final CardsRepository cardsRepository;

    public TransferService(TransfersRepository transfersRepository, CardsRepository cardsRepository) {
        this.transfersRepository = transfersRepository;
        this.cardsRepository = cardsRepository;
    }

    public Operation transfer(Transfer transfer) {
        final Card cardFrom = cardsRepository.getByCardNumber(transfer.getCardFromNumber())
                .orElseThrow(() -> new ErrorTransferException("Card not found (from)"));
        cardsRepository.getByCardNumber(transfer.getCardToNumber())
                .orElseThrow(() -> new ErrorTransferException("Card not found (to)"));
        final int transferValue = transfer.getAmount().getValue();
        if (cardFrom.getAmount().getValue() < (transferValue + (transferValue * Transfer.COMMISSION)))
            throw new ErrorTransferException("Insufficient funds");

        final Transfer newTransfer = transfersRepository.save(transfer);
        return new Operation(newTransfer.getId().toString());
    }

    public Operation confirmOperation(Operation operation) {
        if (operation.getOperationId() == null || operation.getOperationId().equals(""))
            throw new ErrorConfirmationException("Invalid UUID");
        if (!operation.getCode().equals("0000")) throw new ErrorConfirmationException("Invalid code");

        final Transfer transfer = transfersRepository.getById(UUID.fromString(operation.getOperationId()))
                .orElseThrow(() -> new ErrorTransferException("Transfer not found"));
        final Card cardFrom = cardsRepository.getByCardNumber(transfer.getCardFromNumber())
                .orElseThrow(() -> new ErrorTransferException("Card not found (from)"));
        final Card cardTo = cardsRepository.getByCardNumber(transfer.getCardToNumber())
                .orElseThrow(() -> new ErrorTransferException("Card not found (to)"));
        final int transferValue = transfer.getAmount().getValue();

        cardFrom.getAmount().setValue(cardFrom.getAmount().getValue() - transferValue +
                (int) Math.round(transferValue * Transfer.COMMISSION));
        cardTo.getAmount().setValue(cardTo.getAmount().getValue() + transferValue);

        cardsRepository.update(cardFrom);
        cardsRepository.update(cardTo);
        transfersRepository.delete(transfer.getId());
        operation.setCode(null);
        return operation;
    }
}
