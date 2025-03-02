package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.Notifications;
import com.AlTaraf.Booking.database.entity.Reservations;
import com.AlTaraf.Booking.database.entity.Role;
import com.AlTaraf.Booking.database.entity.User;
import com.AlTaraf.Booking.database.repository.NotificationRepository;
import com.AlTaraf.Booking.database.repository.ReservationRepository;
import com.AlTaraf.Booking.database.repository.RoleRepository;
import com.AlTaraf.Booking.database.repository.UserRepository;
import com.AlTaraf.Booking.rest.dto.PushNotificationRequest;
import com.AlTaraf.Booking.rest.mapper.NotificationMapper;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collections;

@Service
public class NotificationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    MessageSource messageSource;

    private final String FCM_URL = "https://fcm.googleapis.com/v1/projects/safari-d2dda/messages:send";


    public void sendPushMessage(String title, String body, Long userId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        System.out.println("userId: " + userId);
        User user = userRepository.findByUserId(userId);

        String accessToken = getAccessToken();

        String jsonPayload = createJsonPayload(title, body, user.getDeviceToken());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(FCM_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println("Response status code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }

    private String createJsonPayload(String title, String body, String deviceToken) {
        return "{"
                + "\"message\":{"
                + "\"token\":\"" + deviceToken + "\","
                + "\"notification\":{"
                + "\"title\":\"" + title + "\","
                + "\"body\":\"" + body + "\""
                + "}"
                + "}"
                + "}";
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                getClass().getClassLoader().getResourceAsStream("service-account.json")
        ).createScoped(Collections.singleton("https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();
        AccessToken token = googleCredentials.getAccessToken();
        return token.getTokenValue();
    }


    public Page<Notifications> findAllByUserIdAndRoleId(Long userId, Long roleId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndRoleId(userId, roleId, pageable);
    }

    public void processNotification(PushNotificationRequest request) throws IOException, InterruptedException {
        Notifications notification = notificationMapper.dtoToEntity(request);
        Role role = roleRepository.findById(2L).orElse(null);

        notification.setRole(role);
        notificationRepository.save(notification);

        if (request.getUserId() != null) {
            sendPushMessage(request.getTitle(), request.getBody(), request.getUserId());
        }
    }

    public void processNotificationForGuest(PushNotificationRequest request) throws IOException, InterruptedException {
        Notifications notification = notificationMapper.dtoToEntity(request);
        Role role = roleRepository.findById(1L).orElse(null);

        notification.setRole(role);
        notificationRepository.save(notification);

        if (request.getUserId() != null) {
            sendPushMessage(request.getTitle(), request.getBody(), request.getUserId());
        }
    }

    public void pushNotificationsReservationCanceled(Long reservationId) throws IOException, InterruptedException {
        Reservations reservations = reservationRepository.findById(reservationId).orElse(null);

        PushNotificationRequest notificationReservationCanceled = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("notification_body_canceled_reservation.message", null,
                        LocaleContextHolder.getLocale()) + " " + reservations.getUnit().getNameUnit(), reservations.getUser().getId(),
                null, reservationId, null);
        processNotificationForGuest(notificationReservationCanceled);
    }

    public void pushNotificationsRecoveryWallet(Long reservationId) throws IOException, InterruptedException {
        Reservations reservations = reservationRepository.findById(reservationId).orElse(null);

        PushNotificationRequest notificationRecoveryWallet = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("wallet_recovery.message", null,
                        LocaleContextHolder.getLocale()), reservations.getUser().getId(),
                null, reservationId, null);
        processNotificationForGuest(notificationRecoveryWallet);
    }
}