package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.Subject;
import kg.iaau.edu.diploma.ist.entity.TestExam;
import kg.iaau.edu.diploma.ist.repository.TestExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestExamService {
    private final TestExamRepository testExamRepository;

    @Autowired
    public TestExamService(TestExamRepository testExamRepository) {
        this.testExamRepository = testExamRepository;
    }

    public TestExam getTestExamById(long id) {
        return this.testExamRepository.findById(id).orElse(null);
    }


}
