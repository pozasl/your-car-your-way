package com.ycyw.graphql.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.ycyw.graphql.generated.types.BisTer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("address")
public class AddressEntity {

    @Id
    private Long id;

    private int streetNumber;

    private BisTer bisTer;

    private String streetType;

    private String streetName;

    private String complement;
  
    private String city;
  
    private String postalCode;

    private String region;
  
    private String countryCode;
  
}
