package com.aero.TafBookingService.Models;

import lombok.Data;

@Data
public class UsersDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String created_at;
    private String updated_at;

}
