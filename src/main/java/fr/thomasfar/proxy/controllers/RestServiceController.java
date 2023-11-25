package fr.thomasfar.proxy.controllers;

import fr.thomasfar.proxy.components.dto.ServiceDtoIn;
import fr.thomasfar.proxy.entities.RestService;
import fr.thomasfar.proxy.services.RestServiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restservices")
@Tag(name = "Rest Services Management")
public class RestServiceController {
    private final RestServiceService restServiceService;

    public RestServiceController(RestServiceService restServiceService) {
        this.restServiceService = restServiceService;
    }

    @PostMapping("/")
    public void add(@RequestBody ServiceDtoIn serviceDtoIn) {
        restServiceService.add(serviceDtoIn);
    }

    @GetMapping()
    public List<RestService> getAll() {
        return restServiceService.getAll();
    }
}
