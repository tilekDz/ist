package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.Department;
import kg.iaau.edu.diploma.ist.entity.Faculty;
import kg.iaau.edu.diploma.ist.entity.Subject;
import kg.iaau.edu.diploma.ist.repository.DepartmentRepository;
import kg.iaau.edu.diploma.ist.repository.FacultyRepository;
import kg.iaau.edu.diploma.ist.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartmentById(long id) {
        return this.departmentRepository.findById(id).orElse(null);
    }

    public void save(Department department) {
        this.departmentRepository.save(department);
    }

    public void delete(Department department) {
        department.setActive(false);
        this.departmentRepository.save(department);
    }
    public List<Department> getAllActive() {
        return this.departmentRepository.findAllByActiveIsTrue();
    }
}
