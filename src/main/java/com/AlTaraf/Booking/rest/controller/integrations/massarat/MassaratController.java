package com.AlTaraf.Booking.rest.controller.integrations.massarat;

import com.AlTaraf.Booking.rest.dto.massarat.CompleteSession;
import com.AlTaraf.Booking.rest.dto.massarat.OpenSession;
import com.AlTaraf.Booking.rest.dto.massarat.SignInPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/massarat")
public class MassaratController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("payment/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInPayment signInPayment) {
        String apiUrl = "http://160.19.103.122:40120/YusorOnline/api/OnlinePaymentServices/Signin";

        // Send the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, signInPayment, String.class);

        // Return the response received from the external API
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("payment/open-session")
    public ResponseEntity<?> openSession(@RequestBody OpenSession openSession) {
        System.out.println("YOO");

        String apiUrl = "http://160.19.103.122:40120/YusorOnline/api/OnlinePaymentServices/OpenSession";
        String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxYzRjODZlYS0zNGRhLTQ2YTktODMxYi0wM2JhYTVlNDA4ZTUiLCJQcm92aWRlcklkIjoiNDgyNiIsIk1lcmNoYW50SWQiOiIxMDA1ODkiLCJVVElEIjoiTWVyY2hhbnQiLCJQT1NJZCI6Ii0xIiwiUElEIjoiMzAwIiwiQXV0aFN0YXRlT25saW5lUGF5bWVudCI6IjIiLCJleHAiOjE3NDM0OTcyNjgsImlzcyI6Im1pdHQtZGV2IiwiYXVkIjoibWl0dC1kZXYifQ.swqJ6LzN2NwyPVUKrYY-A-rcfOUe8x3jyJBEKjPaJzs"; // Replace with the actual token

        System.out.println("amount: " + openSession.getAmount());

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(bearerToken);

        // Create request entity with headers and body
        HttpEntity<OpenSession> requestEntity = new HttpEntity<>(openSession, headers);

        try {
            // Send the POST request with the token
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // Return the response received from the external API
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpStatusCodeException e) {
            // Print HTTP error details
            System.err.println("HTTP Status: " + e.getStatusCode());
            System.err.println("Response Body: " + e.getResponseBodyAsString());
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            // Print generic RestTemplate errors
            System.err.println("RestClientException occurred while calling: " + apiUrl);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calling external API");
        }
    }


    @PostMapping("payment/complete-session")
    public ResponseEntity<?> completeSession(@RequestBody CompleteSession completeSession) {
        String apiUrl = "http://160.19.103.122:40120/YusorOnline/api/OnlinePaymentServices/CompleteSession?culture=ar-LY";

        // Send the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, completeSession, String.class);

        // Return the response received from the external API
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
