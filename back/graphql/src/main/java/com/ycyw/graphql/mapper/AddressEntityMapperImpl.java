package com.ycyw.graphql.mapper;

import org.springframework.stereotype.Component;

import com.ycyw.graphql.entity.AddressEntity;
import com.ycyw.graphql.generated.types.Address;
import com.ycyw.graphql.generated.types.NewAddressInput;

/**
 * AddressEntityMapper implementation 
 */
@Component
public class AddressEntityMapperImpl implements AddressEntityMapper {

    @Override
    public AddressEntity newAddressToEntity(NewAddressInput address) {
        return AddressEntity.builder()
                .street(address.getStreet())
                .complement(address.getComplement())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .region(address.getRegion())
                .countryCode(address.getCountryCode())
                .build();
    }

    @Override
    public Address entityToAddress(AddressEntity entity) {
        return Address.newBuilder()
                .id(entity.getId().toString())
                .street(entity.getStreet())
                .complement(entity.getComplement())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .region(entity.getRegion())
                .countryCode(entity.getCountryCode())
                .build();
    }

}
