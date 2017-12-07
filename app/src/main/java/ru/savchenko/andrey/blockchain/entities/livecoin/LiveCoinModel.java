package ru.savchenko.andrey.blockchain.entities.livecoin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveCoinModel {

    @SerializedName("cur")
    @Expose
    private String cur;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("last")
    @Expose
    private double last;
    @SerializedName("high")
    @Expose
    private double high;
    @SerializedName("low")
    @Expose
    private double low;
    @SerializedName("volume")
    @Expose
    private double volume;
    @SerializedName("vwap")
    @Expose
    private double vwap;
    @SerializedName("max_bid")
    @Expose
    private int maxBid;
    @SerializedName("min_ask")
    @Expose
    private double minAsk;
    @SerializedName("best_bid")
    @Expose
    private double bestBid;
    @SerializedName("best_ask")
    @Expose
    private double bestAsk;

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVwap() {
        return vwap;
    }

    public void setVwap(double vwap) {
        this.vwap = vwap;
    }

    public int getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(int maxBid) {
        this.maxBid = maxBid;
    }

    public double getMinAsk() {
        return minAsk;
    }

    public void setMinAsk(double minAsk) {
        this.minAsk = minAsk;
    }

    public double getBestBid() {
        return bestBid;
    }

    public void setBestBid(double bestBid) {
        this.bestBid = bestBid;
    }

    public double getBestAsk() {
        return bestAsk;
    }

    public void setBestAsk(double bestAsk) {
        this.bestAsk = bestAsk;
    }

    @Override
    public String toString() {
        return "LiveCoinModel{" +
                "cur='" + cur + '\'' +
                ", symbol='" + symbol + '\'' +
                ", last=" + last +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                ", vwap=" + vwap +
                ", maxBid=" + maxBid +
                ", minAsk=" + minAsk +
                ", bestBid=" + bestBid +
                ", bestAsk=" + bestAsk +
                '}';
    }
}
