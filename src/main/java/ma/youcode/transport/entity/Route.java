package ma.youcode.transport.entity;

public class Route {

    private String routeId;
    private String departure;
    private String destination;
    private String distance;

    public String getRouteId() {
        return routeId;
    }

    public String getDistance() {
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
    public void setDistance(String distance) {
        this.distance = distance;
    }
}
