package com.voltstream.modules.users.infrastructure.rest.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
