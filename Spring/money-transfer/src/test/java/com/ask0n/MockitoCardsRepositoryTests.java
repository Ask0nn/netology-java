package com.ask0n;

import com.ask0n.exceptions.ErrorTransferException;
import com.ask0n.models.Amount;
import com.ask0n.models.Card;
import com.ask0n.repositories.CardsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockitoCardsRepositoryTests {
    private List<Card> cardList;
    @Mock
    private JsonBD<Card> cardContext;

    @InjectMocks
    private CardsRepository cardsRepository;

    @BeforeEach
    public void setUp() {
        cardList = new ArrayList<>();
        cardList.add(new Card(UUID.fromString("8c5b5524-05d7-4f0c-bd9b-8bcfa9e03363"),
                "4620869633681275",
                "02/23",
                "268",
                new Amount(50000, "RUB")));
        cardList.add(new Card(UUID.randomUUID(),
                "4802538654321851",
                "04/23",
                "916",
                new Amount(67000, "RUB")));
        cardList.add(new Card(UUID.randomUUID(),
                "4062831737058904",
                "04/25",
                "916",
                new Amount(88000, "RUB")));
        when(cardContext.getList()).thenReturn(cardList);
    }

    @Test
    public void getAllTest() {
        assertThat(cardsRepository.getAll().isEmpty()).isFalse();
        assertThat(cardsRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    public void getByCardNumberTest() {
        final Optional<Card> card = cardsRepository.getByCardNumber("4620869633681275");
        assertThat(card.isPresent()).isTrue();
        final Card _card = card.get();
        assertThat(_card.getId()).isEqualTo(UUID.fromString("8c5b5524-05d7-4f0c-bd9b-8bcfa9e03363"));
    }

    // В этом тесте нет смысла, но как по другому я даже и не знаю.
    @Test
    public void updateTest() {
        final Card card = new Card(UUID.fromString("8c5b5524-05d7-4f0c-bd9b-8bcfa9e03363"),
                "4620869633681275",
                "02/23",
                "268",
                new Amount(53600, "RUB"));

        cardList.add(card);
        when(cardContext.getList()).thenReturn(cardList);
        cardsRepository.update(card);

        assertThat(cardsRepository.getByCardNumber("4620869633681275").get()).isEqualTo(card);
    }

    @Test
    public void updateUUIDIsNullTest() {
        final Card card = new Card(null,
                "4620869962546990",
                "12/23",
                "000",
                new Amount(5360000, "RUB"));

        ErrorTransferException e = assertThrows(
                ErrorTransferException.class,
                () -> cardsRepository.update(card)
        );

        assertThat(e).hasMessageContaining("Card UUID is empty.");
    }

    @Test
    public void updateCardNotFoundTest() {
        final Card card = new Card(UUID.fromString("8c5b5524-05d7-4f0c-bd9b-8bcfa9e03362"),
                "4620869962546990",
                "12/23",
                "000",
                new Amount(5360000, "RUB"));

        ErrorTransferException e = assertThrows(
                ErrorTransferException.class,
                () -> cardsRepository.update(card)
        );

        assertThat(e).hasMessageContaining("Card not found (old)");
    }
}
