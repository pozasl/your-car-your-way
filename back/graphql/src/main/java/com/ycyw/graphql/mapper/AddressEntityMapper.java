package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.AddressEntity;
import com.ycyw.graphql.generated.types.Address;
import com.ycyw.graphql.generated.types.NewAddressInput;

public interface AddressEntityMapper {

    public AddressEntity newAddressToEntity(NewAddressInput address);
    public Address entityToAddress(AddressEntity entity);
    
}
