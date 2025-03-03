package com.db.finki.www.build_board.entity.thread;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag implements Serializable {
    @Id
    String name;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    BBUser creator;

    public Tag(String name, BBUser user) {
        this.name = name;
        this.creator = user;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<BBThread> threads = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @JsonGetter("creator")
    public String getCreatorUsernameJackson(){
        return creator.getUsername() == null ? "" : creator.getUsername() ;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
