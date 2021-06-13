package kg.iaau.edu.diploma.ist.repository;

import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import kg.iaau.edu.diploma.ist.entity.TestExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long>, JpaSpecificationExecutor {
    List<ExamQuestion> findAllByActiveIsTrue();
    List<ExamQuestion> findAllByTestExam(TestExam testExam);
}
