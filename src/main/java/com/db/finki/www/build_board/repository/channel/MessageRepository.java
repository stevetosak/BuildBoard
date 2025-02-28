package com.db.finki.www.build_board.repository.channel;

import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.entity.compositeId.MessageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, MessageId> {
    List<Message> findAllByNameAndProjectIdOrderBySentAtAsc(String channelName, Integer projectId);
}
