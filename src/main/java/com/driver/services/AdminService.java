package com.driver.services;

import com.driver.model.Admin;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    abstract public Admin register(String username, String password);

    abstract public Admin addServiceProvider(int adminId, String providerName);

    abstract public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception;
}