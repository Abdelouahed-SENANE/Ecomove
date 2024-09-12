package ma.youcode.transport.entity;

import java.util.List;

public class Route {

    private String routeId;
    private String departure;
    private String destination;
    private Double distance;
    private List<Ticket> tickets;
    public String getRouteId() {
        return routeId;
    }

    public Double getDistance() {
        return distance;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
