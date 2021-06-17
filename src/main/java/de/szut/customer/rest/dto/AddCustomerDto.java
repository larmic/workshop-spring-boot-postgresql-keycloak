package de.szut.customer.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCustomerDto {

    private final String name;
    private final String company;

    @JsonCreator
    public AddCustomerDto(@JsonProperty("name") final String name,
                          @JsonProperty("company") final String company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }
}
