package com.ajay.service;

import com.ajay.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Ajay wankhade
 */
public interface IRoomService {
    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

    List<Room> getAllRooms();

    Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes);


    void deleteRoom(Long roomId) throws SQLException;

    Optional<Room> getRoomById(Long roomId);
}
