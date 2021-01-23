package kg.iaau.edu.diploma.ist.repository;

import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
}
