package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileForUnitDTO {
    private String name;
    private String type;
    private String fileImageUrl;
    private String fileVideoUrl;
}
