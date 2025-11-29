package Siddharth.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Entity
@Table(name = "users") // PostgreSQL table name
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @NonNull
    @Column(name = "username", nullable = false)
    private String userName;

    @NonNull
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private Double balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transaction> transactions;

    private String roles;

    @Column(nullable = false)
    private String pin;
}