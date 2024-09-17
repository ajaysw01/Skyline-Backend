package com.ajay.repository;

import com.ajay.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Ajay wankhade
 */
public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("SELECT r.roomType FROM Room r ")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r FROM Room r " +
            "WHERE r.roomType LIKE %:roomType% " +
            "AND r.id NOT IN (" +
            "SELECT br.room.id FROM BookedRoom br " +
            "WHERE ((br.checkedInDate <= :checkOutDate) AND (br.checkedOutDate >= :checkInDate)))")
    List<Room> findAvailableRoomsByDatesAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

}
