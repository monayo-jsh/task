package com.task.database.repository;

import com.task.database.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, String> {

    @Modifying
    @Query("UPDATE Keyword Set searchCount = searchCount + 1 where word = :word")
    void increaseSearchCountByKeyword(@Param("word") String word);

}
