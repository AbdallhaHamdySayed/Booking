package com.AlTaraf.Booking.Controller.whatsApp;

import com.AlTaraf.Booking.Entity.File.FileForPdf;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Payload.request.MessageWhats;
import com.AlTaraf.Booking.Repository.File.FileForPdfRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.message.MessageService;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/WhatsApp")
public class WhatsAppController {

    @Autowired
    FileForPdfRepository fileForPdfRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    MessageService messageService;

    @PostMapping("/sendDocument/{userId}")
    public String sendDocument(@PathVariable Long userId, @RequestParam Long reservationId) {
        try {
            FileForPdf pdf = fileForPdfRepository.findByUserIdAndSentFlagIsNull(userId);

            if (pdf == null ) {
                return "لا يوجد ملف جاهز للارسال" ;
            }

            System.out.println("pdf : " + pdf.getId());

            User user = userRepository.findByUserId(userId);

            System.out.println("user.getPhone(): " + user.getPhone());

            String message = "Send_Invoice.message";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            System.out.println("ImagePath: " + pdf.getFileDownloadUri());

            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("token", "v8551cd68z16gr2y");
            map.add("to", "218"+user.getPhone());
            map.add("filename", "Details_Reservation.message");
            map.add("document", pdf.getFileDownloadUri());
            map.add("caption", "Send_Invoice.message");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            pdf.setSentFlag(Boolean.TRUE);
            fileForPdfRepository.save(pdf);

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.ultramsg.com/instance82647/messages/document";
            String response = restTemplate.postForObject(url, request, String.class);

            Reservations reservations = reservationRepository.findById(reservationId).orElse(null);

            // OkHttpClient setup for sending location
            OkHttpClient client = new OkHttpClient();
            okhttp3.RequestBody body = new FormBody.Builder()
                    .add("token", "v8551cd68z16gr2y")
//                    .add("to", "0201110495598")
                    .add("to", "218" + reservations.getUser().getPhone())
                    .add("address", reservations.getUnit().getCity().getArabicCityName() + " - " +  reservations.getUnit().getRegion().getRegionArabicName())
                    .add("lat", String.valueOf(reservations.getUnit().getLatForMapping()))
                    .add("lng", String.valueOf(reservations.getUnit().getLongForMapping()))

                    .build();

            Request locationRequest = new Request.Builder()
                    .url("https://api.ultramsg.com/instance82647/messages/location")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            Response locationResponse = client.newCall(locationRequest).execute();
            System.out.println(locationResponse.body().string());


            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending document: " + e.getMessage();
        }
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam(value = "phone", required = false) String phone,
                              @RequestParam(value = "allUsers", required = false) Boolean allUsers,
                              @RequestParam(value = "allGuest", required = false) Boolean allGuest,
                              @RequestParam(value = "allTrades", required = false) Boolean allTrades,
                              @RequestBody MessageWhats messageWhats) throws IOException {
        if (phone != null && !phone.isEmpty()) {
            return messageService.sendMessage(phone, messageWhats);
        } else {
            if (allUsers != null && allUsers) {
                System.out.println("allUsers != null && allUsers");
                return messageService.sendMessageAllUser(messageWhats);
            }
            if (allTrades != null && allTrades) {
                return messageService.sendMessageAllTraders(messageWhats);
            }
            if (allGuest != null && allGuest) {
                return messageService.sendMessageAllGuests(messageWhats);
            }
        }
        return "تم الارسال بنجاح";
    }


}