package kg.iaau.edu.diploma.ist.specification;

import kg.iaau.edu.diploma.ist.entity.News;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.model.UserPattern;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static kg.iaau.edu.diploma.ist.specification.SpecificatinHelper.getContainsLike;

public class UserSpecification implements Specification<User> {
    private UserPattern criteria;

    public UserSpecification(UserPattern criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isTrue(root.<Boolean>get("active")));
        if(criteria.getFirstName() != null && !criteria.getFirstName().isEmpty()){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("firstName")),
                    getContainsLike(criteria.getFirstName())));
        }
        if(criteria.getLastName() != null && !criteria.getLastName().isEmpty()){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("lastName")),
                    getContainsLike(criteria.getLastName())));
        }
        if(criteria.getEmail() != null && !criteria.getEmail().isEmpty()){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("email")),
                    getContainsLike(criteria.getEmail())));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
