package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a customer entity in the database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    private Long id;

    @Column(name = "firstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "birthDate", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    // Relationship mapping with sessions - many-to-many
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customerSeesion", // Join table name
            joinColumns = @JoinColumn(name = "customer_id"), // FK column in join table for Customer
            inverseJoinColumns = @JoinColumn(name = "session_id") // FK column in join table for Session
    )
    private List<Session> history; // List of sessions associated with the customer
}
