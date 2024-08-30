package com.AlTaraf.Booking.Service.message;

import com.AlTaraf.Booking.Payload.request.MessageWhats;

import java.io.IOException;

public interface MessageService {
    String sendMessage( String phone, MessageWhats messageWhats) throws IOException;
    String sendMessageAllUser(MessageWhats messageWhats) throws IOException;
    String sendMessageAllTraders(MessageWhats messageWhats) throws IOException;
    String sendMessageAllGuests(MessageWhats messageWhats) throws IOException;
}
