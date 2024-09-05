// Note: Do not write @Enumerated annotation above CountryName in this model.
package com.driver.model;

import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;

import javax.persistence.*;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    CountryName countryName;

    String code;

    @JoinColumn
    @OneToOne
    User user;

    @JoinColumn
    @ManyToOne
    ServiceProvider serviceProvider;

    public CountryName getCountryName() {
        return countryName;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}

