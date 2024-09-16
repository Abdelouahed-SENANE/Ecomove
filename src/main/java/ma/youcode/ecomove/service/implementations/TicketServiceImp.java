package ma.youcode.ecomove.service.implementations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import ma.youcode.ecomove.entity.Contract;
import ma.youcode.ecomove.entity.Route;
import ma.youcode.ecomove.entity.SpecialOffer;
import ma.youcode.ecomove.entity.Ticket;
import ma.youcode.ecomove.enums.DiscountType;
import ma.youcode.ecomove.enums.TransportationType;
import ma.youcode.ecomove.repository.ContractRepository;
import ma.youcode.ecomove.repository.RouteRepository;
import ma.youcode.ecomove.repository.TicketRepository;
import ma.youcode.ecomove.repository.implementations.ContractRepositoryImp;
import ma.youcode.ecomove.repository.implementations.RouteRepositoryImp;
import ma.youcode.ecomove.repository.implementations.TicketRepositoryImp;

public class TicketServiceImp implements ma.youcode.ecomove.service.TicketService {
    private final TicketRepository ticketRepository;
    private final ContractRepository contractRepository;
    private final RouteRepository routeRepository;
    public TicketServiceImp() {
        this.ticketRepository = new TicketRepositoryImp();
        this.contractRepository = new ContractRepositoryImp();
        this.routeRepository = new RouteRepositoryImp();
    }

    @Override
    public Ticket addTicket(Ticket ticket)  {


        Route existingRoute = routeRepository.findRoute(ticket.getRoute());

        if (existingRoute != null) {
            ticket.setRoute(existingRoute);
            ticket.setBoughtFor(calculateBuyingPrice(ticket));
        } else {
            Route newRoute = new Route();
            newRoute.setRouteId(UUID.randomUUID().toString());
            newRoute.setDistance(ticket.getRoute().getDistance());
            newRoute.setDestination(ticket.getRoute().getDestination());
            newRoute.setDeparture(ticket.getRoute().getDeparture());
            routeRepository.save(newRoute);
            ticket.setRoute(newRoute);
        }
        Ticket savedTicket = this.ticketRepository.save(ticket);
        return savedTicket;
    }

    @Override
    public Ticket updateTicket(Ticket ticket)  {
        return ticketRepository.update(ticket);
    }

    @Override
    public Ticket getTicket(String ticketId)  {
        return ticketRepository.findTicketById(ticketId);
    }

    @Override
    public List<Ticket> getAllTickets()  {
        return ticketRepository.findAllTickets();
    }

    @Override
    public Ticket deleteTicket(String ticketId)  {
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        if (ticket != null) {
            ticketRepository.delete(ticket);
        }
        return ticket;
    }

    private  Double calculateBuyingPrice(Ticket ticket) {
        Double buyingPrice = 0.0;
        Contract contract = ticket.getContract();
        if (contract != null) {
                buyingPrice = ticket.getBoughtFor() - (ticket.getBoughtFor() * contract.getSpecialRate());
                if (contract.getSpecialOffers().size() > 0) {
                    Double fixedPrice = 0.0;
                    Double percentage = 0.0;
                    for (SpecialOffer specialOffer : ticket.getContract().getSpecialOffers()) {
                        if (specialOffer.getDiscountType().equals(DiscountType.FIX_AMOUNT)){
                            fixedPrice += specialOffer.getDiscountValue();
                        }else {
                            percentage += specialOffer.getDiscountValue();
                        }
                    }
                    buyingPrice = buyingPrice -  (fixedPrice  + ( buyingPrice * percentage));
                }
        } else {
            System.out.println("Contract not found.");
        }
        return buyingPrice;
    }

    @Override
    public List<List<Ticket>> availbeJourneys(String departure, String destination, LocalDateTime departureDateTime , TransportationType type) {
        List<Ticket> tickets = ticketRepository.findAllTickets();
        List<Ticket> validTickets = tickets.stream()
                .filter(ticket ->  ticket.getDepartureTime().isAfter(departureDateTime)
                        && ticket.getContract().getPartner().getTransportationType().equals(type))
                .collect(Collectors.toList());

        Map<String , List<Ticket>> graph = buildGraph(validTickets);

        List<List<Ticket>> journeys = findAllRoutes(graph, departure, destination);

        return journeys;
    }


    private static Map<String , List<Ticket>> buildGraph(List<Ticket> tickets) {
        Map<String , List<Ticket>> graph = new HashMap<>();

        for (Ticket ticket : tickets) {
            String departure = ticket.getRoute().getDeparture();
            graph.computeIfAbsent(departure, k -> new ArrayList<>()).add(ticket);
        }
        return graph;
    }


    private static List<List<Ticket>> findAllRoutes(Map<String , List<Ticket>> graph , String nodeStart , String nodeEnd) {
        List<List<Ticket>> allPaths = new ArrayList<>();
        Queue<List<Ticket>> queue = new LinkedList<>();

        if (graph.containsKey(nodeStart)) {
            for (Ticket ticket : graph.get(nodeStart)){
                List<Ticket> path = new ArrayList<>();
                path.add(ticket);
                queue.add(path);
            }
        }

        while (!queue.isEmpty()) {
            List<Ticket> currentPath = queue.poll();
            Ticket lastTicket = currentPath.get(currentPath.size() - 1);

            if (lastTicket.getRoute().getDestination().equals(nodeEnd)) {
                allPaths.add(currentPath);
            }else {
                if (graph.containsKey(lastTicket.getRoute().getDestination())) {
                    for (Ticket nextTicket : graph.get(lastTicket.getRoute().getDestination())) {
                        if (calculateArrivedTime(lastTicket.getDepartureTime() , lastTicket.getDuration()).isBefore(nextTicket.getDepartureTime())
                        && calculateDiffTimeInHours(lastTicket , nextTicket) < 5) {
                            List<Ticket> newPath = new ArrayList<>(currentPath);
                            newPath.add(nextTicket);
                            queue.add(newPath);
                        }
                    }
                }
            }

        }

        return allPaths;
    }
    // Calculate arrival time
    public static LocalDateTime calculateArrivedTime(LocalDateTime departure , int duration) {
        return  departure.plusMinutes(duration);
    }
    public static Long calculateDiffTimeInHours(Ticket lastTicketArrival , Ticket nextTicketDeparture) {
        Instant arrivalTimeLastTicket = calculateArrivedTime(lastTicketArrival.getDepartureTime() , lastTicketArrival.getDuration()).toInstant(ZoneOffset.UTC);
        Instant departureTimeNextTicket = nextTicketDeparture.getDepartureTime().toInstant(ZoneOffset.UTC);

        long differenceInMillis = departureTimeNextTicket.toEpochMilli() - arrivalTimeLastTicket.toEpochMilli();
        return differenceInMillis / (1000 * 60 * 60);

    }
        @Override
        public Boolean markAsSold(Ticket ticket) {
            return this.ticketRepository.updateStatus(ticket);
        }
}
