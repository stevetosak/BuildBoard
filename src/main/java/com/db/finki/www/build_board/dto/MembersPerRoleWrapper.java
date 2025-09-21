package com.db.finki.www.build_board.dto;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import lombok.Data;

import java.util.List;

@Data
public class MembersPerRoleWrapper {
    private ProjectRole projectRole;
    List<BBUser> users;
}
