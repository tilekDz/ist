package kg.iaau.edu.diploma.ist.specification;

import kg.iaau.edu.diploma.ist.entity.Department;
import kg.iaau.edu.diploma.ist.model.DepartmentPattern;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static kg.iaau.edu.diploma.ist.specification.SpecificatinHelper.getContainsLike;

public class DepartmentSpecification implements Specification<Department> {

    private DepartmentPattern criteria;

    public DepartmentSpecification(DepartmentPattern departmentPattern){
        this.criteria = departmentPattern;
    }
    @Override
    public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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