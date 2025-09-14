package com.db.finki.www.build_board.entity.access_managment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "permissions")
@Entity
@Getter
@Setter
public class Permission {
    @Id
    String name;

    public static final String READ = "READ";
    public static final String WRITE = "WRITE";
    public static final String DELETE = "DELETE";
    public static final String CREATE = "CREATE";
}
