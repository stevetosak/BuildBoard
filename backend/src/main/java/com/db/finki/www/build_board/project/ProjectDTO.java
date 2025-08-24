package com.db.finki.www.build_board.project;

import com.db.finki.www.build_board.project.associated_entities.permissions.Permissions;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Builder
public class ProjectDTO {
    public String name;
    public String logo;
    public String repoURL;
    public String description;
    public List<CustomRoles> roles;
    public List<Member> members;

    @Builder
    public static class Member {
        public String username;
        public String logo;
        public List<GrantedAuthority> typeOfUser;
        public List<String> roles;
    }

    @Builder
    public static class CustomRoles{
       public String name;
       public List<String> permissions;
       public String description;
    }
}
