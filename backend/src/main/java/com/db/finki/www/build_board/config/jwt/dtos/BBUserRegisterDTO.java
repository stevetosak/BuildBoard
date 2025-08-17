package com.db.finki.www.build_board.config.jwt.dtos;


import lombok.Data;

@Data
public class BBUserRegisterDTO {
    private String username;
    private String email;
    private  String name;
    private String password;
    private String description;
    private String sex;
}
