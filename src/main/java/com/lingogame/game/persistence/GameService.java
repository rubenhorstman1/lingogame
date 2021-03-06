package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.repo.GameRepo;
import com.lingogame.game.repo.TurnRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepo gameRepo;
    private final TurnRepo turnRepo;

    @Autowired
    public GameService(GameRepo gamerepo, TurnRepo turnRepo) {
        this.gameRepo = gamerepo;
        this.turnRepo = turnRepo;
    }

    public Game addGame(Game game){
        return gameRepo.save(game);
    }

    public Game findGameId(int id){
        Game g = gameRepo.findGameId(id);
        List<Turn> t = turnRepo.findAllTurnsById(g.getId());
        System.out.println(t.size());
        g.setTurns(t);
        return g;
    }

    public Game createGame() {
        Game game = new Game(0,"in progress");
        return gameRepo.save(game);
    }

    public Game updateGame(Game game){
        return gameRepo.save(game);
    }

}
