package com.db.finki.www.build_board.service;

import com.db.finki.www.build_board.entity.user_types.BBUser;

public interface UserService {
    BBUser verifyCredentials(String username, String password);
}
