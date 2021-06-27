package com.readingisgood.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsDto extends JsonObjectBase
{
    @JsonProperty("statistics")
    private List<MonthlyStatisticsDto> statistics;

    public StatisticsDto(List<MonthlyStatisticsDto> statistics)
    {
        super();
        this.statistics = statistics;
    }

    public StatisticsDto()
    {
        super();
    }

    public List<MonthlyStatisticsDto> getStatistics()
    {
        return statistics;
    }

    public void setStatistics(List<MonthlyStatisticsDto> statistics)
    {
        this.statistics = statistics;
    }

}
