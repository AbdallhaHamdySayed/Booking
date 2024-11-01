package com.AlTaraf.Booking.Dto.Notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationRequest {
    private String title;
    private String body;
    private Long userId;
    private Long unitId;
    private Long reservationId;
    private Long adsId;
}