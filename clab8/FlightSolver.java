import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

  private PriorityQueue<Flight> flightsByDeparture;
  private PriorityQueue<Flight> flightsByLanding;

  public FlightSolver(ArrayList<Flight> flights) {
    Comparator<Flight> compareByStart = (f1, f2) -> f1.startTime - f2.startTime;
    Comparator<Flight> compareByEnd = (f1, f2) -> f1.endTime - f2.endTime;
    flightsByDeparture =
        new PriorityQueue<Flight>(flights.size(), compareByStart);
    flightsByLanding = new PriorityQueue<Flight>(flights.size(), compareByEnd);
    for (Flight flight : flights) {
      flightsByDeparture.add(flight);
      flightsByLanding.add(flight);
    }
  }

  public int solve() {
    int mostPassengers = 0;
    int currentPassengers = 0;
    Flight nextDeparting = flightsByDeparture.poll();
    Flight nextLanding = flightsByLanding.poll();
    while (nextLanding != null) {
      if (nextDeparting == null ||
          nextDeparting.startTime > nextLanding.endTime) {
        currentPassengers -= nextLanding.passengers;
        nextLanding = flightsByLanding.poll();
      } else {
        currentPassengers += nextDeparting.passengers;
        if (currentPassengers > mostPassengers)
          mostPassengers = currentPassengers;
        nextDeparting = flightsByDeparture.poll();
      }
    }
    return mostPassengers;
  }
}
