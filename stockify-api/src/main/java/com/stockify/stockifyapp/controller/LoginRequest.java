package com.stockify.stockifyapp.controller;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;

}
