package com.db.finki.www.build_board.repository.channel;

import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.compositeId.ChannelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, ChannelId> {
    List<Channel> findAllByProjectIdOrderByNameAsc(Integer projectId);
    List<Channel> findAllByDeveloperIdOrderByNameAsc(Integer developerId);
    Channel findByProjectTitleAndName(String title, String name);
}
