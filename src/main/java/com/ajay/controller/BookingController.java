package com.ajay.controller;

import com.ajay.exception.InvalidBookingRequestException;
import com.ajay.exception.ResourceNotFoundException;
import com.ajay.model.BookedRoom;
import com.ajay.model.Room;
import com.ajay.reponse.BookingResponse;
import com.ajay.reponse.RoomResponse;
import com.ajay.service.IBookingService;
import com.ajay.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ajay wankhade
 */
@CrossOrigin("http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private final IBookingService bookingService;
    @Autowired
    private IRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?>getBookingByConfirmationCode(
            @PathVariable String confirmationCode){
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/room/{roomType}/booking")
    public ResponseEntity<?> saveBooking(
           @PathVariable Long roomId,
           @RequestBody BookedRoom bookingRequest){
        try {
            String confirmationCode = bookingService.saveBooking(roomId,bookingRequest);
            return  ResponseEntity.ok("Room booked successfully,Your booking confirmation code is "+ confirmationCode);
        }catch(InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/booking/{bookingId}/delete")
    public void cancelBooking(
            @PathVariable Long bookingId
    ){
      bookingService.cancelBooking(bookingId);
    }


    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(),theRoom.getRoomType(),theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckedInDate(),
                booking.getCheckedOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumberOfGuest(),
                booking.getBookingConfirmationCode(), room);
    }
}
