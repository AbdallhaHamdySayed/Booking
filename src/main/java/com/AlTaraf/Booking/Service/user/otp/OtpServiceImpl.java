package com.AlTaraf.Booking.Service.user.otp;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {

    @Value("${sms.api.url}")
    private String smsApiUrl;

    @Value("${sms.api.token}")
    private String smsApiToken;

    @Value("${whats.api.url}")
    private String whatsApiUrl;

    @Value("${whats.api.token}")
    private String whatsApiToken;

    private final Map<String, Integer> otpStore = new ConcurrentHashMap<>();
    private final MessageSource messageSource;

    @Autowired
    UserRepository userRepository;

    private final OkHttpClient client = new OkHttpClient();

    public OtpServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ResponseEntity<?> validateOtp(String recipient, int otp, String acceptLanguageHeader) {
        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        Integer storedOtp = otpStore.get(recipient);
        if (storedOtp != null && storedOtp.equals(otp)) {

            Optional<User> user = userRepository.findByPhone(recipient);

            user.get().setIsActive(true);

            // userService.sav
            userRepository.save(user.get());

            ApiResponse response = new ApiResponse(200, messageSource.getMessage("otp_valid.message", null, locale));
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(400, messageSource.getMessage("otp_not_valid.message", null, locale));
            return ResponseEntity.status(400).body(response);
        }
    }

    @Override
    public Boolean checkValidateOtp(String recipient, int otp) {

        Integer storedOtp = otpStore.get(recipient);
        return storedOtp != null && storedOtp.equals(otp);
    }

    private int generateOtp() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }


    public ResponseEntity<?> sendOtp(String recipient, String acceptLanguageHeader) {


        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        int otp = generateOtp();
        otpStore.put(recipient, otp);
        String message = "" + otp;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + smsApiToken);

        Map<String, Object> payload = new HashMap<>();
        payload.put("recipient", recipient);
        payload.put("sender_id", "iSend"); // Ensure this is correct
        payload.put("type", "unicode");
        payload.put("message", message);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        try {
            String response = restTemplate.postForObject(smsApiUrl, request, String.class);

            String extractedMessage = extractMessageFromResponse(response);
            return ResponseEntity.ok(extractedMessage);
        } catch (Exception e) {

            ApiResponse response = new ApiResponse(400, messageSource.getMessage("otp_unsend.message", null, locale));
            return ResponseEntity.status(400).body(response);
        }
    }

    private String extractMessageFromResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode dataNode = rootNode.path("data");
            JsonNode messageNode = dataNode.path("message");
            return messageNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting message";
        }
    }

    public ResponseEntity<?> sendOtpViaWhatsApp(String recipient, String acceptLanguageHeader) {

        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        int otp = generateOtp();
        otpStore.put(recipient, otp);
        String message = "" + otp;

        RequestBody body = new FormBody.Builder()
                .add("token", whatsApiToken)
                .add("to", recipient)
                .add("body", message)
                .build();

        Request request = new Request.Builder()
                .url(whatsApiUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return ResponseEntity.ok(message);
            } else {
                ApiResponse responseEntity = new ApiResponse(400, messageSource.getMessage("otp_unsend.message", null, locale));
                return ResponseEntity.status(400).body(responseEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ApiResponse responseEntity = new ApiResponse(400, messageSource.getMessage("otp_unsend.message", null, locale));
            return ResponseEntity.status(400).body(responseEntity);
        }
    }

}