package de.szut.customer.database.model;

import java.time.LocalDateTime;

public class CustomerEntity {

    private Long id;

    private String name;

    private String company;

    public CustomerEntity(String name, String company) {
        this.name = name;
        this.company = company;
    }

    private LocalDateTime createDate = LocalDateTime.now();

    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
