package com.ask0n.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Basket implements Iterable<Product> {
    private List<Product> productList = new ArrayList<>();
    private int sum;
    private int countOfProducts;

    public Basket() {}
    public Basket(Product product){
        productList.add(product);
    }
    public Basket(List<Product> productList){
        this.productList = productList;
    }

    public int getSum(){
        return productList.stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum);
    }
    public int getCountOfProducts(){
        return productList.stream()
                .map(Product::getCount)
                .reduce(0, Integer::sum);
    }
    public void printProducts(){
        Product.printProducts(productList);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Продуктовая корзина:");
        productList.forEach(x -> builder.append("\n").append(x.toString()));
        return builder.toString();
    }

    @Override
    public Iterator<Product> iterator() {
        return new Iterator<Product>() {
            int nextProduct = 0;

            @Override
            public boolean hasNext() {
                return nextProduct < productList.size();
            }

            @Override
            public Product next() {
                return productList.get(nextProduct++);
            }
        };
    }
}
