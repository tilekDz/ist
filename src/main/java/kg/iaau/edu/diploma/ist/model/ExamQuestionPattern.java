package kg.iaau.edu.diploma.ist.model;

public class ExamQuestionPattern {
    private String question;
    private String correctAnswer;

    public ExamQuestionPattern() {
    }

    public ExamQuestionPattern(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
