package com.aero.TafBookingService.Models;

import lombok.Data;

@Data
public class FlightsDTO {

    private Long id;
    private String flight_number;
    private String departure;
    private String arrival ;
    private String departure_time ;
    private String arrival_time ;
    private double price;
    private Long available_seats;
    private String created_at;
    private String updated_at;
}
