package com.db.finki.www.build_board.config.jwt.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDTO {
    private String username;
    private String password;
}
