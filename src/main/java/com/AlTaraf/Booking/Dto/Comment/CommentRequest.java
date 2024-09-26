package com.AlTaraf.Booking.Dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String content;
    private String userName;
    private String fileDownloadUri;
    private String phoneNumber;
    private Long unitId;
}
