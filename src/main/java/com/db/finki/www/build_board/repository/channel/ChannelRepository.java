package com.db.finki.www.build_board.repository.channel;

import com.db.finki.www.build_board.entity.channels.Channel;
import com.db.finki.www.build_board.entity.compositeId.ChannelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, ChannelId> {
    List<Channel> findAllByProjectId(Integer projectId);
    List<Channel> findAllByDeveloperId(Integer developerId);
    Channel findByProjectTitleAndName(String title, String name);
}
