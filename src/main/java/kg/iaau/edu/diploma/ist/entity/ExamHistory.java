package kg.iaau.edu.diploma.ist.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "exam_history")
public class ExamHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "TAKED_BY")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TEST_EXAM_ID")
    private TestExam testExam;

    private String result;

    @Column(name = "CREATED_DATE")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExamAnswers() {
        return examAnswers;
    }

    public void setExamAnswers(String examAnswers) {
        this.examAnswers = examAnswers;
    }

    private String examAnswers;

    public ExamHistory() {
    }

    public ExamHistory(long id, User user, TestExam testExam, String result) {
        this.id = id;
        this.user = user;
        this.testExam = testExam;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TestExam getTestExam() {
        return testExam;
    }

    public void setTestExam(TestExam testExam) {
        this.testExam = testExam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
