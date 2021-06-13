package kg.iaau.edu.diploma.ist.repository;

import kg.iaau.edu.diploma.ist.entity.ExamHistory;
import kg.iaau.edu.diploma.ist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamHistoryRepository extends JpaRepository<ExamHistory, Long> {
    List<ExamHistory> findAllByUser(User user);
    List<ExamHistory> findAllByDateIsNotNull();
}
