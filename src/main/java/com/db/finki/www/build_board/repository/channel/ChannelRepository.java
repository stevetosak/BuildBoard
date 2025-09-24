package com.db.finki.www.build_board.repository.channel;

import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.thread.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, UUID> {
    List<Channel> findAllByProjectIdOrderByNameAsc(Integer projectId);
    List<Channel> findAllByDeveloperIdOrderByNameAsc(Integer developerId);
    Channel findByProjectTitleAndNameOrderByNameAsc(String title, String name);

    List<Channel> findByIdIn(List<UUID> ids);

    void deleteByNameAndProjectId(String name, Integer projectId);

    Channel findByNameAndProject(String name,Project project);
}
