package com.lingogame.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "turn")
public class Turn {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String randomWord;
    private String guessedWord;
    private int mistakes;
    private String feedback;
    private String hint;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Turn(String randomWord, String guessedWord, int mistakes, Game game, String feedback, String hint){
        this.randomWord = randomWord;
        this.guessedWord = guessedWord;
        this.mistakes = mistakes;
        this.game = game;
        this.feedback =feedback;
        this.hint = hint;
    }

    public Turn() {

    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @JsonIgnore
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
    }

    public String getRandomWord() {
        return randomWord;
    }

    public void setRandomWord(String randomWord) {
        this.randomWord = randomWord;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
