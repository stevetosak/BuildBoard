package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUser;
import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUserId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BlacklistedUserRepo extends JpaRepository<BlacklistedUser, BlacklistedUserId> {
    @Query(nativeQuery = true,
    value = """
        select exists(
                select *
                from blacklisted_user bu
                where bu.end_date is NULL and bu.user_id=:userId and bu.topic_id = :topicId
        ) 
    """)
    boolean isUserInBlacklist(@Param("userId") long userId, @Param("topicId") long topicId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = """
    update blacklisted_user
    set end_date = now()
    where topic_id=:topic and user_id = :user
""")
    void revoke(@Param("topic") long topicId, @Param("user") int blacklistedUserId);

    List<BlacklistedUser> findAllByTopicIdAndEndTimeIsNull(Integer topicI);
}
