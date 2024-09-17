package com.ajay.service;

import com.ajay.exception.InvalidBookingRequestException;
import com.ajay.model.BookedRoom;
import com.ajay.model.Room;
import com.ajay.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{

    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private IRoomService roomService;

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }


    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
       if(bookingRequest.getCheckedOutDate().isBefore(bookingRequest.getCheckedInDate())){
           throw new InvalidBookingRequestException("Check In date must come before check out date");
       }
        Room room = roomService.getRoomById(roomId).get();
       List<BookedRoom> existingBookings = room.getBookings();
       boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
       if(roomIsAvailable){
           room.addBooking(bookingRequest);
           bookingRepository.save(bookingRequest);
       }else{
           throw new InvalidBookingRequestException("Sorry,This room is not available for selected date");
       }
       return  bookingRequest.getBookingConfirmationCode();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckedOutDate().isBefore(existingBooking.getCheckedOutDate())
                        || (bookingRequest.getCheckedInDate().isAfter(existingBooking.getCheckedInDate()))
                        && bookingRequest.getCheckedInDate().isBefore(existingBooking.getCheckedOutDate())
                        || (bookingRequest.getCheckedInDate().isAfter(existingBooking.getCheckedInDate()))

                         && bookingRequest.getCheckedOutDate().equals(existingBooking.getCheckedOutDate())
                         || (bookingRequest.getCheckedInDate().isBefore(existingBooking.getCheckedInDate()))

                         && bookingRequest.getCheckedOutDate().isAfter(existingBooking.getCheckedOutDate())

                         || (bookingRequest.getCheckedInDate().equals(existingBooking.getCheckedOutDate()))
                         && bookingRequest.getCheckedOutDate().equals(existingBooking.getCheckedInDate())

                         ||(bookingRequest.getCheckedInDate().equals(existingBooking.getCheckedOutDate()))
                         && bookingRequest.getCheckedOutDate().equals(bookingRequest.getCheckedInDate())
                        );
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
