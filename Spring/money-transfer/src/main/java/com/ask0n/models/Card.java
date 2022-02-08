package com.ask0n.models;

import java.util.UUID;

public class Card {
    private UUID id;
    private String cardNumber;
    private String cardTill;
    private String cardCCV;
    private Amount amount;

    public Card() {}
    public Card(UUID id, String cardNumber, String cardTill, String cardCCV, Amount amount) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardTill = cardTill;
        this.cardCCV = cardCCV;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }
    public void setId() {
        this.id = UUID.randomUUID();
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getCardTill() {
        return cardTill;
    }
    public void setCardTill(String cardTill) {
        this.cardTill = cardTill;
    }
    public String getCardCCV() {
        return cardCCV;
    }
    public void setCardCCV(String cardCCV) {
        this.cardCCV = cardCCV;
    }
    public Amount getAmount() {
        return amount;
    }
    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
