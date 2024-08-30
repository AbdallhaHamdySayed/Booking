package com.AlTaraf.Booking.Entity.File;

import com.AlTaraf.Booking.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FILE_FOR_PROFILE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FileForProfile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    private String fileDownloadUri;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    public FileForProfile(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

}
