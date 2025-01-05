package com.db.finki.www.build_board.service;

import com.db.finki.www.build_board.entity.BBUser;

public interface UserService {
    BBUser verifyCredentials(String username, String password);
}
