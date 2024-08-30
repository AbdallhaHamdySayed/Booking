package com.AlTaraf.Booking.Service.notification;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Mapper.Notification.NotificationMapper;
import com.AlTaraf.Booking.Repository.NotificationRepository;
import com.AlTaraf.Booking.Repository.role.RoleRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    RoleRepository roleRepository;

//    private final String SERVER_KEY = "AAAA22Rv9Jk:APA91bEcJoOKJXEr2-fdzu3aE9RbLFseLRhF4tWj1tLNpg-uqu5R6o1pjHfjMyMo888W9bFsGniaW59wpf1-X8MeUarL4tzwZF4nl-qi8xh2zgPn3RijNogAbFo00hoabzarS5Qwfsxe";
    private final String SERVER_KEY = "AAAAhedicPc:APA91bGAf6fu3fMrWlMZtW1vmILgLmbHz6Ot5xmmiUEdjq13GuhRHIkHSALL6g-hLOIhnqZ-Odj6_E6v9dduMNJqovCDx10v8Z2K9EcVuxNblYsGY2TkP_TsvS5sXrCvFMWLO7yTvYWZ";
    private final String FCM_URL = "https://fcm.googleapis.com/fcm/send";

    public void sendPushMessage(String title, String body, Long userId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        System.out.println("userId: " + userId);
        User user = userRepository.findByUserId(userId);


//        assert user != null;
        String jsonPayload = createJsonPayload(title, body, user.getDeviceToken());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(FCM_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "key=" + SERVER_KEY)
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println("Response status code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }

    private String createJsonPayload(String title, String body, String deviceToken) {
        return "{"
                + "\"to\":\"" + deviceToken + "\","
                + "\"priority\":\"high\","
                + "\"data\":{"
                + "\"click_action\":\"FLUTTER_NOTIFICATION_CLICK\","
                + "\"id\":\"1\","
                + "\"status\":\"done\""
                + "},"
                + "\"notification\":{"
                + "\"title\":\"" + title + "\","
                + "\"body\":\"" + body + "\""
                + "}"
                + "}";
    }

    public Page<Notifications> getNotificationsByUserId(Long userId, Pageable pageable) {
        return notificationRepository.findByUserIdAndRoleIdIsNull(userId, pageable);
    }

    public Page<Notifications> findAllByUserIdAndRoleId(Long userId, Long roleId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndRoleId(userId, roleId, pageable);
    }

    public Page<Notifications> findAllByRoleIdAndUserIdIsNull( Long roleId, Pageable pageable) {
        return notificationRepository.findAllByRoleId( roleId, pageable);
    }

    public Page<Notifications> findAllByRoleIdIsNullAndUserIdIsNull(Pageable pageable) {
        return notificationRepository.findAllByRoleIdIsNullAndUserIdIsNull(pageable);
    }

    public void processNotification(PushNotificationRequest request) throws IOException, InterruptedException {
        Notifications notification = notificationMapper.dtoToEntity(request);
        Role role = roleRepository.findById(2L).orElse(null);

        notification.setRole(role);
        notificationRepository.save(notification);

//        User user = userRepository.findByRolesNameAndUserId(ERole.ROLE_GUEST, request.getUserId());
        if (request.getUserId() != null) {
            sendPushMessage(request.getTitle(), request.getBody(), request.getUserId());
        }
    }

    public void processNotificationForGuest(PushNotificationRequest request) throws IOException, InterruptedException {
        Notifications notification = notificationMapper.dtoToEntity(request);
        Role role = roleRepository.findById(1L).orElse(null);

        notification.setRole(role);
        notificationRepository.save(notification);

//        User user = userRepository.findByRolesNameAndUserId(ERole.ROLE_GUEST, request.getUserId());
        if (request.getUserId() != null) {
            sendPushMessage(request.getTitle(), request.getBody(), request.getUserId());
        }
    }
}