package com.lingogame.game.repo;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Game, Integer> {
    @Query(nativeQuery=true, value="SELECT * FROM game t WHERE t.id = ?1")
    Game findGameId(int id);
    @Query(nativeQuery=true, value="SELECT score FROM game t WHERE t.id = ?1")
    Game findGameScoreId(int id);
    @Modifying
    @Query(nativeQuery=true, value="UPDATE game g set g.score = game.getScore() WHERE g.id = ?2")
    Game updateG(Game game, int id);
}
