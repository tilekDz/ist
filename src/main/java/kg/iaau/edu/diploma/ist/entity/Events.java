package kg.iaau.edu.diploma.ist.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition="TEXT")
    private String description;

    @Lob
    @Column(name = "IMAGE")
    private byte[] image;

    @Column(name = "EVENT_DATE")
    private Date eventDate;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    public Events() {
    }

    public Events(long id, String title, String description, byte[] image, Date eventDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.eventDate = eventDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
