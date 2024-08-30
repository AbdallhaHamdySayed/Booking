package com.AlTaraf.Booking.Dto.Notifications.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationForAllResponse {
    private Long id;
    private String title;
    private String body;
    private String logoUrl;
}