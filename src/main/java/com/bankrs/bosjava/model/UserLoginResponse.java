package com.bankrs.bosjava.model;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String id, token;
}
