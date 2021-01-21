package kg.iaau.edu.diploma.ist.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "test_exam")
public class TestExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "EXAM_NAME")
    private String name;

    @Column(name = "EXAM_DESCRIPTION", columnDefinition="TEXT")
    private String description;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User user;

    @Column(name = "CREATED_DATE")
    private Date date;

    public TestExam() {
    }

    public TestExam(String name, String description, Boolean active, Subject subject, User user, Date date) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.subject = subject;
        this.user = user;
        this.date = date;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
