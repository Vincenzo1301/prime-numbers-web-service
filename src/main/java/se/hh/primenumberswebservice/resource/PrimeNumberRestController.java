package se.hh.primenumberswebservice.resource;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.hh.primenumberswebservice.data.PrimeNumberDto;
import se.hh.primenumberswebservice.service.PrimeNumberService;

@RestController
@RequestMapping("/api/v1/prime")
public class PrimeNumberRestController {

  private final PrimeNumberService primeNumberService;

  public PrimeNumberRestController(PrimeNumberService primeNumberService) {
    this.primeNumberService = primeNumberService;
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<PrimeNumberDto> addPrimeNumber(
      @RequestBody PrimeNumberDto body,
      @RequestHeader(value = "X-Origin-Server", required = false) String originServer) {
    PrimeNumberDto response = primeNumberService.addPrimeNumber(body, originServer);
    return new ResponseEntity<>(response, CREATED);
  }

  @GetMapping
  public ResponseEntity<PrimeNumberDto> checkPrimeNumber(
      @RequestParam int number,
      @RequestHeader(value = "X-Origin-Server", required = false) String originServer) {
    System.out.println("[INFO]: Received request with origin server: " + originServer);

    PrimeNumberDto response = primeNumberService.isPrime(number, originServer);
    return ResponseEntity.ok(response);
  }
}
