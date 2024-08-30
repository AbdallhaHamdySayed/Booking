package com.AlTaraf.Booking.Dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private String result;
    private String amount;
    private String store_id;
    private String our_ref;
    private String payment_method;
    private String customer_phone;
    private String custom_ref;
}
