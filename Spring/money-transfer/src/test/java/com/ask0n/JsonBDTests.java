package com.ask0n;

import com.ask0n.models.Amount;
import com.ask0n.models.Operation;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonBDTests {
    private final Path filePath = Path.of("amount.json");
    private JsonBD<Amount> amountContext;

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(filePath);

        amountContext = new JsonBD<>(filePath, Amount[].class, false);

        List<Amount> amounts = amountContext.getList();
        amounts.add(new Amount(2000, "EUR"));
        amounts.add(new Amount(15000, "RUB"));

        amountContext.saveFile();
    }

    @AfterEach
    public void delete() throws IOException {
        Files.deleteIfExists(filePath);
    }

    @Test
    public void checkFileTest() {
        assertThat(Files.exists(filePath)).isTrue();
    }

    @Test
    public void notEmptyListTest() {
        assertThat(amountContext.getList().isEmpty()).isFalse();
    }

    @Test
    public void saveTest() throws IOException {
        final Gson gson = new Gson();
        final Amount amount = new Amount(1000, "JPY");
        final List<Amount> list = amountContext.getList();
        list.add(amount);

        amountContext.saveFile();
        final List<Amount> newList = Arrays.asList(gson.fromJson(Files.readString(filePath), Amount[].class));

        assertThat(newList.get(2)).isEqualTo(amount);
    }

    @Test
    public void loadTest() throws IOException {
        //setUp
        final String uuid = UUID.randomUUID().toString();
        final Path filePath = Path.of("operation.json");
        final JsonBD<Operation> operationContext = new JsonBD<>(filePath, Operation[].class, false);
        operationContext.getList().add(new Operation(uuid, "0000"));
        operationContext.saveFile();

        final JsonBD<Operation> _operationContext = new JsonBD<>(filePath, Operation[].class, false);
        assertThat(_operationContext.getList().isEmpty()).isFalse();
        assertThat(_operationContext.getList().get(0).getOperationId()).isEqualTo(uuid);
        Files.deleteIfExists(filePath);
    }
}
