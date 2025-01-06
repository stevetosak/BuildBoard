package com.db.finki.www.build_board.entity.user_types;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_manager")
public class ProjectOwner extends BBUser{
}
