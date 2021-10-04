import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyTreeTest {
    private AirportTree sydney;
    private AirportTree brisbane;
    private Aircraft planeOne = new Aircraft("A350-X",
            "Airbus","Sydney","Hong Kong",
            460, 7000, 0,1);
    private Aircraft planeTwo = new Aircraft("737-800",
            "BOEING","Melbourne","Cairns",
            138, 4500, 0,4);
    private Aircraft planeThree = new Aircraft("A320",
            "Airbus","Brisbane","Newcastle",
            112, 4300, 0,3);
    private Aircraft planeFour = new Aircraft("787",
            "BOEING","Port Macquarie","Adelaide",
            320, 7200, 0,6);

    private Airline qantasInternational= new Airline("Qantas",
            planeOne);
    private Airline qantasDomestic = new Airline("Qantas", planeTwo);
    private Airline jetStar = new Airline("JetStar", planeThree);
    private Airline airChina = new Airline("Air China", planeFour);

    @Test(timeout=1000)
    public void doubleDepthTree(){
        //Night FLIGHT
        //here we have a plane that is full so need to create a subtree and go
        //into the night flights so a second child in the standard tree

         brisbane = new AirportTree("Brisbane YBBN");
        assertEquals("Brisbane YBBN",brisbane.getAirportName());

        brisbane.addAirline(this.qantasInternational);
        brisbane.addAirline(this.qantasDomestic);
        brisbane.addAirline(this.jetStar);
        brisbane.addAirline(this.airChina);


        brisbane.addAirlines();

        assertEquals("Qantas",
            brisbane.getAirline(1).getAirlineName());


        brisbane.getAirline(1).getPlane().setCapacity(0);

        assertEquals(0,
            brisbane.getAirline(1).getPlane().getCapacity());
        assertEquals(0,
         brisbane.getAirline(1).getPlane().getPassengersOboard());

        Passenger bob = new Passenger("Jeff Flamingo",
                1);

        brisbane.addPassenger(bob);

        //System.out.println(brisbane.generateTicket(bob));
        assertEquals("Airline Ticket\nPassenger Name : Jeff Flamingo" +
                        "\nOrigin : Sydney\nDestination : Hong Kong\nAirline : "
                        +
                        "Qantas Night Flight\nAircraft Type: A350-X\n" +
                         "Ticket ID : 1-1\n",
                this.brisbane.generateTicket(bob));

        assertEquals(0,
        brisbane.getAirline(1).getPlane().getPassengersOboard());
        assertEquals(1,
        brisbane.getNightFlightAirline(1).getPassengersOboard());
        assertEquals(40,
        brisbane.getNightFlightAirline(1).getCapacity());


    }

    @Test(timeout=1000)
    public void SingleDepthTree(){
        //Day flight single depth for subtree
        //passengers just board a normal flight

        this.sydney = new AirportTree("Sydney INL YSSY");
        this.sydney.addAirline(this.qantasInternational);
        this.sydney.addAirline(this.qantasDomestic);
        this.sydney.addAirline(this.jetStar);
        this.sydney.addAirline(this.airChina);
        assertEquals("Sydney INL YSSY", this.sydney.getAirportName());
        assertEquals("Port Macquarie",
                this.sydney.getAirline(6).getPlane().getOrigin());
        assertEquals("Adelaide",
                this.sydney.getAirline(6).getPlane().getDestination());

        Passenger sebastianVettel = new Passenger("sebastian "
        + "Vettel ",
                4);

        assertEquals(0,
                this.sydney.getAirline(4).
                        getPlane().getPassengersOboard());

        assertEquals(138,
                this.sydney.getAirline(4).getPlane().getCapacity());


        this.sydney.addPassenger(sebastianVettel);
        assertEquals("Airline Ticket\nPassenger Name : " +
         "sebastian Vettel " +
         "\nOrigin : Melbourne\nDestination : Cairns\nAirline : " +
          "Qantas\nAircraft Type: 737-800\nTicket ID : 4-1\n",
        this.sydney.generateTicket(sebastianVettel));

        assertEquals(1,
                this.sydney.getAirline(4).
                        getPlane().getPassengersOboard());



    }

}
