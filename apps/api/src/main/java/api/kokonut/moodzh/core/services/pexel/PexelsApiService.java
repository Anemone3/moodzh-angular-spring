package api.kokonut.moodzh.core.services.pexel;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import api.kokonut.moodzh.data.api.PexelsApiResponse;
import api.kokonut.moodzh.data.api.PexelsPhotoCuratedResponse;
import io.netty.channel.ChannelOption;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class PexelsApiService {
        private final WebClient webClient;
        private final String API_KEY;

        public PexelsApiService(WebClient.Builder webClientBuilder, @Value("${apis.pexels_key}") String pexelsApiKey) {
                HttpClient httpClient = HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // Timeout de conexión (5s)
                                .responseTimeout(Duration.ofSeconds(15)); // Timeout de respuesta total (15s)

                this.webClient = webClientBuilder
                                .baseUrl("https://api.pexels.com/v1")
                                .clientConnector(new ReactorClientHttpConnector(httpClient))
                                .build();
                this.API_KEY = pexelsApiKey;
        }

        public PexelsApiResponse searchPhotosSync(String query, int page) {
                return webClient.get()
                                .uri(uriBuiler -> uriBuiler.queryParam("query", query).queryParam("page", page)
                                                .build("/search"))
                                .header("Authorization", API_KEY)
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                                clientResponse -> Mono.error(
                                                                new RuntimeException("Error en la API de Pexels: "
                                                                                + clientResponse.statusCode())))
                                .bodyToMono(PexelsApiResponse.class).block();
        }

        public Mono<PexelsApiResponse> searchPhotosAsync(String query, int page) {
                return webClient.get()
                                .uri(uriBuiler -> uriBuiler.queryParam("query", query).queryParam("page", page)
                                                .build("/search"))
                                .header("Authorization", API_KEY)
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                                clientResponse -> Mono.error(
                                                                new RuntimeException("Error en la API de Pexels: "
                                                                                + clientResponse.statusCode())))
                                .bodyToMono(PexelsApiResponse.class);
        }

        public Mono<PexelsPhotoCuratedResponse> curatedPhotos(int page) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.queryParam("page", page).queryParam("per_page", 15)
                                                .build("/curated"))
                                .header("Authorization", "2Wc90xYoRgJwNtYzRMLtXF7lLXZ22GkTn7gHl2k8pnSAlvVKyGK5FUk7")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                                clientResponse -> Mono.error(
                                                                new RuntimeException("Error en la API de Pexels: "
                                                                                + clientResponse.statusCode())))
                                .bodyToMono(PexelsPhotoCuratedResponse.class);
        }

}