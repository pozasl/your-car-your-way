package com.ycyw.graphql.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final AddressRepository addressRepository;
    private final DatabaseClient client;

    private static final String SELECT_QUERY = """
        SELECT
            u.id u_id,
            u.email,
            u.firstname,
            u.lastname,
            u.password,
            u.birthdate,
            u.roles,
            u.created_at,
            u.updated_at,
            ua.address_id
        FROM users u
        LEFT JOIN users_address ua ON u.id = ua.user_id
    """;

    public UserRepositoryImpl(AddressRepository addressRepository, DatabaseClient client) {
        this.addressRepository = addressRepository;
        this.client = client;
    }

    @Override
    public Mono<User> findById(String id) {
        String query = String.format("%s WHERE u.id = :id", SELECT_QUERY);
        return client.sql(query)
        .bind("id", Long.parseLong(id))
        .fetch()
        .all()
        .bufferUntilChanged(r -> r.get("u_id"))
        .flatMap(this::userFromRow)
        .singleOrEmpty();
    }

    @Override
    public Mono<User> findByEmail(String email) {
        String query = String.format("%s WHERE u.email = :email", SELECT_QUERY);
        return client.sql(query)
        .bind("email", email)
        .fetch()
        .all()
        .bufferUntilChanged(r -> r.get("u_id"))
        .flatMap(this::userFromRow)
        .singleOrEmpty();
    }

    @Override
    public Flux<User> findAll() {
        String query = String.format("%s ORDER BY u.id", SELECT_QUERY);
        return client.sql(query)
        .fetch()
        .all()
        .bufferUntilChanged(result -> result.get("u_id"))
        .flatMap(this::userFromRow);
    }

    @Override
    public Mono<User> save(User user) {
        return saveUser(user).flatMap(this::saveUserAddress);
    }

    private Mono<User> userFromRow(List<Map<String, Object>> rows) {
        Map<String, Object> row = rows.get(0);
        return addressRepository.findById(row.get("address_id").toString())
            .map(address -> User.newBuilder()
            .id(row.get("u_id").toString())
            .email(row.get("email").toString())
            .firstName(row.get("firstname").toString())
            .lastName(row.get("lastname").toString())
            .password(row.get("pasword").toString())
            .birthDate(OffsetDateTime.parse(row.get("birthdate").toString()))
            .roles(List.of(Role.valueOf(row.get("roles").toString())))
            .address(address)
            .build());
    }

    private Mono<User> saveUser(User user) {
        if (user.getId() == null) {
            return client.sql("INSERT INTO users(email, firstname, lastname, password, birthdate, roles) VALUES (:email, :firstname, :lastname, :password, :birthdate, :roles)")
                    .bind("email", user.getEmail())
                    .bind("firstname", user.getFirstName())
                    .bind("lastname", user.getLastName())
                    // TODO: Encode password
                    .bind("password", user.getPassword())
                    .bind("birthdate", user.getBirthDate())
                    .bind("roles", String.join(",", user.getRoles().stream().map(r -> r.toString()).collect(Collectors.toList())))
                    .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                    .fetch().first()
                    .doOnNext(result -> user.setId(result.get("id").toString()))
                    .thenReturn(user);
        }
        // TODO: Encode password or skip password update
        String pass_update = user.getPassword() == "" ? "" : "password = :password, "; 
        return client.sql("UPDATE users SET email = :email, firstname = :fistname, " + pass_update + "birthdate = :birthdate, roles = :roles WHERE id = :id")
                    .bind("id", Long.parseLong(user.getId()))
                    .fetch().first()
                    .thenReturn(user);
    }

    private Mono<User> saveUserAddress(User user) {
        return addressRepository.save(user.getAddress())
            .flatMap(address -> client.sql("INSERT INTO users_adress(user_id, address_id) VALUES (:user_id, address_id) ON CONFLICT DO NOTHING")
                .bind("user_id",Long.parseLong(user.getId()))
                .bind("address_id", Long.parseLong(address.getId()))
                .fetch().first()
                .doOnNext(result -> user.setAddress(address))
                .thenReturn(user)
            )
        ;
    }
    
}
