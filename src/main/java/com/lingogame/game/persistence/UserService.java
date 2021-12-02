package com.lingogame.game.persistence;

import com.lingogame.game.domain.User;
import com.lingogame.game.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User createNew(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findById(User user){
        return userRepo.findById(user.getId());
    }
}
