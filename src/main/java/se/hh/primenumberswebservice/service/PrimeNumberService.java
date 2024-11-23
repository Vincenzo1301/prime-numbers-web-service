package se.hh.primenumberswebservice.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import se.hh.primenumberswebservice.data.PrimeNumberDto;

@Service
public class PrimeNumberService {

  private final Map<Integer, Boolean> primeNumbers = new HashMap<>();

  public PrimeNumberDto isPrime(int number) {
    if (!primeNumbers.containsKey(number)) {
      return new PrimeNumberDto(number, "");
    }

    return new PrimeNumberDto(number, primeNumbers.get(number) ? "prime" : "not prime");
  }

  public PrimeNumberDto addPrimeNumber(PrimeNumberDto body) {
    Integer number = body.number();
    Boolean isPrime = Boolean.parseBoolean(body.isPrime());
    primeNumbers.put(number, isPrime);

    return new PrimeNumberDto(number, isPrime.toString());
  }
}
