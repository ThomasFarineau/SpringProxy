package fr.thomasfar.proxy.services;

import fr.thomasfar.proxy.entities.RestService;
import fr.thomasfar.proxy.repositories.RestServiceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class GatewayService {
    private static final Logger LOGGER = Logger.getLogger(GatewayService.class.getName());

    private final RestServiceRepository restServiceRepository;

    public GatewayService(RestServiceRepository restServiceRepository) {
        this.restServiceRepository = restServiceRepository;
    }

    public ResponseEntity<?> forwardRequest(String id, HttpServletRequest request) {
        LOGGER.info("forwardRequest " + id);
        Optional<RestService> restService = restServiceRepository.findById(id);
        if (restService.isPresent()) {
            RestService service = restService.get();
            if (!service.getHosts().isEmpty()) {
                String host = selectHost(service.getHosts());
                String redirectUrl = buildRedirectUrl(host, request, id);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", redirectUrl);
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
        }

        return ResponseEntity.notFound().build();
    }

    private String selectHost(Set<String> hosts) {
        LOGGER.info("selectHost " + hosts);
        if (hosts == null || hosts.isEmpty()) {
            return null;
        }
        // il faut faire le load balancing ici
        // pour l'instant : on prend un host au hasard
        int index = new Random().nextInt(hosts.size());
        return new ArrayList<>(hosts).get(index);
    }

    private String buildRedirectUrl(String host, HttpServletRequest request, String id) {
        LOGGER.info("buildRedirectUrl " + host);
        StringBuilder redirectUrl = new StringBuilder(host);

        // Ajoutez le chemin et les paramètres de la requête
        String pathAfterId = request.getRequestURI().substring(request.getRequestURI().indexOf(id) + id.length());
        redirectUrl.append(pathAfterId);

        String query = request.getQueryString();
        if (query != null && !query.isEmpty()) {
            redirectUrl.append("?").append(query);
        }

        return redirectUrl.toString();
    }
}
