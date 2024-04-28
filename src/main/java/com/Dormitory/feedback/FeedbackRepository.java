package com.Dormitory.feedback;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Optional<List<Feedback>> findByStudentId(Integer studentId);
}
