package kg.iaau.edu.diploma.ist.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "exam_question")
public class ExamQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    private TestExam testExam;

    @Column(name = "QUESTION", columnDefinition="TEXT")
    private String question;

    @Column(name = "FIRST_ANSWER")
    private String firstAnswer;

    @Column(name = "SECOND_ANSWER")
    private String secondAnswer;

    @Column(name = "THIRD_ANSWER")
    private String thirdAnswer;

    @Column(name = "FOURTH_ANSWER")
    private String fourthAnswer;

    @Column(name = "CORRECT_ANSWER")
    private String correctAnswer;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User user;

    @Column(name = "CREATED_DATE")
    private Date date;

    public ExamQuestion() {
    }

    public ExamQuestion(TestExam testExam, String question, String firstAnswer,
                        String secondAnswer, String thirdAnswer, String fourthAnswer,
                        String correctAnswer, User user, Date date) {
        this.testExam = testExam;
        this.question = question;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.correctAnswer = correctAnswer;
        this.user = user;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TestExam getTestExam() {
        return testExam;
    }

    public void setTestExam(TestExam testExam) {
        this.testExam = testExam;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
