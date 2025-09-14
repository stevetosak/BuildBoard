package com.db.finki.www.build_board.entity.access_managment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProjectResource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_resource_id_seq")
    @SequenceGenerator(name = "project_resource_id_seq",sequenceName = "project_resource_id_seq",allocationSize=1)
    int id;
}
