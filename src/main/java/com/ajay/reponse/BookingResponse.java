package com.ajay.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingResponse {

    private Long id;

    private LocalDate checkedInDate;

    private LocalDate checkedOutDate;

    private String guestFullName;

    private String guestEmail;

    private int NumOfAdults;

    private int NumOfChildren;

    private int totalNumberOfGuest;

    private String bookingConfirmationCode;

    private RoomResponse room;

    public BookingResponse( Long id,LocalDate checkedInDate, LocalDate checkedOutDate, String bookingConfirmationCode) {
        this.checkedInDate = checkedInDate;
        this.id = id;
        this.checkedOutDate = checkedOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }



}