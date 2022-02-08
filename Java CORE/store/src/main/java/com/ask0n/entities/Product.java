package com.ask0n.entities;

import java.util.List;
import java.util.UUID;

public class Product {
    private final UUID id;
    private String name;
    private int price;
    private String manufacturer;
    private int count;

    public Product(String name, int price, String manufacturer, int count) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.count = count;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        if (price > 0)
            this.price = price;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setCount(int count) {
        if (count > 0)
            this.count = count;
    }

    public static void printProducts(List<Product> productList){
        System.out.println("Доступные товары");
        for (int i = 0; i < productList.size(); i++)
            System.out.println(i + ". " + productList.get(i).toString());
        System.out.println();
    }

    @Override
    public String toString() {
        return String.format("%s; производитель: %s; Количество: %s; стоимость %s;",
                name, manufacturer, count, price);
    }
}
