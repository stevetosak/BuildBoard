package com.db.finki.www.build_board.dto;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeveloperWithRolesForProjectDTO {
    private BBUser user;
    private List<ProjectRole> roles;
}
