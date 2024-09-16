package ma.youcode.ecomove.repository;

import ma.youcode.ecomove.entity.Route;

public interface RouteRepository {

    Boolean save(Route route);
    Route findRoute(Route route);
}
