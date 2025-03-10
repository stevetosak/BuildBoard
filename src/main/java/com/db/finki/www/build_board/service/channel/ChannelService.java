package com.db.finki.www.build_board.service.channel;

import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.compositeId.ChannelId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.user_type.Developer;
import com.db.finki.www.build_board.repository.DeveloperRepository;
import com.db.finki.www.build_board.repository.channel.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final DeveloperRepository developerRepository;

    private final String INVALID_INPUT_REGEX = "(^/|/$|[\\#?])";

    private boolean isInvalidInput(String input) {
        return input.matches(INVALID_INPUT_REGEX);
    }

    public ChannelService(ChannelRepository channelRepository, DeveloperRepository developerRepository) {
        this.channelRepository = channelRepository;
        this.developerRepository = developerRepository;
    }

    public List<Channel> getAllChannelsForProject(Project project) {
        return channelRepository.findAllByProjectIdOrderByNameAsc(project.getId());
    }

    public Channel create(Project project, String channelName, String description, BBUser user){
        if(channelRepository.findByProjectTitleAndNameOrderByNameAsc(project.getTitle(), channelName) != null) {
            throw new IllegalArgumentException("Channel with title '" + channelName + "' already exists");
        }

        if(isInvalidInput(channelName)) {
            throw new IllegalArgumentException("Channel name contains invalid characters");
        }

        Developer developer = developerRepository.findById(user.getId());
        Channel channel = new Channel(channelName,project,description,developer);
        return channelRepository.save(channel);
    }
    public Channel getByNameAndProject(String channelName, Project project){
        return channelRepository.findByProjectTitleAndNameOrderByNameAsc(project.getTitle(), channelName);
    }
    public void deleteChannel(String channelName,Project project){
        ChannelId cid = new ChannelId(project.getId(), channelName);
        channelRepository.deleteById(cid);
    }


}
