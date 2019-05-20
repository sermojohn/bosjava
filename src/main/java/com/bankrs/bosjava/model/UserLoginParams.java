package com.bankrs.bosjava.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginParams {
    private String username, password;
}
