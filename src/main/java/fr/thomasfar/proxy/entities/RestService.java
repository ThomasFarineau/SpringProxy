package fr.thomasfar.proxy.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class RestService {
    @Id
    private final String id;
    private Set<String> hosts = new HashSet<>();

    public void addHost(String host) {
        hosts.add(host);
    }
}
