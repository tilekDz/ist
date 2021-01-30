package kg.iaau.edu.diploma.ist.repository;

import kg.iaau.edu.diploma.ist.entity.Subject;
import kg.iaau.edu.diploma.ist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByActiveIsTrue();
}
