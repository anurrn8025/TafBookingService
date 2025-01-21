package com.aero.TafBookingService.Controllers;
import com.aero.TafBookingService.Models.BookingDTO;
import com.aero.TafBookingService.Services.BookingServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@AllArgsConstructor
public class BookingController {

    private final BookingServiceImpl bookingServiceImpl;

    //Create a new booking.
    @PostMapping("/bookings")
    public BookingDTO AddBooking(@RequestBody BookingDTO newBooking){
        return bookingServiceImpl.AddBooking(newBooking);
    }

    //Get booking details.
    @GetMapping("/bookings/{bookingId}")
    public BookingDTO getSingleBooking(@PathVariable("bookingId")Long bookingId){
        return bookingServiceImpl.getSingleBooking(bookingId);
    }

    //Retrieve all bookings for a specific user
    @GetMapping("/bookings/user/{userId}")
    public List<BookingDTO> getAllBookingsOfUser(@PathVariable("userId")Long userId){
        return bookingServiceImpl.getAllBookingsOfUser(userId);
    }

    //Cancel a booking.
    //  Update flight details
    @PutMapping("/bookings/{bookingId}")
    public BookingDTO cancelBooking(@PathVariable("bookingId")Long bookingId){
        return bookingServiceImpl.cancelBooking(bookingId);
    }

}
