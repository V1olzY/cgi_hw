package controller;

import dao.SessionDao;
import model.Seat;
import model.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionDao sessionDao;

    /**
     * Constructor for SessionController.
     * @param sessionDao Instance of SessionDao.
     */
    public SessionController(SessionDao sessionDao){
        this.sessionDao = sessionDao;
    }

    /**
     * Retrieves all sessions.
     * @return List of all sessions.
     */
    @GetMapping
    public List<Session> getAllSessions() {
        return sessionDao.getSessions();
    }

    /**
     * Retrieves a session by its ID.
     * @param id The ID of the session to retrieve.
     * @return The session with the specified ID.
     */
    @GetMapping("/{id}")
    public Session getSessionById(@PathVariable Long id) {
        return sessionDao.getSessionById(id);
    }

    /**
     * Retrieves available seats for a session.
     * @param id The ID of the session.
     * @param numOfTickets Number of tickets to find.
     * @return List of available seats for the session.
     */
    @GetMapping("/{id}/seats")
    public List<Seat> getSessionSeats(@PathVariable Long id, @RequestParam Integer numOfTickets) {

        List<Seat> availableSeats = sessionDao.getAvailableSeats(id);

        // Sort available seats by their distance from the center of the screen
        availableSeats.sort(Comparator.comparingInt(this::calculateDistanceFromCenter));

        List<Seat> preferredSeats = new ArrayList<>();

        int consecutiveSeats = 0;
        Seat lastSeat = null;

        // Select preferred seats
        for (Seat seat : availableSeats) {
            // Check if the seat is in a preferred location
            if (isPreferredLocation(seat)) {
                // If selecting multiple tickets, make sure seats are adjacent
                if (consecutiveSeats < numOfTickets) {
                    if (lastSeat == null || isAdjacent(seat, lastSeat)) {
                        preferredSeats.add(seat);
                        consecutiveSeats++;
                    } else {
                        preferredSeats.clear(); // Reset previous selection if seats are not adjacent
                        consecutiveSeats = 1; // Reset counter
                        preferredSeats.add(seat);
                    }
                } else {
                    break; // Enough seats selected
                }
                lastSeat = seat;
            }
        }

        // Check if the number of added tickets matches the expected
        while (preferredSeats.size() < numOfTickets) {
            availableSeats.remove(lastSeat);
            // Find the nearest available seat to the last selected seat
            Seat nearestSeat = findNearestAvailableSeat(lastSeat, availableSeats);

            // Check if  nearest seat is found
            if (nearestSeat != null) {
                // Add the nearest seat to the selection
                preferredSeats.add(nearestSeat);
                availableSeats.remove(nearestSeat);
                // Update last seat to the nearest seat
                lastSeat = nearestSeat;
            } else {
                // Break the loop if no more available seats
                break;
            }
        }

        return preferredSeats;
    }

    // Method to find the nearest available seat to the selected seat
    private Seat findNearestAvailableSeat(Seat selectedSeat, List<Seat> availableSeats) {
        Seat nearestSeat = null;
        double minDistance = Double.MAX_VALUE;

        // Iterate through available seats to find the nearest one
        for (Seat seat : availableSeats) {
            if (seat.isAvailable()) { // Check if the seat is available
                double distance = calculateDistance(selectedSeat, seat);
                if (distance < minDistance) { // Update the nearest seat if the distance is smaller
                    nearestSeat = seat;
                    minDistance = distance;
                }
            }
        }

        return nearestSeat;
    }

    // Method to calculate the distance between two seats
    private double calculateDistance(Seat seat1, Seat seat2) {
        int dx = seat1.getRowNr() - seat2.getRowNr();
        int dy = seat1.getSeatNr() - seat2.getSeatNr();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Method to calculate the distance from a seat to the center of the screen
    private int calculateDistanceFromCenter(Seat seat) {
        int numRows = 9; // Assume there are 9 rows
        int numSeatsInRow = 10; // Assume there are 10 seats per row

        int centralRow = numRows / 2;
        int centralSeat = numSeatsInRow / 2;

        int rowDifference = Math.abs(seat.getRowNr() - centralRow);
        int seatDifference = Math.abs(seat.getSeatNr() - centralSeat);

        return rowDifference + seatDifference;
    }

    // Method to check if a seat is in a preferred location
    private boolean isPreferredLocation(Seat seat) {
        int optimalDistance = 5; // Optimal distance from the screen center
        int minDistanceToScreen = 2; // Minimum distance to the screen

        // Check if the seat is within the optimal distance from the center and not too close to the screen
        return calculateDistanceFromCenter(seat) <= optimalDistance && seat.getRowNr() >= minDistanceToScreen;
    }

    // Method to check if two seats are adjacent
    private boolean isAdjacent(Seat seat1, Seat seat2) {
        return seat1.getRowNr() == seat2.getRowNr() && Math.abs(seat1.getSeatNr() - seat2.getSeatNr()) == 1;
    }

    /**
     * Creates a new session.
     * @param session The session object to create.
     * @return The created session object.
     */
    @Transactional
    @PostMapping
    public Session createSession(@RequestBody Session session) {
        return sessionDao.insertSession(session);
    }

    /**
     * Deletes a session by its ID.
     * @param id The ID of the session to delete.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionDao.deleteSession(id);
    }
}
