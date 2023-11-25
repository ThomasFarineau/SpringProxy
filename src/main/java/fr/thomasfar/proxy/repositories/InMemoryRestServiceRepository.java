package fr.thomasfar.proxy.repositories;

import fr.thomasfar.proxy.entities.RestService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Repository
public class InMemoryRestServiceRepository implements RestServiceRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryRestServiceRepository.class.getName());
    private final Map<String, RestService> storage = new ConcurrentHashMap<>();

    @Override
    public List<RestService> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public RestService save(String id) {
        LOGGER.info("save " + id);
        RestService restService = new RestService(id);
        storage.put(restService.getId(), restService);
        return restService;
    }

    @Override
    public Optional<RestService> findById(String id) {
        if (id == null) return Optional.empty();
        RestService restService = storage.get(id);
        LOGGER.info("findById " + id + " -> " + restService);
        if (restService == null) return Optional.empty();
        return Optional.of(restService);
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}
