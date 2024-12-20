package com.AlTaraf.Booking.Dto.Notifications.Response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationResponse {
    private Long id;
    private String title;
    private String body;
    private Long userId;
    private Boolean seen;
    private String logoUrl;
    private String lang;
    private Duration elapsedTime;
    private Long unitId;
    private Long reservationId;
    private Long adsId;
}