package kg.iaau.edu.diploma.ist.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "SUBJECT_NAME")
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition="TEXT")
    private String description;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @Column(name = "CREATED_DATE")
    private Date date;

    public Subject() {
    }

    public Subject(String name, String description, Boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
