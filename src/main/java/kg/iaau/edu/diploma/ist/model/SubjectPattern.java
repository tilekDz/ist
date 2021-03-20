package kg.iaau.edu.diploma.ist.model;

public class SubjectPattern {
    private String name;
    private String description;

    public SubjectPattern() {
    }

    public SubjectPattern(String name, String description) {
        this.name = name;
        this.description = description;
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
}
