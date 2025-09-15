package com.db.finki.www.build_board.dto;

import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.access_managment.ProjectResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResourceWrapper {
    Permission permission;
    ProjectResource resource;

}


