package com.ask0n.repositories;

import com.ask0n.JsonBD;
import com.ask0n.exceptions.ErrorTransferException;
import com.ask0n.models.Card;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CardsRepository {
    private final JsonBD<Card> cardContext;

    public CardsRepository(JsonBD<Card> cardContext) {
        this.cardContext = cardContext;
    }

    public List<Card> getAll() {
        return cardContext.getList();
    }

    public synchronized Optional<Card> getByCardNumber(String cardNumber) {
        return cardContext.getList().stream().filter(c -> c.getCardNumber().equals(cardNumber)).findFirst();
    }

    public Card update(Card card) {
        if (card.getId() == null) throw new ErrorTransferException("Card UUID is empty.");
        final List<Card> cards = cardContext.getList();
        final Card oldCard = cards.stream()
                .filter(c -> c.getId().equals(card.getId()))
                .findFirst()
                .orElseThrow(() -> new ErrorTransferException("Card not found (old)"));
        synchronized (cardContext) {
            cards.set(cards.indexOf(oldCard), card);
            cardContext.saveFile();
        }
        return card;
    }
}
