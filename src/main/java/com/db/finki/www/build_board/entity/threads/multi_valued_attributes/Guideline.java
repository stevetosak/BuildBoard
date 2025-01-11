package com.db.finki.www.build_board.entity.threads.multi_valued_attributes;

import com.db.finki.www.build_board.entity.threads.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "topic_guidelines")
public class Guideline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guideline_gen")
    @SequenceGenerator(name = "guideline_gen", sequenceName = "topic_guidelines_id_seq",  allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private String description;

}
