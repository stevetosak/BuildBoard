package com.db.finki.www.build_board.entity.threads;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    String name;


    public Tag(String name) {
        this.name = name;
    }
    public Tag(){}

    public List<BBThread> getThreads() {
        return threads;
    }

    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "tags")
    private List<BBThread> threads = new ArrayList<>();
}
