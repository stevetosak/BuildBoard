package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//TODO: implement crud opertaions on project and associated features
@Data
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

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "topic_belongs_to_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics = new ArrayList<>();

    @Override
    public String getTypeName() {
        return "project";
    }
    public String getDescription() {return content;}
    public void setDescription(String description) {this.content = description;}

}
