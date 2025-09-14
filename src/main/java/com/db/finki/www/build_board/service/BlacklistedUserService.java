package com.db.finki.www.build_board.service;

import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUser;
import com.db.finki.www.build_board.repository.BlacklistedUserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlacklistedUserService {
    private final BlacklistedUserRepo  blacklistedUserRepo;

    public BlacklistedUserService(BlacklistedUserRepo blacklistedUserRepo) {this.blacklistedUserRepo = blacklistedUserRepo;}

    public boolean isBlacklisted(long userId, long topicId) {
        return blacklistedUserRepo.isUserInBlacklist(userId, topicId);
    }

    public void revoke(long topicId, int blacklistedUserId) {
        blacklistedUserRepo.revoke(topicId,blacklistedUserId);
    }

    public Set<Integer> findForTopic(int topicId) {
       return blacklistedUserRepo.findAllByTopicId(topicId).stream().map(b -> b.getRefersTo()
               .getId()).collect(Collectors.toSet());
    }
}
