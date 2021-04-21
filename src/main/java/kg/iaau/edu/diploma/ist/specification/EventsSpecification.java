package kg.iaau.edu.diploma.ist.specification;

import kg.iaau.edu.diploma.ist.entity.Events;
import kg.iaau.edu.diploma.ist.entity.Faculty;
import kg.iaau.edu.diploma.ist.model.EventsPattern;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static kg.iaau.edu.diploma.ist.specification.SpecificatinHelper.getContainsLike;

public class EventsSpecification implements Specification<Events> {
    private EventsPattern criteria;

    public EventsSpecification(EventsPattern criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Events> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isTrue(root.<Boolean>get("active")));
        if(criteria.getTitle() != null && !criteria.getTitle().isEmpty()){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("title")),
                    getContainsLike(criteria.getTitle())));
        }
        if(criteria.getDescription() != null && !criteria.getDescription().isEmpty()){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("description")),
                    getContainsLike(criteria.getDescription())));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
