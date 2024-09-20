package com.ajaysw.controller;

import com.ajaysw.exception.InvalidBookingRequestException;
import com.ajaysw.exception.ResourceNotFoundException;
import com.ajaysw.model.BookedRoom;
import com.ajaysw.model.Room;
import com.ajaysw.response.BookingResponse;
import com.ajaysw.response.RoomResponse;
import com.ajaysw.service.IBookingService;
import com.ajaysw.service.IRoomService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ajay Wankhade
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
@Tag(name = "Room Bookign Management", description = "APIs for managing hotel rooms bookings")

public class BookingController {
    private final IBookingService bookingService;
    private final IRoomService roomService;


    @Operation(summary = "Get all bookings", description = "Retrieves a list of all bookings. Only accessible by admins.")
    @ApiResponse(responseCode = "200", description = "Bookings found",
            content = @Content(schema = @Schema(implementation = BookingResponse.class)))
    @GetMapping("/all-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }


    @Operation(summary = "Save a booking", description = "Creates a new booking for a specific room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid booking request")
    })
    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking( @Parameter(description = "ID of the room to book") @PathVariable Long roomId,
                                          @Parameter(description = "Booking details") @RequestBody BookedRoom bookingRequest){
        try{
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok(
                    "Room booked successfully, Your booking confirmation code is :"+confirmationCode);

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Get booking by confirmation code", description = "Retrieves a booking using its confirmation code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(
            @Parameter(description = "Confirmation code of the booking") @PathVariable String confirmationCode){
        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    @Operation(summary = "Get bookings by user email", description = "Retrieves all bookings for a specific user")
    @ApiResponse(responseCode = "200", description = "Bookings found",
            content = @Content(schema = @Schema(implementation = BookingResponse.class)))

    @GetMapping("/user/{email}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserEmail(
             @PathVariable String email) {
        List<BookedRoom> bookings = bookingService.getBookingsByUserEmail(email);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }


    @Operation(summary = "Cancel a booking", description = "Cancels a booking by its ID")
    @ApiResponse(responseCode = "204", description = "Booking cancelled successfully")
    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(
            @Parameter(description = "ID of the booking to cancel") @PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }


    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(),booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(), room);
    }
}
