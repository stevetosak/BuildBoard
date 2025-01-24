package com.db.finki.www.build_board.entity.user_types;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "developer")
@Getter
@Setter
@NoArgsConstructor
public class Developer extends BBUser{

}
