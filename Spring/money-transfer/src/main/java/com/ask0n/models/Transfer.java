package com.ask0n.models;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Validated
public class Transfer {
    public static final double COMMISSION = 0.01;

    private UUID id;
    @Pattern(regexp = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$")
    private String cardFromNumber;
    @Pattern(regexp = "(?:0[1-9]|1[0-2])/[0-9]{2}")
    private String cardFromValidTill;
    @Size(min = 3, max = 3)
    private String cardFromCVV;
    @Pattern(regexp = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$")
    private String cardToNumber;
    private Amount amount;

    public Transfer() {}
    public Transfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Amount amount) {
        this (cardFromNumber, cardFromValidTill, cardFromCVV, cardToNumber, amount, true);
    }
    public Transfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Amount amount, Boolean withId) {
        if (withId) this.id = UUID.randomUUID();
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }
    public void setId() {
        this.id = UUID.randomUUID();
    }
    public String getCardFromNumber() {
        return cardFromNumber;
    }
    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }
    public String getCardFromValidTill() {
        return cardFromValidTill;
    }
    public void setCardFromValidTill(String cardFromValidTill) {
        this.cardFromValidTill = cardFromValidTill;
    }
    public String getCardFromCVV() {
        return cardFromCVV;
    }
    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }
    public String getCardToNumber() {
        return cardToNumber;
    }
    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }
    public Amount getAmount() {
        return amount;
    }
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", cardFromNumber='" + cardFromNumber + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
