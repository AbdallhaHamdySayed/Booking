package com.AlTaraf.Booking.Dto.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileForAdsDTO {
    private String name;

    private String type;

    private String fileDownloadUri;
}
