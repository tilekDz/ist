package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.Faculty;
import kg.iaau.edu.diploma.ist.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getFacultyById(long id) {
        return this.facultyRepository.findById(id).orElse(null);
    }

    public Page<Faculty> getAllFaculties(Specification specification, Pageable pageable) {
        return facultyRepository.findAll(specification, pageable);
    }

    public void save(Faculty faculty) {
        this.facultyRepository.save(faculty);
    }

    public void delete(Faculty faculty) {
        faculty.setActive(false);
        this.facultyRepository.save(faculty);
    }
    public List<Faculty> getAllActive() {
        return this.facultyRepository.findAllByActiveIsTrue();
    }


}
