package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.Subject;
import kg.iaau.edu.diploma.ist.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject getSubjectById(long id) {
        return this.subjectRepository.findById(id).orElse(null);
    }

    public Page<Subject> getAllSubject(Specification specification, Pageable pageable) {
        return subjectRepository.findAll(specification, pageable);
    }

    public void save(Subject subject) {
        this.subjectRepository.save(subject);
    }

    public void delete(Subject subject) {
        subject.setActive(false);
        this.subjectRepository.save(subject);
    }
    public List<Subject> getAllActive() {
        return this.subjectRepository.findAllByActiveIsTrue();
    }
}
