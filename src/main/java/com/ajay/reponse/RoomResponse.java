package com.ajay.reponse;

import com.ajay.model.BookedRoom;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

/**
 *
 * @author Ajay wankhade
 */
@Data
@NoArgsConstructor
public class RoomResponse {

    private Long id;

    private  String roomType;

    private BigDecimal roomPrice;

    private boolean isBooked = false;

    private String photo;

    private List<BookingResponse> bookings;

    public RoomResponse(Long id,
                        String roomType,
                        BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponse( Long id,
                         String roomType,
                         BigDecimal roomPrice,
                         boolean isBooked,
                         byte[] photoBytes,
                         List<BookingResponse> bookings) {
        this.isBooked = isBooked;
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.bookings = bookings;
    }

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean booked, byte[] photoBytes) {
        this.isBooked = isBooked;
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;

    }
}
