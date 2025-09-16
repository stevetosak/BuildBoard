package com.db.finki.www.build_board.entity.access_managment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "permissions")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Permission {
    @Id
    String name;

    public static final String READ = "READ";
    public static final String WRITE = "WRITE";
    public static final String DELETE = "DELETE";
    public static final String CREATE = "CREATE";

    public Permission(String name){
        this.name = name;
    }
}
