package kg.iaau.edu.diploma.ist.entity;

import javax.persistence.*;

@Entity(name = "faculty")
public class Faculty{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "FACULTY_NAME")
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition="TEXT")
    private String description;

    @Column(name = "FACULTY_ACTIVE")
    private Boolean active;

    public Faculty(){
    }
    public Faculty(long id, String name, String description, Boolean active){
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

}