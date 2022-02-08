package com.ask0n;

import com.ask0n.models.Amount;
import com.ask0n.models.Operation;
import com.ask0n.models.Transfer;
import com.ask0n.repositories.TransfersRepositoryImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockitoTransferControllerTest {
    private final Transfer transfer = new Transfer("4620869633681275",
            "02/23",
            "268",
            "4802538654321851",
            new Amount(10000, "RUB"));

    private final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransfersRepositoryImpl transfersRepository;

    @Test
    public void transferReturnsHTTPStatus200() throws Exception {
        when(transfersRepository.save(any(Transfer.class))).thenReturn(transfer);

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(transfer)))
                .andExpect(status().is(200));
    }

    @Test
    public void transferReturnsHTTPStatus400() throws Exception {
        final Transfer transfer = new Transfer("1111111111111111111111111111111111111111",
                "02/23",
                "268",
                "4802538654321851",
                new Amount(10000, "RUB"));

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(transfer)))
                .andExpect(status().is(400));
    }

    @Test
    public void transferReturnsHTTPStatus500() throws Exception {
        final Transfer transfer = new Transfer("4620869633681211",
                "02/23",
                "268",
                "4802538654321851",
                new Amount(1000, "RUB"));

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(transfer)))
                .andExpect(status().is(500));
    }

    @Test
    public void confirmOperationReturnsHTTPStatus200() throws Exception {
        String json = gson.toJson(new Operation(UUID.randomUUID().toString(), "0000"));

        when(transfersRepository.getById(any(UUID.class))).thenReturn(Optional.of(transfer));

        mockMvc.perform(post("/confirmOperation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(200));
    }

    @Test
    public void confirmOperationReturnsHTTPStatus400() throws Exception {
        String json = gson.toJson(new Operation(null, null));

        mockMvc.perform(post("/confirmOperation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400));
    }

    @Test
    public void confirmOperationReturnsHTTPStatus500() throws Exception {
        String json = gson.toJson(new Operation(UUID.randomUUID().toString(), "1000"));

        mockMvc.perform(post("/confirmOperation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(500));
    }
}
