package kg.iaau.edu.diploma.ist.entity;

import javax.persistence.*;

@Entity(name = "department")
public class Department{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DEPARTMENT_NAME")
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition="TEXT")
    private String description;

    @Column(name = "DEPARTMENT_ACTIVE")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FACULTY_ID")
    private Faculty faculty;

    public Department(){
    }

    public Department(long id, String name, String description, Boolean active){
        this.id = id;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}