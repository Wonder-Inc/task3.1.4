package ru.itm.resttemplates;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.itm.resttemplates.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestConnection {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final String resourceUrl = "http://94.198.50.185:7081/api/users";
    
    public RestConnection(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
        this.headers.set("Cookie", String.join(";", restTemplate.headForHeaders(resourceUrl).get("Set-Cookie")));
    }
    
    public String getResult() {
        return addUser().getBody() + updateUser().getBody() + deleteUser().getBody();
    }
    
    private List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(resourceUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();
    }
    
    private ResponseEntity<String> addUser() {
        User user = new User(3L, "James", "Brown", (byte) 5);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.postForEntity(resourceUrl, entity, String.class);
    }
    
    private ResponseEntity<String> updateUser() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 5);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, String.class, 3);
    }
    
    private ResponseEntity<String> deleteUser() {
        Map<String, Long> uriVariables = new HashMap<>() {{
            put("id", 3L);
        }};
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(resourceUrl + "/{id}", HttpMethod.DELETE, entity, String.class, uriVariables);
    }
}
