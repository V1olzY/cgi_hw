package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * Represents a session of a movie in the cinema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {

    /**
     * The unique identifier for the session.
     */
    @Id
    @SequenceGenerator(name = "session_seq", sequenceName = "session1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_seq")
    private Long id;

    /**
     * The movie being screened in this session.
     */
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    /**
     * The language in which the movie is being screened.
     */
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    /**
     * The hall number where the session is taking place.
     */
    @Column(name = "hallNr", nullable = false, length = 20)
    private String hallNr;

    /**
     * The start time of the session.
     */
    @Column(name = "startAt", nullable = false)
    private LocalDateTime startAt;

    /**
     * The price of admission for this session.
     */
    @Column(name = "price", nullable = false, precision = 2)
    private float price;

}
