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
@Table(name = "FILE_FOR_PDF")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FileForPdf {

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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @Column(name = "SENT_FLAG")
    private Boolean sentFlag;


    public FileForPdf(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

}
