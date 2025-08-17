package com.db.finki.www.build_board.entity.thread;

import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "project_thread")
public class Project extends BBThread implements NamedThread {

    private String title;

    @Column(name = "repo_url")
    private String repoUrl;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parent")
    private List<Topic> topics = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "developer_associated_with_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private Set<BBUser> developers = new HashSet<>(); // NE GO KORISTI GETTEROT OVDE

    @OneToMany(mappedBy = "project")
    private Set<ProjectRequests> requests = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @OrderBy("name")
    private Set<Channel> channels;

    public Project(String title, String repoUrl, String description, BBUser user) {
        setTitle(title);
        setRepoUrl(repoUrl);
        setDescription(description);
        setUser(user);
    }

    @Override
    public String getTypeName() {
        return "projects";
    }

    public String getDescription() {return content;}
    public void setDescription(String description) {this.content = description;}

}
