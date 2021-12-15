package com.lingogame.game.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="WORD")
public class Word implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String value;
    private int length;

    @ManyToOne
    @JoinColumn(name="turnid")
    private Turn turn;

    public Word(String value){
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

}
