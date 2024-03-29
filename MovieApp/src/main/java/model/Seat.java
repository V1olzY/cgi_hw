package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a seat entity in the cinema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    /**
     * The row number of the seat.
     */
    private int rowNr;

    /**
     * The seat number within the row.
     */
    private int seatNr;

    /**
     * Indicates whether the seat is available or not.
     */
    private boolean isAvailable;
}
