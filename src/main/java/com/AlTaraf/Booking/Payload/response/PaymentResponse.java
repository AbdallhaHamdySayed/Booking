package com.AlTaraf.Booking.Payload.response;

import com.AlTaraf.Booking.Payload.request.PaymentMethod;

public class PaymentResponse {
    private String result;
    private String amount;
    private String store_id;
    private String our_ref = "66666";
    private PaymentMethod payment_method;
    private String customer_phone;
    private String custom_ref;
}
