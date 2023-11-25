package fr.thomasfar.proxy.services;

import fr.thomasfar.proxy.components.dto.ServiceDtoIn;
import fr.thomasfar.proxy.entities.RestService;
import fr.thomasfar.proxy.repositories.InMemoryRestServiceRepository;
import fr.thomasfar.proxy.repositories.RestServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RestServiceService {
    private static final Logger LOGGER = Logger.getLogger(RestServiceService.class.getName());

    private final RestServiceRepository restServiceRepository;

    public RestServiceService(RestServiceRepository restServiceRepository) {
        this.restServiceRepository = restServiceRepository;
    }

    public void add(ServiceDtoIn serviceDtoIn) {
        LOGGER.info("add " + serviceDtoIn);
        Optional<RestService> restService = restServiceRepository.findById(serviceDtoIn.getId());
        if (restService.isPresent()) {
            LOGGER.info("add host " + serviceDtoIn.getHost() + " to " + restService.get());
            restService.get().addHost(serviceDtoIn.getHost());
            return;
        }
        RestService newRestService = restServiceRepository.save(serviceDtoIn.getId());
        newRestService.addHost(serviceDtoIn.getHost());
    }

    public List<RestService> getAll() {
        return restServiceRepository.findAll();
    }
}
