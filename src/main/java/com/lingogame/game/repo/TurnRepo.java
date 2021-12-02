package com.lingogame.game.repo;

import com.lingogame.game.domain.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnRepo extends JpaRepository<Turn, Integer> {
    @Query(nativeQuery=true, value="SELECT * FROM turn t WHERE t.game_id = ?1")
    List<Turn> findAllTurnsById(Integer id);
    @Query(nativeQuery=true, value="SELECT random_word FROM turn t WHERE t.game_id = ?1 order by length(random_word) desc limit 1")
    String findRandomWordOnGameId(int id);
}
