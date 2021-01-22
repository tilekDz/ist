package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.repository.ExamQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamQuestionService {
    private final ExamQuestionRepository examQuestionRepository;

    @Autowired
    public ExamQuestionService(ExamQuestionRepository examQuestionRepository) {
        this.examQuestionRepository = examQuestionRepository;
    }


}
