package com.spring.springbootmonolithicweb.dao;

import com.spring.springbootmonolithicweb.model.Quiz;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
