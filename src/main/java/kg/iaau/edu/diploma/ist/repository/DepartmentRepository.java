package kg.iaau.edu.diploma.ist.repository;

import kg.iaau.edu.diploma.ist.entity.Department;
import kg.iaau.edu.diploma.ist.entity.Faculty;
import kg.iaau.edu.diploma.ist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor {
    List<Department> findAllByActiveIsTrue();
}
