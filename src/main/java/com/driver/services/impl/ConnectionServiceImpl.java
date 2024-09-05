package com.driver.services.impl;

import com.driver.exception.AlreadyConnectedException;
import com.driver.exception.AlreadyDisconnectedException;
import com.driver.exception.CannotEstablishCommunicationException;
import com.driver.exception.UnableToConnectException;
import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{
        User user = userRepository2.findById(userId).get();

        if(user.getConnected() == true)
            throw new AlreadyConnectedException("Already connected");

        if(countryName.equals(user.getOriginalCountry().toString()))
            return user;

        List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
        for(ServiceProvider serviceProvider : serviceProviderList){
            for(Country country: serviceProvider.getCountryList()){
                if(country.getCountryName().toString().equals(countryName)){
                    Connection connection = new Connection();
                    connection.setServiceProvider(serviceProvider);
                    connection.setUser(user);
                    connectionRepository2.save(connection);
                    user.setConnected(true);
                    user.getConnectionList().add(connection);
                    serviceProvider.getConnectionList().add(connection);
                    serviceProviderRepository2.save(serviceProvider);
                    String maskip = country.getCode()+"."+serviceProvider.getId()+"."+user.getId();
                    user.setMaskedIp(maskip);
                    return userRepository2.save(user);
                }
            }
        }
        throw new UnableToConnectException("Unable to connect");
    }
    @Override
    public User disconnect(int userId) throws Exception {
        User user = userRepository2.findById(userId).get();
        if(user.getConnected()==false)
            throw new AlreadyDisconnectedException("Already disconnected");

        Connection connection = user.getConnectionList().getLast();
        user.getConnectionList().remove(connection);
        user.setMaskedIp(null);
        user.setConnected(false);
        connection.getServiceProvider().getConnectionList().remove(connection);
        serviceProviderRepository2.save(connection.getServiceProvider());
        return userRepository2.save(user);


    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {

        User sender = userRepository2.findById(senderId).get();
        User receiver = userRepository2.findById(receiverId).get();

        String senderCountry = sender.getOriginalCountry().getCountryName().toCode();
        String receiverCountry;
        if(receiver.getConnected()==false)
            receiverCountry = receiver.getOriginalCountry().getCountryName().toCode();
        else
            receiverCountry = receiver.getMaskedIp().substring(0,3);

        if(receiverCountry.equals(senderCountry))
            return sender;

        for(ServiceProvider serviceProvider: sender.getServiceProviderList()){
            for(Country country:serviceProvider.getCountryList()){
                if(country.getCode().equals(receiverCountry))
                    return connect(sender.getId(), country.getCountryName().toString());
            }
        }

        throw  new CannotEstablishCommunicationException("Cannot establish communication");
    }
}
