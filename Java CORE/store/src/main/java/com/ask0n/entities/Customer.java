package com.ask0n.entities;

import java.util.UUID;

public class Customer extends User{
    private Basket basket;

    public Customer(UUID id, String name) {
        super(id, name);
    }

    public Basket getBasket() {
        return basket;
    }
    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Override
    public String toString(){
        return String.format("Покупатель: %s" +
                        "\n%s" +
                        "\nВсего товаров - %s" +
                        "\nОбщая стоимость - %s",
                getName(), basket.toString(), basket.getCountOfProducts(), basket.getSum());
    }
}
