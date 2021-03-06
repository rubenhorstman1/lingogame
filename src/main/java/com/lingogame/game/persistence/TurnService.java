package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.repo.TurnRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.List;


@Service
@Transactional
public class TurnService {
    private final TurnRepo turnRepo;
    private WordService wordService;
    private GameService gameService;
    public int score = 0;
    String hint;
    String charFeedback;
    String randomword;

    @Autowired
    public TurnService(TurnRepo turnRepo, WordService wordService, GameService gameService) {
        this.turnRepo = turnRepo;
        this.wordService = wordService;
        this.gameService = gameService;
    }

    @Transactional
    public Turn addRound(Turn round){
        return turnRepo.save(round);
    }

    public String findRandomWordOnGameId(int id){
        return turnRepo.findRandomWordOnGameId(id);
    }

    public List<Turn> findRounds(Integer id){
        return turnRepo.findAllTurnsById(id);
    }

    public Turn correctGuessedChars(Turn turn, String rw) throws FileNotFoundException {
        randomword = rw;
        charFeedback = "";
        String numberOfLines = "";
        if (turn.getMistakes() < 5) {
            if (turn.getGuessedWord().equals(randomword)) {//als het in een keer goed is
                hint = "correct";
                turn.setFeedback(hint);
                turn.setHint(randomword);
                addRound(turn); //save turn object.
                if(turn.getGame().getState() == null){
                    score = gameService.findGameId(turn.getGame().getId()).getScore();
                }
                score +=100;
                Game g = new Game(turn.getGame().getId(), score, "in progress");
                gameService.updateGame(g);
                randomword = wordService.returnRandomWord((randomword.length()+1));
                String firstLetter = wordService.returnHint(numberOfLines);
                Turn t = new Turn();
                t.setRandomWord(randomword);
                t.setHint(firstLetter);
                t.setMistakes(0);
                t.setGame(g);
                return t;
            } else {
                turn.setHint(checkGuessedChars(turn));
                turn.setFeedback(charFeedback);
                turn.setMistakes(turn.getMistakes()+1);
                addRound(turn);
            }
            return turn; // return turn object.
        } else {
            Game game = new Game(turn.getGame().getId(), score, "finished");
            turn.setGame(game);
            gameService.updateGame(game);
            return turn;
        }
    }

    public String checkGuessedChars(Turn turn) {
        int counter = 0;
        String correctChars = "";
        if (turn.getRandomWord().length() != turn.getGuessedWord().length()) {
            for (char letter: turn.getGuessedWord().toCharArray()) {
                charFeedback = "Het gerade woord is niet de juiste lengte";
            }
            correctChars= turn.getHint();
        } else {
            for (char letter : turn.getRandomWord().toCharArray()) {
                char currentChar = turn.getGuessedWord().charAt(counter);
                if (letter == turn.getGuessedWord().charAt(counter)) {
                    charFeedback += "correct, ";
                    correctChars += letter;
                } else  {
                    correctChars = checkCharPresentOrAbsent(currentChar, correctChars, turn.getRandomWord());
                }
                counter++;
            }
            String real = compareHint(correctChars, turn);
            System.out.println(real);
            correctChars = real;
        }
        return correctChars;
    }

    public String compareHint(String hint, Turn turn){
        int counter = 0;
        String correcthint= "";
        for (char letter: hint.toCharArray()) {
            char currentChar = turn.getHint().charAt(counter);
            if(letter != '_'){
                correcthint += letter;
            }
            else if(currentChar != '_'){
                correcthint += currentChar;
            }
            else{
                correcthint += '_';
            }
            counter++;
        }
        return correcthint;
    }

    public String checkCharPresentOrAbsent(char currentChar, String correctChars, String randomwoord) {
        if ((randomwoord.indexOf(currentChar)) >= 0) {
            if ((correctChars.indexOf(currentChar)) >= 0) {
                charFeedback += "present, ";
            }
            else if(correctChars.indexOf(currentChar) < 0){
                charFeedback += "present, ";
            }
        } else {
            charFeedback += "absent, ";
        }
        correctChars += '_';
        return correctChars;
    }
}
