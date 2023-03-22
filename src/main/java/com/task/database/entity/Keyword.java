package com.task.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = @Index(name = "idx_keyword_01", columnList = "searchCount desc, word"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Keyword {

    @Id
    private String word;

    @Column(nullable = false)
    private Integer searchCount;

}
