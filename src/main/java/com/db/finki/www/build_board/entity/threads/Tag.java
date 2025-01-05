package com.db.finki.www.build_board.entity.threads;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    String name;

    @ManyToMany(mappedBy = "tags")
    private List<BBThread> threads;
}
