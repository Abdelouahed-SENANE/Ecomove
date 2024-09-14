package ma.youcode.transport.entity;

import ma.youcode.transport.enums.TransportationType;

import java.time.LocalDateTime;

public class Preference {
    private String preferenceId;
    private String preferredDeparture;
    private String preferredDestination;
    private TransportationType preferredTransportationType;
    private LocalDateTime preferredDepartureTime;
    private Passenger passenger;

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public LocalDateTime getPreferredDepartureTime() {
        return preferredDepartureTime;
    }

    public void setPreferredDeparture(String preferredDeparture) {
        this.preferredDeparture = preferredDeparture;
    }

    public void setPreferredDepartureTime(LocalDateTime preferredDepartureTime) {
        this.preferredDepartureTime = preferredDepartureTime;
    }

    public void setPreferredDestination(String preferredDestination) {
        this.preferredDestination = preferredDestination;
    }

    public void setPreferredTransportationType(TransportationType preferredTransportationType) {
        this.preferredTransportationType = preferredTransportationType;
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public String getPreferredDeparture() {
        return preferredDeparture;
    }

    public String getPreferredDestination() {
        return preferredDestination;
    }

    public TransportationType getPreferredTransportationType() {
        return preferredTransportationType;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger getPassenger() {
        return passenger;
    }
}
