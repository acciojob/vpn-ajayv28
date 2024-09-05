package com.driver.repository;

import com.driver.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Integer> {

    public ServiceProvider findByName(String name);

}
