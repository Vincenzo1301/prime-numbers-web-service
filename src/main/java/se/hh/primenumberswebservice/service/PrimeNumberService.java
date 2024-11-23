package se.hh.primenumberswebservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.hh.primenumberswebservice.client.PrimeNumberClient;
import se.hh.primenumberswebservice.data.PrimeNumberDto;

@Service
public class PrimeNumberService {

  @Value("${server.port}")
  private int port;

  @Value("${cluster.servers}")
  private List<String> cluster;

  private final Map<Integer, Boolean> primeNumbers;
  private final PrimeNumberClient primeNumberClient;

  public PrimeNumberService(PrimeNumberClient primeNumberClient) {
    this.primeNumbers = new HashMap<>();
    this.primeNumberClient = primeNumberClient;
  }

  public PrimeNumberDto isPrime(int number, String originServer) {
    if (!primeNumbers.containsKey(number)) {

      // Avoid querying other servers if this request originated from a cluster peer
      if (originServer != null) {
        return new PrimeNumberDto(number, "");
      }

      // Query other servers in the cluster
      for (String server : cluster) {
        PrimeNumberDto response =
            primeNumberClient.checkPrime(server, number, "http://localhost:" + port);
        if (response != null && !response.isPrime().isEmpty()) {
          // Update local cache
          primeNumbers.put(number, response.isPrime().equals("prime"));
          return response;
        }
      }

      return new PrimeNumberDto(number, "");
    }

    return new PrimeNumberDto(number, primeNumbers.get(number).toString());
  }

  public PrimeNumberDto addPrimeNumber(PrimeNumberDto body, String originServer) {
    Integer number = body.number();
    Boolean isPrime = Boolean.parseBoolean(body.isPrime());
    primeNumbers.put(number, isPrime);
    System.out.println("[INFO]: Received number " + number + " with prime status " + isPrime);

    // Avoid propagating to other servers if this server is the origin
    if (originServer != null) {
      return new PrimeNumberDto(number, isPrime.toString());
    }

    // Propagate to other servers in the cluster
    for (String server : cluster) {
      ResponseEntity<Void> response =
          primeNumberClient.storePrime(server, body, "http://localhost:" + port);
      if (response.getStatusCode().is2xxSuccessful()) {
        System.out.println("[INFO]: Updated cache of " + server + " with number " + number);
      } else {
        System.out.println("[ERROR]: Failed to update cache with number " + number);
      }
    }

    return new PrimeNumberDto(number, isPrime.toString());
  }
}
