package com.db.finki.www.build_board.entity.access_managment;

import com.db.finki.www.build_board.entity.compositeId.UsersProjectRolesId;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_role_is_assigned_to_developer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersProjectRoles {
    @EmbeddedId
    UsersProjectRolesId id;

    public ProjectRole getProjectRole() {
        return id.getRole();
    }
    public BBUser getUser() {
        return id.getUser();
    }

}
