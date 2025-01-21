package com.aero.TafBookingService.Services.Interfaces;
import com.aero.TafBookingService.Models.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO AddBooking(BookingDTO newBooking);
    BookingDTO getSingleBooking(Long bookingId);
    List<BookingDTO> getAllBookingsOfUser(Long userId);
    BookingDTO cancelBooking(Long bookingId);


}
