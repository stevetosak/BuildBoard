package com.db.finki.www.build_board.config;

import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.access_managment.ProjectAccessManagementService;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class WebSocketPermissionInterceptor implements HandshakeInterceptor {
    private final ProjectAccessManagementService projectAccessManagementService;
    private final ChannelService channelService;
    private final ProjectService projectService;

    public WebSocketPermissionInterceptor(ProjectAccessManagementService projectAccessManagementService, ChannelService channelService, ProjectService projectService) {this.projectAccessManagementService = projectAccessManagementService;
        this.channelService = channelService;
        this.projectService = projectService;
    }

    private boolean checkStringParam(String x){
        return x == null || x.isBlank();
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        URI uri = request.getURI();
        MultiValueMap<String, String> queryParams =
                UriComponentsBuilder
                        .fromUri(uri).build().getQueryParams();

        String projectName =  queryParams.getFirst("project");
        String channelName =  queryParams.getFirst("channelName");

        if(checkStringParam(projectName) || checkStringParam(channelName)){
            throw new BadRequestException("You didn't provide the correct params");
        }

        projectName= URLDecoder.decode(projectName,
                StandardCharsets.UTF_8);
        channelName= URLDecoder.decode(channelName,StandardCharsets.UTF_8);

        Project project = projectService.getByTitle(projectName);
        Channel channel = channelService.getByNameAndProject(channelName,
                project);

        BBUser user = (BBUser)
                ((ServletServerHttpRequest) request).getServletRequest().getSession().getAttribute("user");

        if(!projectAccessManagementService.hasPermissionToAccessResource(
                user.getId(),
                Permission.WRITE,
                channel.getProjectResource()
                        .getId(),
                project.getId()
                                                                        )){
            return false;
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
