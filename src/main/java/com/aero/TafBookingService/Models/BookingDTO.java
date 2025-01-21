package com.aero.TafBookingService.Models;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private UsersDTO users;
    private FlightsDTO flights;
    private String status;
    private String created_at;
    private String updated_at;

}
