package com.lingogame.game.repo;

import com.lingogame.game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Game, Integer> {
    @Query(nativeQuery=true, value="SELECT * FROM game t WHERE t.id = ?1")
    Game findGameId(int id);
}
