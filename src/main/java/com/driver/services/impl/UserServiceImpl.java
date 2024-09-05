package com.driver.services.impl;

import com.driver.exception.CountryNotFoundException;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{

        CountryName countryEnum;
        try{
            countryEnum = CountryName.valueOf(countryName.toUpperCase());
        }catch(Exception e){
            throw new CountryNotFoundException("No country exixt with given code");
        }

        Country newCountry = new Country();

        newCountry.setCountryName(countryEnum);
        newCountry.setCode(countryEnum.toCode());

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setConnected(false);
        newUser.setOriginalCountry(newCountry);

        newCountry.setUser(newUser);

        userRepository3.save(newUser);
        countryRepository3.save(newCountry);

        newUser.setOriginalIp(newUser.getOriginalCountry().getCode() + "." + newUser.getId());
        return userRepository3.save(newUser);

    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {

        User currUser = userRepository3.findById(userId).get();
        ServiceProvider currSProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        currUser.getServiceProviderList().add(currSProvider);
        currSProvider.getUsers().add(currUser);

        serviceProviderRepository3.save(currSProvider);
        return userRepository3.save(currUser);

    }
}
