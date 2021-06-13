package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import kg.iaau.edu.diploma.ist.entity.TestExam;
import kg.iaau.edu.diploma.ist.repository.ExamQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamQuestionService {
    private final ExamQuestionRepository examQuestionRepository;

    @Autowired
    public ExamQuestionService(ExamQuestionRepository examQuestionRepository) {
        this.examQuestionRepository = examQuestionRepository;
    }

    public ExamQuestion getExamQuestionById(long id) {
        return this.examQuestionRepository.findById(id).orElse(null);
    }

    public Page<ExamQuestion> getAllExamQuestions(Specification specification, Pageable pageable) {
        return examQuestionRepository.findAll(specification, pageable);
    }

    public void save(ExamQuestion examQuestion) {
        this.examQuestionRepository.save(examQuestion);
    }

    public void delete(ExamQuestion examQuestion) {
        examQuestion.setActive(false);
        this.examQuestionRepository.save(examQuestion);
    }

    public List<ExamQuestion> getAllActive() {
        return this.examQuestionRepository.findAllByActiveIsTrue();
    }

    public List<ExamQuestion> findAllByTest(TestExam testExam) {
        return this.examQuestionRepository.findAllByTestExam(testExam);
    }

}
