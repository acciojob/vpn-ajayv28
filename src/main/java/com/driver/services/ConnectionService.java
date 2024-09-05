package com.driver.services;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ConnectionService {

    public abstract User connect(int userId, String countryName) throws Exception;

    public abstract User disconnect(int userId) throws Exception;

    public abstract User communicate(int senderId, int receiverId) throws Exception;
}