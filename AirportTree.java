import sun.awt.image.ImageWatched;
import sun.reflect.generics.tree.Tree;

import java.util.LinkedList;
/**
 * A standard Airport which "Utilises" the given Standard Tree
 * CDT managing passengers and whether they can flight on an airlines normal
 * or night service from their origin to destination. As well as creating a
 * printable ticket to board a flight.
 *
 * @author Nikhil Naik
 */
public class AirportTree {
    //Name of the airport to manage
    private String airportName;
    //Parent tree of the airport
    private StandardTree<Airline> airport;
    //Lists to manage data from either normal or night flight
    private LinkedList<Airline> airlineList = new LinkedList<>();
    private LinkedList<Airline> airlineNight = new LinkedList<>();
    //Lists to manage the subtrees of the airlines
    private LinkedList<StandardTree> airlineTree = new LinkedList<>();
    private LinkedList<StandardTree> airlineNightSubTree = new LinkedList<>();

    /**
     * Constructs a new Airport with a single node
     *
     * @param airportName the element to store at this Airport tree's root
     */
    public AirportTree (String airportName) {
        //an airport tree only needs a name to exist can work for airports
        // around the globe
        this.airportName = airportName;
        this.airport = new StandardTree<>(null);
    }

    /**
     * Returns the name of the airport
     *
     * @return  The name of the airport
     */
    public String getAirportName() {
        return this.airportName;
    }

    /**
     * Adds an airline to the Airport
     *
     * @param airliner the aircraft to be added to the airport tree
     */
    public void addAirline (Airline airliner) {
        if (!this.airlineList.contains(airliner)) {
            this.airlineList.add(airliner);
        }
    }

    /**
     * Adds to the list of subtrees for the airline, as well as add the
     * airline to be a subtree of the airport
     */
    public void addAirlines() {
        for (Airline A : this.airlineList) {
            this.airport.addChild(new StandardTree<>(A));
            this.airlineTree.add(new StandardTree<>(A));
        }
    }

    /**
     * Returns an airline given a route number
     *
     * @param routeId the ID number of the flight
     *
     */
    public Airline getAirline(int routeId) {
        for (Airline airline : this.airlineList) {
            if (airline.getPlane().getRouteID() == routeId) {
                return airline;
            }
        }
        return null;
    }

    /**
     * Returns a readable passenger ticket
     *
     * @param pas the passenger to get ticket of
     * @param air the airline the passenger is on
     *
     */
    public String stringBuilder(Passenger pas,Airline air) {
        StringBuilder ticket = new StringBuilder();
        ticket.append("Airline Ticket" + "\n");
        ticket.append("Passenger Name : " + pas.getName() +
                "\n");
        ticket.append("Origin : " + air.getPlane().getOrigin() +
                "\n");
        ticket.append("Destination : " +
                air.getPlane().getDestination() + "\n");
        ticket.append("Airline : " + air.getAirlineName() + "\n");
        ticket.append("Aircraft Type: " +
                air.getPlane().getAircraftName() + "\n");
        ticket.append("Ticket ID : " +
                pas.getPassengerRouteID() + "-" +
                air.getPlane().getPassengersOboard() + "\n");
        return ticket.toString();
    }

    /**
     * Returns readable ticket for a passenger whom is either on a day flight
     *  or a night flight
     *
     * @param passenger the passenger to produce a ticket for
     *
     */
    public String generateTicket(Passenger passenger) {
        for (Airline Plane  : this.airlineNight) {
            if (Plane.getPlane().getRouteID() ==
                    passenger.getPassengerRouteID()) {
                return stringBuilder(passenger,Plane);
            }
        }
        for (Airline Plane  : this.airlineList) {
           if (Plane.getPlane().getRouteID() ==
                    passenger.getPassengerRouteID()) {
               return stringBuilder(passenger,Plane);
           }
        }
        return null;
    }

    /**
     * Returns an aircraft given a route id
     *
     * @param routeId the ID number of the flight
     *
     */
    public Aircraft getNightFlightAirline(int routeId) {
        for (StandardTree<Airline> airTree : this.airlineNightSubTree) {
            if (airTree.getRoot().getPlane().getRouteID() == routeId) {
                return airTree.getRoot().getPlane();
            }
        }
        return null;
    }

    /**
     * Attempts to add a passenger to a either a day flight, if this is full,
     *  create a new sub-tree which branches from the current airline and
     *  creates a night flight and adds the passenger to the night flight
     *
     * @param person passenger to be added to a flight that matches the ID
     * for the flight and passenger flight ID.
     *
     */
    public void addPassenger(Passenger person) {
        for (Airline airline : this.airlineList) {
            if (airline.getPlane().getRouteID() == person.getPassengerRouteID()) {
                if (!airline.getPlane().overCapacity()){
                    //Normal Flight no subtree standard tree required
                    airline.getPlane().addPassenger(person);

                } else {
                    //Normal flight is overcapacity subtree STD Tree required
                    int size = this.airlineTree.size();
                    for (int i = 0; i < size; i++) {
                        if (this.airlineTree.get(i).getRoot().equals(airline)){
                            //Create a new Aircraft
                            Aircraft nightFlightPlane =
                              new Aircraft(airline.getPlane().getAircraftName(),
                              airline.getPlane().getManufacture(),
                              airline.getPlane().getOrigin(),
                              airline.getPlane().getDestination(),
                              airline.getPlane().getCapacity(),
                              airline.getPlane().getRange(),
                              airline.getPlane().getPassengersOboard(),
                              airline.getPlane().getRouteID());

                            nightFlightPlane.resetFlight();
                            nightFlightPlane.setAircraftName
                                    (airline.getPlane().getAircraftName());
                            nightFlightPlane.addPassenger(person);

                            Airline nightFlight =
                                    new Airline(airline.getAirlineName() +
                                     " Night Flight", nightFlightPlane);
                            //Add the aircraft to the night flight list and
                            // add as a child the root(Current Aircraft)
                            this.airlineTree.get(i).addChild(new
                                    StandardTree<Airline>(nightFlight));
                            this.airlineNightSubTree.add(new
                                    StandardTree<Airline>(nightFlight));
                            if (!this.airlineNight.contains(nightFlight)) {
                                this.airlineNight.add(nightFlight);
                            }
                        }
                    }
                }
            }
        }
    }
}
/**
 * Aircraft can either be from BOEING or Airbus, and has a predefined flight
 * origin and destination, this can be added to an Airlines fleet tree
 */
