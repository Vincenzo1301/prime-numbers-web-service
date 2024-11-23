package se.hh.primenumberswebservice.client;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import se.hh.primenumberswebservice.data.PrimeNumberDto;

@Component
public class PrimeNumberClient {

  private final RestClient restClient;

  public PrimeNumberClient() {
    this.restClient = RestClient.create();
  }

  /**
   * Sends a GET request to check if the number is prime.
   *
   * @param server the target server URL
   * @param number the number to check
   * @param originServer the origin server URL (to prevent loops)
   * @return the response containing prime status or an empty response if not found
   */
  public PrimeNumberDto checkPrime(String server, int number, String originServer) {
    String getUri = server + "/api/v1/prime?number=" + number;
    try {
      return restClient
          .get()
          .uri(getUri)
          .header("X-Origin-Server", originServer)
          .retrieve()
          .body(PrimeNumberDto.class);
    } catch (Exception e) {
      System.out.println("Failed to connect to " + server + ": " + e.getMessage());
      return null;
    }
  }

  /**
   * Sends a POST request to store the prime number.
   *
   * @param server the target server URL
   * @param body the body containing the number and its prime status
   * @param originServer the origin server URL (to prevent loops)
   * @return the HTTP response from the server
   */
  public ResponseEntity<Void> storePrime(String server, PrimeNumberDto body, String originServer) {
    String postUri = server + "/api/v1/prime";
    try {
      return restClient
          .post()
          .uri(postUri)
          .header("X-Origin-Server", originServer)
          .contentType(APPLICATION_JSON)
          .body(body)
          .retrieve()
          .toBodilessEntity();
    } catch (Exception e) {
      System.out.println("Failed to update " + server + ": " + e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }
}
