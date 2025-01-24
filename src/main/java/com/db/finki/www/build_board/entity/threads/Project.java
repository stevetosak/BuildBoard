package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.requests.ProjectRequests;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import com.db.finki.www.build_board.entity.user_types.Developer;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO: project request
//TODO: crud na kanali
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

    public Project(String title, String repoUrl, String description, BBUser user){
        setTitle(title);
        setRepoUrl(repoUrl);
        setDescription(description);
        setUser(user);
    }

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "parent")
    private List<Topic> topics = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name ="developer_associated_with_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private Set<Developer> developers = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<ProjectRequests> requests = new HashSet<>();

    @Override
    public String getTypeName() {
        return "project";
    }
    public String getDescription() {return content;}
    public void setDescription(String description) {this.content = description;}

}
