package ru.savchenko.andrey.blockchain.entities.livecoin;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Andrey on 07.11.2017.
 */

public class Params {
    private String currencyPair;
    private String price;
    private String quantity;


    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Params{" +
                "currencyPair='" + currencyPair + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public Map<String, String> getQueryMap(){
        Map<String, String>hashMap = new TreeMap<>();
        hashMap.put("currencyPair", currencyPair);
//        hashMap.put("price", price);
        hashMap.put("quantity", quantity);
        return hashMap;
    }
}
