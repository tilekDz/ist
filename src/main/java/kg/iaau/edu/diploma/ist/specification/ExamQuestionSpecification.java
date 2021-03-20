package kg.iaau.edu.diploma.ist.specification;

import kg.iaau.edu.diploma.ist.entity.Department;
import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import kg.iaau.edu.diploma.ist.model.DepartmentPattern;
import kg.iaau.edu.diploma.ist.model.ExamQuestionPattern;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static kg.iaau.edu.diploma.ist.specification.SpecificatinHelper.getContainsLike;

public class ExamQuestionSpecification implements Specification<ExamQuestion> {

    private ExamQuestionPattern criteria;

    public ExamQuestionSpecification(ExamQuestionPattern examQuestionPattern){
        this.criteria = examQuestionPattern;
    }
    @Override
    public Predicate toPredicate(Root<ExamQuestion> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isTrue(root.<Boolean>get("active")));
        if(criteria.getName() != null && !criteria.getName().isEmpty()){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("name")),
                    getContainsLike(criteria.getName())));
        }
        if(criteria.getDescription() != null && !criteria.getDescription().isEmpty()){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("description")),
                    getContainsLike(criteria.getDescription())));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}