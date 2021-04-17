package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.TestExam;
import kg.iaau.edu.diploma.ist.repository.TestExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<TestExam> getAllTestExams(Specification specification, Pageable pageable) {
        return testExamRepository.findAll(specification, pageable);
    }

    public void save(TestExam testExam) {
        this.testExamRepository.save(testExam);
    }

    public void delete(TestExam testExam) {
        testExam.setActive(false);
        this.testExamRepository.save(testExam);
    }

    public List<TestExam> getAllActive() {
        return this.testExamRepository.findAllByActiveIsTrue();
    }


}
