package ru.savchenko.andrey.blockchain.entities.livecoin;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientOrder {

    @SerializedName("datetime")
    @Expose
    private int datetime;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("quantity")
    @Expose
    private double quantity;
    @SerializedName("commission")
    @Expose
    private double commission;
    @SerializedName("clientorderid")
    @Expose
    private int clientorderid;

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getClientorderid() {
        return clientorderid;
    }

    public void setClientorderid(int clientorderid) {
        this.clientorderid = clientorderid;
    }

    @Override
    public String toString() {
        return "ClientOrder{" +
                "datetime=" + datetime +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", commission=" + commission +
                ", clientorderid=" + clientorderid +
                '}';
    }
}
