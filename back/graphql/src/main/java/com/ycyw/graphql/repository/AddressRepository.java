package com.ycyw.graphql.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ycyw.graphql.generated.types.Address;

/**
 * Address's reactive repository
 */
@Repository
public interface AddressRepository extends ReactiveCrudRepository<Address, String>{
    
}
