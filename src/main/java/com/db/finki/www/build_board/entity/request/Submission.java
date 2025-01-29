package com.db.finki.www.build_board.entity.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "submission")
@Getter
@Setter
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(generator = "sub_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_gen",sequenceName = "submission_id_seq",allocationSize = 1,initialValue = 0)
    private Integer id;

    @OneToOne(mappedBy = "forSubmission")
    private Feedback feedback;
}
