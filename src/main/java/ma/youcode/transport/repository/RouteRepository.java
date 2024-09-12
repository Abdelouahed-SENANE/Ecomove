package ma.youcode.transport.repository;

import ma.youcode.transport.entity.Route;

public interface RouteRepository {

    Boolean save(Route route);
    Route findRoute(Route route);
}
