package ru.savchenko.andrey.blockchain.entities;


import java.util.Date;

/**
 * Created by Andrey on 22.10.2017.
 */

public class MoneyScore {
    private int id;
    private Double max;
    private Double min;
    private Date minDate;
    private Date maxDate;

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    @Override
    public String toString() {
        return "MoneyScore{" +
                "id=" + id +
                ", max=" + max +
                ", min=" + min +
                ", minDate=" + minDate +
                ", maxDate=" + maxDate +
                '}';
    }

    public MoneyScore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public MoneyScore(int id, Double max, Double min) {
        this.id = id;
        this.max = max;
        this.min = min;
    }

    public MoneyScore(int id, Double max, Double min, Date minDate, Date maxDate) {
        this.id = id;
        this.max = max;
        this.min = min;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }
}