class Aircraft {
    //Charactertics of an Aircraft that is direct from the manufacturer
    private  String aircraftName;
    private  String manufacture;
    private String origin;
    private String destination;
    private  int capacity;
    private  int range;
    private LinkedList<Passenger> passengers = new LinkedList<>();
    private int passengersOnboard = 0;
    private int routeID;
    //The night flight must have at least 40 passengers to operate
    final int minimumCapacity = 40;

    /**
     * Constructs a new Aircraft
     *
     * @param aircraftName
     * @param manufacture
     * @param origin
     * @param destination
     * @param capacity
     */
    public Aircraft(String aircraftName, String manufacture, String origin,
     String destination,
            int capacity,
            int range, int passengersOnboard, int routeID){
            //Aicraft chracteristics
            this.aircraftName = aircraftName;
            this.manufacture = manufacture;
            this.origin = origin;
            this.destination = destination;
            this.capacity = capacity;
            this.range = range;
            this.passengersOnboard = passengersOnboard;
            this.routeID = routeID;
    }

    /**
     * Returns the destination of the aircraft
     *
     * @return destination of the aircraft
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * Returns the origin of the aircraft
     *
     * @return origin of the aircraft
     */
    public String getOrigin() {
        return this.origin;
    }

    public void resetFlight () {
        this.passengersOnboard = 0;
        this.setCapacity(minimumCapacity);
    }

    /**
     * Update the number of passengers allowed on board, could change due to
     * FFA regulations
     *
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Change the name of the aircraft
     *
     * @param aircraftName
     */
    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }

    /**
     * Returns the capacity of the aircraft
     *
     * @return capacity of the aircraft
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the name of the aircraft
     *
     * @return name of the aircraft
     */
    public String getAircraftName() {
        return this.aircraftName;
    }

    /**
     * Returns the manufactere of the aircraft
     *
     * @return manufactere of the aircraft
     */
    public String getManufacture() {
        return this.manufacture;
    }

    /**
     * Returns the number of passengers of the aircraft
     *
     * @return number of passengers of the aircraft
     */
    public int getPassengersOboard() {
        return this.passengersOnboard;
    }

    /**
     * Returns the range in km of the aircraft
     *
     * @return range of the aircraft
     */
    public int getRange() {
        return this.range;
    }

    /**
     * Returns the ID of the aircraft
     *
     * @return ID of the aircraft
     */
    public int getRouteID() {
        return this.routeID;
    }

    /**
     * Returns if the aircraft is over capacity
     *
     * @return boolean if over capacity true
     */
    public boolean overCapacity() {
        if (this.passengersOnboard >= this.capacity) {
            return true;
        }
        return false;
    }

    /**
     * Add a passenger to the aircraft if possible
     *
     * @param person
     */
    public void addPassenger (Passenger person) {
        if (person != null && person.getPassengerRouteID() == this.routeID) {
            passengers.add(person);
            this.passengersOnboard++;
        }
    }
}

/**
 * Airlines have a fleet of aircraft and form part of the standard tree
 * utilisation
 */
class Airline {
    private String airlineName;
    private Aircraft plane;

    /**
     * Constructs a new Airline
     *
     * @param airlineName
     * @param plane
     */
    public Airline (String airlineName, Aircraft plane) {
        //Make up of an airline
        this.airlineName = airlineName;
        this.plane = plane;
    }

    /**
     * Returns the plane in the fleet
     *
     * @return plane in the fleet
     */
    public Aircraft getPlane() {
        return this.plane;
    }

    /**
     * Returns the name of the Airline
     *
     * @return name of the airline
     */
    public String getAirlineName() {
        return this.airlineName;
    }
}

/**
 * Passengers arrive at the airport and board a flight, given they have pre
 * booked their flight, they board an airline and extend as an element from
 * the Standard Tree.
 */
class Passenger {
    private  String name;
    private  int passengerRouteID;

    /**
     * Constructs a new Passenger
     *
     * @param name
     * @param passengerRouteID
     */
    public Passenger(String name, int passengerRouteID){
        //makeup of a passenger
        this.name = name;
        this.passengerRouteID = passengerRouteID;
    }

    /**
     * Returns the route ID of the passenger
     *
     * @return route ID of the passenger
     */
    public int getPassengerRouteID() {
        return this.passengerRouteID;
    }

    /**
     * Returns the name of the passenger
     *
     * @return name of the passenger
     */
    public String getName() {
        return this.name;
    }
}