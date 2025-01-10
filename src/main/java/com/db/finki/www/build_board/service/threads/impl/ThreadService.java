package com.db.finki.www.build_board.service.threads.impl;

import com.db.finki.www.build_board.entity.threads.BBThread;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.repository.UserRepository;
import com.db.finki.www.build_board.repository.threads.BBThreadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {
    private final BBThreadRepository bbThreadRepository;
    private final UserRepository userRepository;

    public ThreadService(BBThreadRepository bbThreadRepository, UserRepository userRepository) {
        this.bbThreadRepository = bbThreadRepository;
        this.userRepository = userRepository;
    }

    public void rate(int threadId, int userId,boolean like){
        BBThread thread = bbThreadRepository.findById(threadId);
        BBUser user = userRepository.findById(userId);
        if(like){
            List<BBUser> users = thread.getLikes();
            if(!users.contains(user)){
                thread.getLikes().add(user);
            }

        } else {
            thread.getLikes().remove(user);
        }
        bbThreadRepository.save(thread);
    }
}
