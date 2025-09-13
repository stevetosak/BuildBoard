package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUser;
import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedUserRepo extends JpaRepository<BlacklistedUser, BlacklistedUserId> {
}
