package com.ycyw.graphql.mapper;

import org.springframework.stereotype.Component;

import com.ycyw.graphql.entity.AddressEntity;
import com.ycyw.graphql.generated.types.Address;
import com.ycyw.graphql.generated.types.NewAddress;

@Component
public class AddressEntityMapperImpl implements AddressEntityMapper {

    @Override
    public AddressEntity newAddressToEntity(NewAddress address) {
        return AddressEntity.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .zipcode(address.getZipcode())
                .state(address.getState())
                .country(address.getCountry())
                .build();
    }

    @Override
    public Address entityToAddress(AddressEntity entity) {
        return Address.newBuilder()
                .id(entity.getId().toString())
                .street(entity.getStreet())
                .city(entity.getCity())
                .zipcode(entity.getZipcode())
                .state(entity.getState())
                .country(entity.getCountry())
                .build();
    }

}
