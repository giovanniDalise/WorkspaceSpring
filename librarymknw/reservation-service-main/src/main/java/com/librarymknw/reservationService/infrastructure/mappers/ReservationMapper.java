package com.librarymknw.reservationService.infrastructure.mappers;

import com.librarymknw.reservationService.core.domain.models.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {

    @Select("SELECT * FROM reservation")
    List<Reservation> findAll();

    @Select("SELECT * FROM reservation WHERE id = #{id}")
    Reservation findById(@Param("id") Long id);

    @Insert("INSERT INTO reservation (user_id, book_id, reservation_date, due_date) " +
            "VALUES (#{userId}, #{bookId}, #{reservationDate}, #{dueDate})")
    void save(Reservation reservation);

    @Update("UPDATE reservation SET user_id = #{userId}, book_id = #{bookId}, " +
            "reservation_date = #{reservationDate}, due_date = #{dueDate} WHERE id = #{id}")
    void update(Reservation reservation);

    @Delete("DELETE FROM reservation WHERE id = #{id}")
    void delete(@Param("id") Long id);
}
