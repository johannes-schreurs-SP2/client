package model;

public class UserAnswer {
    private int userId;
    private int surveyId;
    private int questionId;
    private Boolean answered;

    public UserAnswer() {

    }

    public UserAnswer(int userId, int surveyId, int questionId, Boolean answered) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.questionId = questionId;
        this.answered = answered;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setAnswerd(Boolean answered) {
        this.answered = answered;
    }

    public Boolean getAnswered() {
        return answered;
    }
}
