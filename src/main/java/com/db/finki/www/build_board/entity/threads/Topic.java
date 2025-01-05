package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@NoArgsConstructor
@Table(name = "topic_thread")
public class Topic extends BBThread implements NamedThread {

    private String title;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String,String> guidelines;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Topic parent;

    @Override
    public String getTypeName() {return "topic";}
    @Override
    public Integer getId() {return super.getId();}
    @Override
    public String getTitle() {return this.title;}
    public void setTitle(String title) {this.title = title;}
}
