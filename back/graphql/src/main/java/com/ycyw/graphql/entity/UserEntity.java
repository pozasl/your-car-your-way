package com.ycyw.graphql.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.ycyw.graphql.generated.types.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate birthDate;

    private AddressEntity address;

    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
