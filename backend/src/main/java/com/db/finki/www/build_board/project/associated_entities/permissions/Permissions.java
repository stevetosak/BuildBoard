package com.db.finki.www.build_board.project.associated_entities.permissions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Permissions {
    @Id
    public String name;
}
