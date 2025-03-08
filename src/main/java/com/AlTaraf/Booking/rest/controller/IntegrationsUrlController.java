package com.AlTaraf.Booking.rest.controller;

import com.AlTaraf.Booking.rest.dto.PaymentSignInDto;
import com.AlTaraf.Booking.rest.dto.PaymentOpenSessionDto;
import com.AlTaraf.Booking.rest.dto.PaymentCompleteSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/integrations-url")
public class IntegrationsUrlController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("payment/sign-in")
    public ResponseEntity<?> signIn(@RequestBody PaymentSignInDto paymentSignInDto) {
        String apiUrl = "http://160.19.103.122:40120/YusorOnline/api/OnlinePaymentServices/Signin";

        // Send the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, paymentSignInDto, String.class);

        // Return the response received from the external API
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("payment/open-session")
    public ResponseEntity<?> openSession(@RequestBody PaymentOpenSessionDto paymentOpenSessionDto) {
        String apiUrl = "http://160.19.103.122:40120/YusorOnline/api/OnlinePaymentServices/OpenSession";

        // Send the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, paymentOpenSessionDto, String.class);

        // Return the response received from the external API
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("payment/complete-session")
    public ResponseEntity<?> completeSession(@RequestBody PaymentCompleteSessionDto paymentCompleteSessionDto) {
        String apiUrl = "http://160.19.103.122:40120/YusorOnline/api/OnlinePaymentServices/CompleteSession?culture=ar-LY";

        // Send the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, paymentCompleteSessionDto, String.class);

        // Return the response received from the external API
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
