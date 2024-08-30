package com.AlTaraf.Booking.Controller;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequestForAll;
import com.AlTaraf.Booking.Dto.Notifications.Response.PushNotificationResponse;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Mapper.Notification.NotificationForAllMapper;
import com.AlTaraf.Booking.Mapper.Notification.NotificationMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.NotificationRepository;
import com.AlTaraf.Booking.Repository.role.RoleRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Notification")
public class NotificationController {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationForAllMapper notificationForAllMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/Send")
    public ResponseEntity<?> sendPushNotification(@RequestBody PushNotificationRequest request) {
        try {
            User user = userRepository.findByUserId(request.getUserId());
            for (Role role : user.getRoles()) {
                Notifications notification = notificationMapper.dtoToEntity(request);
                notification.setUser(user);
                notification.setRole(role);
                notificationRepository.save(notification);

                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }

            notificationService.sendPushMessage(request.getTitle(), request.getBody(), request.getUserId());

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-For-Guest-One-User")
    public ResponseEntity<?> sendPushNotificationforGuest(@RequestBody PushNotificationRequest request) {

        return ResponseEntity.ok(null);
    }

    @PostMapping("/Send-For-Guest-One-User-test")
    public ResponseEntity<?> sendPushNotificationforGuestTest(@RequestBody PushNotificationRequest request) {
        try {
            Notifications notification = notificationMapper.dtoToEntity(request);
            Role role = roleRepository.findById(1L).orElse(null);

            notification.setRole(role);
            notificationRepository.save(notification);

            User user = userRepository.findByRolesNameAndUserId(ERole.ROLE_GUEST,request.getUserId());
            if (user != null ) {
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to user with role Guest!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-For-Lessor-One-User")
    public ResponseEntity<?> sendPushNotificationforLessor(@RequestBody PushNotificationRequest request) {

        return ResponseEntity.ok(null);

    }

    @PostMapping("/Send-For-Lessor-One-User-Test")
    public ResponseEntity<?> sendPushNotificationforLessorTest(@RequestBody PushNotificationRequest request) {
        try {
            Notifications notification = notificationMapper.dtoToEntity(request);
            Role role = roleRepository.findById(2L).orElse(null);

            notification.setRole(role);
            notificationRepository.save(notification);

            User user = userRepository.findByRolesNameAndUserId(ERole.ROLE_LESSOR,request.getUserId());
            if (user != null ) {
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to user with role Lessor!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-All")
    public ResponseEntity<?> sendPushNotificationToAll(@RequestBody PushNotificationRequestForAll request) {
        try {
            List<User> users = userRepository.findAll();

            for (User user : users) {

                // Set the role for the notification based on the user's roles
                for (Role role : user.getRoles()) {
                    Notifications notification = notificationForAllMapper.dtoToEntity(request);
                    notification.setUser(user);
                    notification.setRole(role);
                    notificationRepository.save(notification);

                    notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Push notifications sent successfully to all users!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Failed to send push notification."));
        }
    }
    @PostMapping("/Send-For-All-Guest")
    public ResponseEntity<?> sendPushNotificationforAllGuest(@RequestBody PushNotificationRequestForAll request) {
        try {

            Role role = roleRepository.findById(1L).orElse(null);

            List<User> users = userRepository.findByRoleId(1L);
            for (User user : users) {
                Notifications notification = notificationForAllMapper.dtoToEntity(request);

                notification.setRole(role); // Set role ID to 2
                notification.setUser(user);
                notificationRepository.save(notification);
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to all users with role Lessors!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-For-All-Lessor")
    public ResponseEntity<?> sendPushNotificationforLessor(@RequestBody PushNotificationRequestForAll request) {
        try {

            Role role = roleRepository.findById(2L).orElse(null);
            List<User> users = userRepository.findByRoleId(2L);

            for (User user : users) {
                Notifications notification = notificationForAllMapper.dtoToEntity(request);

                notification.setRole(role); // Set role ID to 2
                notification.setUser(user);
                notificationRepository.save(notification);
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to all users with role Lessors!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }


    @GetMapping("/user")
    public ResponseEntity<?> getNotificationsByUserIdAndRoleId(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long roleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PushNotificationResponse> response;

            Page<Notifications> notifications = notificationService.findAllByUserIdAndRoleId(userId, roleId, pageable);
        for (Notifications notification : notifications) {
            // Perform operations on each notification, if needed
            // For example, you can set the notification as seen
            notification.setSeen(true);
            // Save the updated notification back to the database
            notificationRepository.save(notification);
        }

            response = notifications.map(NotificationMapper.INSTANCE::entityToDto);

//        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/setSeen")
    public ResponseEntity<?> setNotificationsSeen(@RequestParam Long userId, @RequestParam Long roleId) {
//        try {
//            notificationRepository.setNotificationsSeenByUserIdAndRoleId(userId, roleId);
//            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Notifications marked as seen successfully!"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Failed to mark notifications as seen."));
//        }
        return ResponseEntity.ok(null);

    }

    @GetMapping("/count-unseen")
    public Long getCountOfUnseenNotifications(@RequestParam Long userId, @RequestParam Long roleId) {
        Long count = notificationRepository.countByUserIdAndRoleIdAndSeenIsNull(userId, roleId);
        messagingTemplate.convertAndSend("/topic/notifications", count.toString());
        return count;
    }

}