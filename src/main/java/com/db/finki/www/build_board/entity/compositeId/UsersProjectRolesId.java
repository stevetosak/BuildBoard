package com.db.finki.www.build_board.entity.compositeId;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersProjectRolesId {
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    ProjectRole role;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    BBUser user;

}
