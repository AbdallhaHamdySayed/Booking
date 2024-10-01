package com.AlTaraf.Booking.Service.Payment;

import com.AlTaraf.Booking.Dto.TransactionResponseDTO;
import com.AlTaraf.Booking.Dto.payment.PaymentDto;
import com.AlTaraf.Booking.Dto.payment.PaymentResponse;
import com.AlTaraf.Booking.Entity.Transactions.Transactions;
import com.AlTaraf.Booking.Entity.Transactions.TransactionsDetail;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.Wallet.Wallet;
import com.AlTaraf.Booking.Repository.Transactions.TransactionsDetailRepository;
import com.AlTaraf.Booking.Repository.Transactions.TransactionsRepository;
import com.AlTaraf.Booking.Repository.Wallet.WalletRepository;
import com.AlTaraf.Booking.Repository.payment.PayemntRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import com.AlTaraf.Booking.Entity.Payment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PayemntRepository payemntRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionsDetailRepository transactionsDetailRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${api.shop.store_id}")
    String apiShopStoreId;

    @Value("${api.shop.url}")
    String apiShopUrl;

    @Value("${api.shop.token}")
    String apiShopToken;

    @Value("${api.shop.transaction}")
    String apiShopTransaction;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Override
    public ResponseEntity<?> sendTransactionRequest(Long userId, String customRef) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiShopToken);
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/x-www-form-urlencoded");
            headers.set("X-RateLimit-Limit", "30");
            headers.set("X-RateLimit-Remaining", "29");

            Payment payment = payemntRepository.findByCustomRef(customRef);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("store_id", payment.getId());
            body.add("custom_ref", customRef);
            HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(body, headers);

            User user = userRepository.findByUserId(userId);
            payment.setUser(user);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TransactionResponseDTO> response = restTemplate.postForEntity(apiShopTransaction, httpRequest, TransactionResponseDTO.class);

            TransactionResponseDTO transactionResponseDTO = response.getBody();

            if (transactionResponseDTO != null && transactionResponseDTO.getData() != null) {
                Double amount = Double.parseDouble(transactionResponseDTO.getData().getAmount());

                Transactions transactions = transactionsRepository.findById(3L).orElse(null);

                TransactionsDetail transactionsDetail = new TransactionsDetail();
                transactionsDetail.setCustomRef(transactionResponseDTO.getData().getCustom_ref());
                transactionsDetail.setTransactions(transactions);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date parsedDate = dateFormat.parse(transactionResponseDTO.getData().getDate_time());

                transactionsDetail.setDate(parsedDate);
                transactionsDetail.setPhone(transactionResponseDTO.getData().getOwner_phone());
                transactionsDetail.setValue(amount);
                transactionsDetail.setUser(user);
                transactionsDetail.setGatewayArabicName(transactionResponseDTO.getData().getGateway());
                transactionsDetail.setGatewayEnglishName(transactionResponseDTO.getData().getGateway_name());

                transactionsDetailRepository.save(transactionsDetail);

                double currentWalletBalance = user.getWallet();
                currentWalletBalance += amount;
                user.setWallet(currentWalletBalance);
                userRepository.save(user);

                Payment paymentEntityToActive = payemntRepository.findByCustomRef(customRef);

                paymentEntityToActive.setIsActive(true);

                payemntRepository.save(paymentEntityToActive);

                Wallet wallet = new Wallet("شحن رصيد", "Charge Account", amount ,user, "" , "", paymentEntityToActive.getCustom_ref(), true);
                walletRepository.save(wallet);

                System.out.println("payment: " + paymentEntityToActive.getCustom_ref() + " Payment Active: " + paymentEntityToActive.getIsActive());
                messagingTemplate.convertAndSend("/topic/payment", response.getBody());

                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            } else {
                messagingTemplate.convertAndSend("/topic/payment", response.getBody());
                System.out.println("else Transaction Payment: ");

                String errorMessage = "Payment_Failed.message";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
        } catch (HttpClientErrorException.NotFound e) {
            // Handle 404 Not Found error
            System.out.println("Exception: " + e);
            String errorMessage = "Payment_Failed.message";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception e) {
            messagingTemplate.convertAndSend("/topic/payment", "Payment failed for user " + userId);
            System.out.println("Error Transaction Payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Or handle the error as needed
        }
    }

    @Override
    public ResponseEntity<?> initialPayment(Double amount, String phone, String email) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiShopToken);
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-RateLimit-Limit", "30");
        headers.set("X-RateLimit-Remaining", "29");

        Payment paymentEntity = new Payment();
        paymentEntity.setId(apiShopStoreId);
        paymentEntity.setAmount(amount);
        paymentEntity.setPhone(phone);
        paymentEntity.setEmail(email);
        paymentEntity.setCustom_ref(paymentEntity.getCustom_ref());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("id", paymentEntity.getId());
        body.add("amount", String.valueOf(amount));
        body.add("phone", phone);
        if (email != null) {
            body.add("email", email);
        }
        body.add("backend_url", "https://api.ihjezly.com/payment/back-end-url");
        body.add("custom_ref", paymentEntity.getCustom_ref());

        payemntRepository.save(paymentEntity);

        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(apiShopUrl, httpRequest, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            String customRef = rootNode.path("custom_ref").asText();
            String url = rootNode.path("url").asText();

            PaymentResponse paymentResponse = new PaymentResponse(customRef, url);

            User user = userRepository.findByPhoneForUser(phone);
            paymentEntity.setUser(user);

            System.out.println("Initial Payment by phone User Id: " + user.getId());
            payemntRepository.save(paymentEntity);

            System.out.println("Initial Payment User Id: " + paymentEntity.getUser().getId());

//            sendTransactionRequest(user.getId(), customRef);

            return new ResponseEntity<>(paymentResponse, response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to parse the response", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> backEndUrl(PaymentDto paymentDto) {

        System.out.println("-------------------------");
        System.out.println("Test Backend API");
        System.out.println("-------------------------");

        Payment paymentEntity = payemntRepository.findByCustomRef(paymentDto.getCustom_ref());

        System.out.println("-------------------------");
        System.out.println("paymentDto.getCustom_ref():  " + paymentDto.getCustom_ref());
        System.out.println("-------------------------");

        paymentEntity.setPayment_method(paymentDto.getPayment_method());

        System.out.println("-------------------------");
        System.out.println("payment get payment method: " + paymentEntity.getPayment_method());
        System.out.println("-------------------------");

        System.out.println("-------------------------");
        System.out.println("paymentDto.getCustomer_phone():  " + paymentDto.getCustomer_phone());
        System.out.println("-------------------------");

        String phoneNumberWithoutPlus = paymentDto.getCustomer_phone().replace("+", "");


        User user = userRepository.findByPhoneForUser(phoneNumberWithoutPlus);
        System.out.println("-------------------------");
        System.out.println("user Id: " + user.getId());

        paymentEntity.setUser(user);
        payemntRepository.save(paymentEntity);

        sendTransactionRequest(user.getId(), paymentDto.getCustom_ref());

        return null;
    }

}
