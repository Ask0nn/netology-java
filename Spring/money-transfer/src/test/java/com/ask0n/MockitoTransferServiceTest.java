package com.ask0n;

import com.ask0n.exceptions.ErrorConfirmationException;
import com.ask0n.exceptions.ErrorTransferException;
import com.ask0n.models.Amount;
import com.ask0n.models.Card;
import com.ask0n.models.Operation;
import com.ask0n.models.Transfer;
import com.ask0n.repositories.CardsRepository;
import com.ask0n.repositories.TransfersRepositoryImpl;
import com.ask0n.services.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MockitoTransferServiceTest {
    private static final Transfer transfer = new Transfer("4620869633681275",
            "02/23",
            "268",
            "4802538654321851",
            new Amount(10000, "RUB"));

    private static final Card cardFrom = new Card(UUID.randomUUID(),
            "4620869633681275",
            "02/23",
            "268",
            new Amount(8014400, "RUB"));
    private static final Card cardTo = new Card(UUID.randomUUID(),
            "4802538654321851",
            "04/23",
            "916",
            new Amount(9700000, "RUB"));


    @Mock
    private TransfersRepositoryImpl transfersRepository;
    @Mock
    private CardsRepository cardsRepository;

    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(transfersRepository.getById(any(UUID.class))).thenReturn(Optional.of(transfer));
        when(transfersRepository.save(any(Transfer.class))).thenReturn(transfer);

        when(cardsRepository.getByCardNumber("4620869633681275")).thenReturn(Optional.of(cardFrom));
        when(cardsRepository.getByCardNumber("4802538654321851")).thenReturn(Optional.of(cardTo));
    }

    @Test
    public void transferTest() {
        final Operation operation = transferService.transfer(transfer);
        assertThat(operation.getOperationId().matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})")).isTrue();
    }

    @Test
    public void transferInsufficientFundsTest() {
        final Transfer transfer = new Transfer("4620869633681275",
                "02/23",
                "268",
                "4802538654321851",
                new Amount(1000000000, "RUB"));
        ErrorTransferException e = assertThrows(
                ErrorTransferException.class,
                () -> transferService.transfer(transfer));
        assertThat(e).hasMessageContaining("Insufficient funds");
    }

    @Test
    public void transferNotFoundCardFromTest() {
        final Transfer transfer = new Transfer("4620869633681111",
                "02/23",
                "268",
                "4802538654321851",
                new Amount(10000, "RUB"));
        ErrorTransferException e = assertThrows(
                ErrorTransferException.class,
                () -> transferService.transfer(transfer));
        assertThat(e).hasMessageContaining("Card not found (from)");
    }

    @Test
    public void transferNotFoundCardToTest() {
        final Transfer transfer = new Transfer("4620869633681275",
                "02/23",
                "268",
                "4802538654321111",
                new Amount(10000, "RUB"));
        ErrorTransferException e = assertThrows(
                ErrorTransferException.class,
                () -> transferService.transfer(transfer));
        assertThat(e).hasMessageContaining("Card not found (to)");
    }

    @Test
    public void confirmOperationCodePositiveTest() {
        final Operation operation = new Operation(UUID.randomUUID().toString(), "0000");
        final Operation _operation = transferService.confirmOperation(operation);
        assertThat(_operation.getOperationId()).isEqualTo(operation.getOperationId());
        assertThat(_operation.getOperationId().matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})")).isTrue();
    }

    @Test
    public void confirmOperationCodeNegativeTest() {
        final Operation operation = new Operation(UUID.randomUUID().toString(), "1234");
        ErrorConfirmationException e = assertThrows(
                ErrorConfirmationException.class,
                () -> transferService.confirmOperation(operation));
        assertThat(e).hasMessageContaining("Invalid code");
    }

    @Test
    public void confirmOperationNullUUIDTest() {
        final Operation operation = new Operation("", "0000");
        ErrorConfirmationException e = assertThrows(
                ErrorConfirmationException.class,
                () -> transferService.confirmOperation(operation));
        assertThat(e).hasMessageContaining("Invalid UUID");
    }
}
