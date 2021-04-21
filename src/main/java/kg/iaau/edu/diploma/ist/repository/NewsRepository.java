package kg.iaau.edu.diploma.ist.repository;

import kg.iaau.edu.diploma.ist.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor {
    @Query(value = "SELECT * from (SELECT * from NEWS n where is_active = TRUE order by id desc) as t limit 2", nativeQuery = true)
    List<News> findLastFourNews();
}
