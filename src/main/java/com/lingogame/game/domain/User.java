package com.lingogame.game.domain;

import javax.persistence.*;

@Entity(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToOne
    private Game game;

    public User() {

    }

    public User(Game game, String name) {
        this.game = game;
        this.name =name;
    }

    public User(Game game, String name, int id) {
        this.game = game;
        this.name =name;
        this.id  = id;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
