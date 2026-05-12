package com.example.vidaplus.infrastructure.user.repository;

import com.example.vidaplus.infrastructure.user.UserEntity;
import com.example.vidaplus.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserRepositoryHandler {

    private final UserRepository repository;

    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void save(UserEntity entity){
        repository.save(entity);
    }

}
