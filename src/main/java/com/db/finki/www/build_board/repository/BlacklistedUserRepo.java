package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUser;
import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlacklistedUserRepo extends JpaRepository<BlacklistedUser, BlacklistedUserId> {
    @Query(nativeQuery = true,
    value = """
        select exists(
                select *
                from blacklisted_user bu
                where bu.user_id=:userId
        ) 
    """)
    boolean isUserInBlacklist(@Param("userId") long userId);
}
