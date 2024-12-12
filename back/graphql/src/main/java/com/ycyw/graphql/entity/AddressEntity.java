package com.ycyw.graphql.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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

    private String street;
  
    private String city;
  
    private String state;
  
    private String zipcode;
  
    private String country;
  
    private String timezone;

}
