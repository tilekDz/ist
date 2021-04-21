package kg.iaau.edu.diploma.ist.repository;

import kg.iaau.edu.diploma.ist.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long>, JpaSpecificationExecutor {
    @Query(value = "SELECT * from (SELECT * from EVENTS n where is_active = TRUE order by id desc) as t limit 3", nativeQuery = true)
    List<Events> findLastThreeEvents();
}
