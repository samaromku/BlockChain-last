package ru.savchenko.andrey.blockchain.entities.livecoin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("client_id")
    @Expose
    private int clientId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("quantity")
    @Expose
    private double quantity;
    @SerializedName("remaining_quantity")
    @Expose
    private double remainingQuantity;
    @SerializedName("blocked")
    @Expose
    private double blocked;
    @SerializedName("blocked_remain")
    @Expose
    private int blockedRemain;
    @SerializedName("commission_rate")
    @Expose
    private double commissionRate;
    @SerializedName("trades")
    @Expose
    private Object trades;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(double remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public double getBlocked() {
        return blocked;
    }

    public void setBlocked(double blocked) {
        this.blocked = blocked;
    }

    public int getBlockedRemain() {
        return blockedRemain;
    }

    public void setBlockedRemain(int blockedRemain) {
        this.blockedRemain = blockedRemain;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Object getTrades() {
        return trades;
    }

    public void setTrades(Object trades) {
        this.trades = trades;
    }

}
