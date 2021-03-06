package com.ownblog.caelog.pojo;

public class User {
    private int id;
    private String nickname;
    private String password_;
    private String introduction;
    private String backimgsrc;
    private String securityQuestion;
    private String securityAnswer;
    private String securityEmail;
    private int backgroundColorSelect;

    public User(int id, String nickname, String password_, String introduction, String backimgsrc, String securityQuestion, String securityAnswer, String securityEmail, int backgroundColorSelect) {
        this.id = id;
        this.nickname = nickname;
        this.password_ = password_;
        this.introduction = introduction;
        this.backimgsrc = backimgsrc;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.securityEmail = securityEmail;
        this.backgroundColorSelect = backgroundColorSelect;
    }

    public String getPassword_() {
        return password_;
    }

    public void setPassword_(String password_) {
        this.password_ = password_;
    }

    public int getBackgroundColorSelect() {
        return backgroundColorSelect;
    }

    public void setBackgroundColorSelect(int backgroundColorSelect) {
        this.backgroundColorSelect = backgroundColorSelect;
    }

    public User(String nickname, String password_){
        this.nickname=nickname;
        this.password_=password_;
    }

    public User(int id, String nickname, String password, String introduction, String backimgsrc, String securityQuestion, String securityAnswer, String securityEmail) {
        this.id = id;
        this.nickname = nickname;
        this.password_ = password;
        this.introduction = introduction;
        this.backimgsrc = backimgsrc;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.securityEmail = securityEmail;
    }

    public User() {
    }

    public User(int id, String nickname, String password, String introduction,String backimgsrc) {
        this.id = id;
        this.nickname = nickname;
        this.password_ = password;
        this.backimgsrc = backimgsrc;
        this.introduction=introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password_;
    }

    public void setPassword(String password) {
        this.password_ = password;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBackimgsrc() {
        return backimgsrc;
    }

    public void setBackimgsrc(String backimgsrc) {
        this.backimgsrc = backimgsrc;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityEmail() {
        return securityEmail;
    }

    public void setSecurityEmail(String securityEmail) {
        this.securityEmail = securityEmail;
    }

    public void hiddenforsafety(){
        this.password_="******";
        this.securityAnswer="******";
        this.securityEmail="******";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password_ + '\'' +
                ", introduction='" + introduction + '\'' +
                ", backimgsrc='" + backimgsrc + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", securityEmail='" + securityEmail + '\'' +
                '}';
    }
}
