package com.ycyw.graphql.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.Title;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("account")
public class AccountEntity {

    @Id
    private Long id;

    private Title title;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private LocalDate birthDate;

    @Transient
    private AddressEntity address;
    private Long addressId;

    @Builder.Default
    private Role role = Role.CUSTOMER;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder.Default
    private Boolean enabled = true;

}
