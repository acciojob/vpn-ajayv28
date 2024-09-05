package com.driver.services.impl;

import com.driver.exception.CountryNotFoundException;
import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin newAdmin = new Admin();
        newAdmin.setUsername(username);
        newAdmin.setPassword(password);
        return adminRepository1.save(newAdmin);
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        Optional<Admin> currAdminOpt = adminRepository1.findById(adminId);
        Admin currAdmin = currAdminOpt.get();

        ServiceProvider currSProvider = serviceProviderRepository1.findByName(providerName);

        currAdmin.getServiceProviders().add(currSProvider);
        //currSProvider.setAdmin(currAdmin);

        serviceProviderRepository1.save(currSProvider);
        return adminRepository1.save(currAdmin);
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{


        CountryName countryEnum;
        try{
            countryEnum = CountryName.valueOf(countryName.toUpperCase());
        }catch(Exception e){
            throw new CountryNotFoundException("Country not found");
        }

        Country newCountry = new Country();

        newCountry.setCountryName(countryEnum);
        newCountry.setCode(countryEnum.toCode());

        ServiceProvider currSProvider = serviceProviderRepository1.findById(serviceProviderId).get();
        newCountry.setServiceProvider(currSProvider);
        currSProvider.getCountryList().add((newCountry));

        //countryRepository1.save(newCountry);
        return  serviceProviderRepository1.save(currSProvider);
    }

}
