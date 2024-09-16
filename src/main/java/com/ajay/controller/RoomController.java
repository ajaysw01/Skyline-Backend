package com.ajay.controller;

import com.ajay.exception.PhotoRetrievalException;
import com.ajay.exception.ResourceNotFoundException;
import com.ajay.model.BookedRoom;
import com.ajay.model.Room;
import com.ajay.reponse.RoomResponse;
import com.ajay.service.BookingService;
import com.ajay.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Ajay wankhade
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final IRoomService roomService;

    private final BookingService bookingService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType")    String roomType,
            @RequestParam("roomPrice")  BigDecimal roomPrice) throws SQLException, IOException {

        System.out.println("Received photo: " + photo.getOriginalFilename());
        System.out.println("Received roomType: " + roomType);
        System.out.println("Received roomPrice: " + roomPrice);

        Room savedRoom = roomService.addNewRoom(photo,roomType,roomPrice);

        //convert to roomresponse
        RoomResponse response = new RoomResponse(savedRoom.getId(),savedRoom.getRoomType(), savedRoom.getRoomPrice());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/room/types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException, PhotoRetrievalException {
       List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String base64 = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64);
                roomResponses.add(roomResponse);
            }
        }
        return  ResponseEntity.ok(roomResponses);
    }

   @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long roomId,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false)BigDecimal roomPrice,
            @RequestParam(required = false)MultipartFile photo
    ) throws SQLException, IOException, PhotoRetrievalException {

        byte[] photoBytes = photo!= null && !photo.isEmpty()?
                photo.getBytes(): roomService.getRoomPhotoByRoomId(roomId);
        Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes): null;
        Room theRoom = roomService.updateRoom(roomId,roomType,roomPrice,photoBytes);
        theRoom.setPhoto(photoBlob);
        RoomResponse roomResponse = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);

    }


    @DeleteMapping("/delete/room/{roomId}")
    private ResponseEntity<Void> deleteRoom(
            @PathVariable Long roomId) throws SQLException {
            roomService.deleteRoom(roomId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(
            @PathVariable Long roomId) {
        Optional<Room> theRoom = roomService.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomResponse roomResponse = null;
            try {
                roomResponse = getRoomResponse(room);
            } catch (PhotoRetrievalException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(Optional.of(roomResponse));

        }).orElseThrow(() -> new ResourceNotFoundException("Oh Sorry, Room no found"));
    }

    private RoomResponse getRoomResponse(Room room) throws PhotoRetrievalException {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
//        List<BookingResponse> bookingInfo = bookings
//                .stream()
//                .map(booking -> new BookingResponse(booking.getBookingId(),
//                        booking.getCheckedInDate(),
//                        booking.getCheckedOutDate(),
//                        booking.getBookingConfirmationCode())).toList();

        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if(photoBlob != null){
            try{
                photoBytes = photoBlob.getBytes(1,(int)photoBlob.length());
            }catch (SQLException e){
                throw  new PhotoRetrievalException("Error retrieving photo");
            }
        }
    return  new RoomResponse(room.getId(),
            room.getRoomType(),
            room.getRoomPrice(),
            room.isBooked(),
            photoBytes
//            //bookingInfo
            );

    }

    private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }


}
