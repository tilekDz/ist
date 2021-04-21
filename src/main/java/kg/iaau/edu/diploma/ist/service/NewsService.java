package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.News;
import kg.iaau.edu.diploma.ist.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void save(News news) {
        this.newsRepository.save(news);
    }

    public void delete(News news) {
        news.setActive(false);
        this.newsRepository.save(news);
    }

    public Page<News> getAllNews(Specification specification, Pageable pageable) {
        return this.newsRepository.findAll(specification, pageable);
    }

    public News getById(Long id) {
        return this.newsRepository.findById(id).orElse(null);
    }

    public List<News> findLastFourNews() {
        return this.newsRepository.findLastFourNews();
    }
}
