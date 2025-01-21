package com.aero.TafBookingService.Services;

import com.aero.TafBookingService.Models.*;
import com.aero.TafBookingService.Services.Interfaces.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${datastore.ms.url}")
    private String dataStoreUrl;

    @Value("${users.ms.url}")
    private String usrServUrl;

    @Value("${flights.ms.url}")
    private String flightServUrl;

    private static final Logger logger = LogManager.getLogger(BookingServiceImpl.class);

    @Override
    public BookingDTO AddBooking(BookingDTO newBooking) {
        //check if seats are available
        System.out.println("status is ="+ newBooking.getStatus());


        Long reqflight = newBooking.getFlights().getId();
        System.out.println("reqflight is ="+ reqflight);

        FlightsDTO flightsDTO = restTemplate.getForObject(
                flightServUrl + "/" + reqflight, FlightsDTO.class);
        if(flightsDTO == null){
            throw new RuntimeException("Flight is not available");
        }
        if(flightsDTO.getAvailable_seats()<=0) {
            throw new RuntimeException("Flight is fully booked");
        }

        //reduce seat count by 1 and update flights table
        flightsDTO.setAvailable_seats((flightsDTO.getAvailable_seats())-1);
        System.out.println("seats avilable : "+flightsDTO.getAvailable_seats());
        restTemplate.put(flightServUrl+ "/" + reqflight, flightsDTO);

        return restTemplate.postForObject(dataStoreUrl , newBooking, BookingDTO.class);

    }

    @Override
    public  BookingDTO getSingleBooking(Long bookingId) {
        System.out.println("bookingId id = "+ bookingId);
        return restTemplate.getForObject(dataStoreUrl+ "/" +bookingId, BookingDTO.class);
    }

    @Override
    public List<BookingDTO> getAllBookingsOfUser(Long userId) {
        //check if user exists
        UsersDTO users = restTemplate.getForObject(usrServUrl + "/" + userId, UsersDTO.class);
        if(users== null) {
            throw new RuntimeException("Invalid user");
        }

        return restTemplate.exchange( dataStoreUrl+ "/user/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookingDTO>>() {}).getBody();

    }

    @Override
    public BookingDTO cancelBooking(Long bookingId) {

        BookingDTO bookingDTO = restTemplate.getForObject(dataStoreUrl + "/" + bookingId, BookingDTO.class);
        if(bookingDTO== null) {
            throw new RuntimeException("Invalid Booking");
        }

        BookingDTO updtbooking = new BookingDTO();
        updtbooking.setStatus("cancelled");
       return  restTemplate.patchForObject(dataStoreUrl+ "/" +bookingId,updtbooking,BookingDTO.class);

    }
}
