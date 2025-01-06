package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import com.db.finki.www.build_board.entity.threads.multi_valued_attributes.Guideline;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "topic_thread")
public class Topic extends BBThread implements NamedThread {

    private String title;

    @OneToMany(mappedBy = "topic")
    private List<Guideline> guidelines;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Topic parent;

    @Override
    public String getTypeName() {return "topic";}
}
