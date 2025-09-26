package com.db.finki.www.build_board.entity.thread;

import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.user_type.BBUser;
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
public class Project extends BBThread {

    private String title;

    @Column(name = "repo_url")
    private String repoUrl;

    public Project(String title, String repoUrl, String description, BBUser user){
        setTitle(title);
        setRepoUrl(repoUrl);
        setDescription(description);
        setUser(user);
    }

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "parent")
    private List<Topic> topics = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name ="developer_associated_with_project",
            joinColumns = @JoinColumn(name = "in_project"),
            inverseJoinColumns = @JoinColumn(name = "about_dev")
    )
    private Set<BBUser> developers = new HashSet<>(); // NE GO KORISTI GETTEROT OVDE

    @OneToMany(mappedBy = "project")
    private Set<ProjectRequests> requests = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @OrderBy("name")
    private Set<Channel> channels;

    public String getDescription() {return content;}
    public void setDescription(String description) {this.content = description;}

}
