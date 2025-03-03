package com.db.finki.www.build_board.service.util;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.UserRepository;
import com.db.finki.www.build_board.repository.thread.BBThreadRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ThreadService {
    private final BBThreadRepository bbThreadRepository;
    private final UserRepository userRepository;

    public ThreadService(BBThreadRepository bbThreadRepository, UserRepository userRepository) {
        this.bbThreadRepository = bbThreadRepository;
        this.userRepository = userRepository;
    }

    public BBThread rate(int threadId, int userId,boolean like){
        BBThread thread = bbThreadRepository.findById(threadId);
        BBUser user = userRepository.findById(userId);
        if(like){
            Set<BBUser> users = thread.getLikes();
            if(!users.contains(user)){
                thread.getLikes().add(user);
            }

        } else {
            thread.getLikes().remove(user);
        }
        return bbThreadRepository.save(thread);
    }
}
