package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "project_thread")
public class Project extends BBThread implements NamedThread {

    private String title;
    @Column(name = "repo_url")
    private String repoUrl;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getTypeName() {
        return "project";
    }
}
