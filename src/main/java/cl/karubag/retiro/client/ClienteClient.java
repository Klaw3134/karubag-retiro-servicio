package cl.karubag.retiro.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://karubag-cliente-servicio.onrender.com")
                .build();
    }

    public boolean existeCliente(Long clienteId) {
        try {
            webClient.get()
                    .uri("/api/clientes/" + clienteId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
