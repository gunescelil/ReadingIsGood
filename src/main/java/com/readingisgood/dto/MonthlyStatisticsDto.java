package com.readingisgood.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthlyStatisticsDto extends JsonObjectBase
{
    @JsonProperty("month")
    private String month;

    @JsonProperty("totalOrderCount")
    private int totalOrderCount;

    @JsonProperty("totalBookCount")
    private int totalBookCount;

    @JsonProperty("totalPurchasedAmount")
    private double totalPurchasedAmount;
    
    public MonthlyStatisticsDto()
    {
        super();
    }

    public MonthlyStatisticsDto(String month, int totalOrderCount, int totalBookCount, double totalPurchasedAmount)
    {
        super();
        this.month = month;
        this.totalOrderCount = totalOrderCount;
        this.totalBookCount = totalBookCount;
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public int getTotalOrderCount()
    {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount)
    {
        this.totalOrderCount = totalOrderCount;
    }

    public int getTotalBookCount()
    {
        return totalBookCount;
    }

    public void setTotalBookCount(int totalBookCount)
    {
        this.totalBookCount = totalBookCount;
    }

    public double getTotalPurchasedAmount()
    {
        return totalPurchasedAmount;
    }

    public void setTotalPurchasedAmount(double totalPurchasedAmount)
    {
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

}
