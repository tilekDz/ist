package kg.iaau.edu.diploma.ist.model;

public class NewsPattern {
    private String title;
    private String description;

    public NewsPattern() {
    }

    public NewsPattern(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
