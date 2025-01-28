package com.db.finki.www.build_board.entity.thread;

import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import com.db.finki.www.build_board.entity.thread.multi_valued_attribute.Guideline;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "topic_thread")
public class Topic extends BBThread implements NamedThread {

    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "topic")
    private List<Guideline> guidelines;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private BBThread parent;

    @Override
    public String getTypeName() {return "topics";}
}
