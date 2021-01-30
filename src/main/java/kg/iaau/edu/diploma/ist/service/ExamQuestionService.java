package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.repository.ExamQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamQuestionService {
    private final ExamQuestionRepository examQuestionRepository;

    @Autowired
    public ExamQuestionService(ExamQuestionRepository examQuestionRepository) {
        this.examQuestionRepository = examQuestionRepository;
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



}
