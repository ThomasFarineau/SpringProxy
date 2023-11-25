package fr.thomasfar.proxy.repositories;

import fr.thomasfar.proxy.entities.RestService;

import java.util.List;
import java.util.Optional;

public interface RestServiceRepository {
    List<RestService> findAll();

    RestService save(String id);

    Optional<RestService> findById(String id);

    void deleteById(String id);
}
