package com.AlTaraf.Booking.Dto.Notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationRequestForAll {
    private String title;
    private String body;
}