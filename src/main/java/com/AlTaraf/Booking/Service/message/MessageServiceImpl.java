package com.AlTaraf.Booking.Service.message;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Payload.request.MessageWhats;
import com.AlTaraf.Booking.Service.user.UserService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    UserService userService;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public String sendMessage(String phone, MessageWhats messageWhats) throws IOException {

       Optional<User> user = userService.findByPhone(phone);

        RequestBody body = new FormBody.Builder()
                .add("token", "v8551cd68z16gr2y")
                .add("to", user.get().getPhone())
                .add("body", messageWhats.getMessage())
                .build();

        Request request = new Request.Builder()
                .url("https://api.ultramsg.com/instance82647/messages/chat")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    @Override
    public String sendMessageAllUser(MessageWhats messageWhats) throws IOException {
        List<User> users = userService.getAllUser();
        StringBuilder result = new StringBuilder();

        for (User user : users) {
            System.out.println("All");
            System.out.println("user phone: " + user.getPhone());
            RequestBody body = new FormBody.Builder()
                    .add("token", "v8551cd68z16gr2y")
                    .add("to", user.getPhone())
                    .add("body", messageWhats.getMessage())
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.ultramsg.com/instance82647/messages/chat")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                result.append(response.body().string()).append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public String sendMessageAllTraders(MessageWhats messageWhats) throws IOException {

        List<User> users = userService.getAllByRolesName(ERole.ROLE_LESSOR);

        StringBuilder result = new StringBuilder();

        for (User user : users) {
            System.out.println("All");
            System.out.println("user phone: " + user.getPhone());
            RequestBody body = new FormBody.Builder()
                    .add("token", "v8551cd68z16gr2y")
                    .add("to", user.getPhone())
                    .add("body", messageWhats.getMessage())
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.ultramsg.com/instance82647/messages/chat")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                result.append(response.body().string()).append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public String sendMessageAllGuests(MessageWhats messageWhats) throws IOException {

        List<User> users = userService.getAllByRolesName(ERole.ROLE_GUEST);

        StringBuilder result = new StringBuilder();

        for (User user : users) {
            System.out.println("All");
            System.out.println("user phone: " + user.getPhone());
            RequestBody body = new FormBody.Builder()
                    .add("token", "v8551cd68z16gr2y")
                    .add("to", user.getPhone())
                    .add("body", messageWhats.getMessage())
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.ultramsg.com/instance82647/messages/chat")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                result.append(response.body().string()).append("\n");
            }
        }
        return result.toString();
    }
}
