package com.ask0n;

import com.ask0n.exceptions.ErrorTransferException;
import com.ask0n.models.Amount;
import com.ask0n.models.Transfer;
import com.ask0n.repositories.TransfersRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockitoTransfersRepositoryImplTests {
    @Autowired
    private TransfersRepositoryImpl transfersRepository;

    private UUID uuid1;
    private UUID uuid2;

    @BeforeAll
    public void init() {
        final Transfer transfer1 = new Transfer(
                "4620869633681275",
                "02/23",
                "268",
                "4802538654321851",
                new Amount(1000, "RUB"),
                false
        );

        final Transfer transfer2 = new Transfer(
                "4062831737058904",
                "04/25",
                "916",
                "4802538654321851",
                new Amount(1000, "RUB"),
                false
        );

        final Transfer transfer3 = new Transfer(
                "4062835498544812",
                "12/26",
                "917",
                "4802538654321851",
                new Amount(1000, "RUB"),
                false
        );

        uuid1 = transfersRepository.save(transfer1).getId();
        uuid2 = transfersRepository.save(transfer2).getId();
        transfersRepository.save(transfer3);
    }

    @Test
    public void getAllTest() {
        assertThat(transfersRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    public void getByIdTest() {
        final Transfer transfer = transfersRepository.getById(uuid1).get();
        assertThat(transfer.getId()).isEqualTo(uuid1);
        assertThat(transfer.getCardFromNumber()).isEqualTo("4620869633681275");
        assertThat(transfer.getCardToNumber()).isEqualTo("4802538654321851");
    }

    @Test
    public void saveTest() {
        final Transfer transfer = new Transfer(
                "4906943733025207",
                "12/23",
                "924",
                "4620869962546990",
                new Amount(100000, "RUB"),
                false
        );

        final Transfer _transfer = transfersRepository.save(transfer);

        assertThat(_transfer.getId().toString().matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})")).isTrue();
        assertThat(_transfer.getCardFromNumber()).isEqualTo("4906943733025207");
        assertThat(_transfer.getCardToNumber()).isEqualTo("4620869962546990");
        transfersRepository.delete(_transfer.getId());
    }

    @Test
    public void saveWithUUIDTest() {
        final Transfer transfer = new Transfer(
                "4906943733025207",
                "12/23",
                "924",
                "4620869962546990",
                new Amount(100000, "RUB")
        );

        ErrorTransferException e = assertThrows(
                ErrorTransferException.class,
                () -> transfersRepository.save(transfer)
        );
        assertThat(e).hasMessageContaining("Transfer contains uuid. But for save need empty uuid field.");
    }

    @Test
    public void deleteTest() {
        transfersRepository.delete(uuid2);

        assertThat(transfersRepository.getById(uuid2).isPresent()).isFalse();
    }

    @Test
    public void deleteNonExistsTest() {
        ErrorTransferException e = assertThrows(
                ErrorTransferException.class,
                () -> transfersRepository.delete(UUID.randomUUID())
        );
        assertThat(e).hasMessageContaining("Transfer not found");
    }
}
