package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.AddressEntity;
import com.ycyw.graphql.generated.types.Address;
import com.ycyw.graphql.generated.types.NewAddressInput;

/**
 * Convert Address entity
 */
public interface AddressEntityMapper {

    /**
     * Convert Address model to entity
     *
     * @param address The address model
     * @return An address entity
     */
    public AddressEntity newAddressToEntity(NewAddressInput address);

    /**
     * onvert Address entity to model 
     *
     * @param entity The address entity
     * @return The address model
     */
    public Address entityToAddress(AddressEntity entity);
    
}
