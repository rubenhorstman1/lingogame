package com.lingogame.game.repo;

import com.lingogame.game.domain.User;
import com.lingogame.game.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
