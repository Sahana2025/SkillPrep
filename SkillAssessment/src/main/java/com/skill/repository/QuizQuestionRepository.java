package com.skill.repository;

import com.skill.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {
    // Custom method to find questions by level
    List<QuizQuestion> findByLevel(String level);
}
