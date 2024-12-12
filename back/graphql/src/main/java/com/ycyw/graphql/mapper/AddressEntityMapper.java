package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.AddressEntity;
import com.ycyw.graphql.generated.types.Address;
import com.ycyw.graphql.generated.types.NewAddress;

public interface AddressEntityMapper {

    public AddressEntity newAddressToEntity(NewAddress address);
    public Address entityToAddress(AddressEntity entity);
    
}
