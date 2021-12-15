package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.repo.TurnRepo;
import com.lingogame.game.webservices.TurnResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        List<Turn>turns = turnRepo.findAllTurnsById(id);
        System.out.println(turns.size());
        return turns;
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
                score = gameService.findGameScoreId(turn.getGame().getId()).getScore();
                score +=100;
                Game g = new Game(turn.getGame().getId(), score, "in progress");
                gameService.updateGame(g);
                randomword = wordService.returnRandomWord((randomword.length()+1));
                String firstLetter = wordService.ReturnFirstChar(numberOfLines);
                Turn t = new Turn();
                t.setRandomWord(randomword);
                t.setHint(firstLetter);
                t.setMistakes(0);
                return t;
            } else {//als het niet in een keer goed geraden is
                turn.setHint(checkGuessedChars(turn));
                turn.setFeedback(charFeedback);
                turn.setMistakes(turn.getMistakes()+1);
                addRound(turn);
            }
            return turn; // return turn object.
        } else {
            Game game = new Game(turn.getGame().getId(), score, "finished");
            gameService.updateGame(game);
            return turn;
        }
    }

    public String checkGuessedChars(Turn turn) {
        int counter = 0;
        String stringCorrecteChars = "";
        if (randomword.length() != turn.getGuessedWord().length()) {
            for (char letter: randomword.toCharArray()) {
                charFeedback += "invalid, ";
            }
            stringCorrecteChars= "Het gerade woord is niet de juiste lengte";
        } else {
            for (char letter : randomword.toCharArray()) {
                char currentChar = turn.getGuessedWord().charAt(counter);//het karakter dat we nu checken
                if (letter == turn.getGuessedWord().charAt(counter)) {//als de letters op de goede plek staan
                    charFeedback += "correct, ";
                    stringCorrecteChars += letter;
                } else if (letter != turn.getGuessedWord().charAt(counter)) {//als de letters niet op de goede plek staan
                    stringCorrecteChars = checkCharPresentOrAbsent(currentChar, stringCorrecteChars, turn.getRandomWord());
                }
                counter++;
            }
            String real = compareHint(stringCorrecteChars, turn);
            stringCorrecteChars = real;
        }
        System.out.println(stringCorrecteChars);
        return stringCorrecteChars;
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

    public String checkCharPresentOrAbsent(char currentChar, String stringCorrectChars, String randomwoord) {
        int currentCharOccuranceInRandomWord = checkCharOccurencesInRandomWord(currentChar, stringCorrectChars, randomwoord);
        if ((randomwoord.indexOf(currentChar)) >= 0) {//als de letter een positie groter dan 0 heeft oftewel erin zit
            //check of het letter al geweest is
            if ((stringCorrectChars.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
                charFeedback += "present, ";
                System.out.println(currentChar + " " + charFeedback + " deze letter zit er zovaak in " + currentCharOccuranceInRandomWord);// letter is al geweest maar niet correct
            } else {//als de letter nog niet al gekozen is
                charFeedback += "present, ";
                System.out.println(currentChar + " " + charFeedback + " deze letter zit niet op de goede plek");
            }
        } else {
            charFeedback += "absent, ";
            System.out.println(currentChar + " " + charFeedback + " deze letter zit  er niet in");
        }
        stringCorrectChars += '_';
        return stringCorrectChars;
    }

    public int checkCharOccurencesInRandomWord(char currentChar, String stringCorrectChars, String randomwoord) {
        int index = randomwoord.indexOf(currentChar);
        int currentCharOccuranceInRandomWord = 0;
        if ((stringCorrectChars.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
            while (index >= 0) {//check hoevaak de letter voorkomt
                index = randomwoord.indexOf(currentChar, index + 1);
                currentCharOccuranceInRandomWord += 1;
            }
        }
        return currentCharOccuranceInRandomWord;
    }

}
