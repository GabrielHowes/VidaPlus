package com.example.vidaplus.domain.user;

import com.example.vidaplus.domain.user.model.RegisterInput;
import com.example.vidaplus.domain.user.model.UserDto;
import com.example.vidaplus.domain.user.model.UserInput;
import com.example.vidaplus.domain.user.port.UserApiPort;
import com.example.vidaplus.domain.user.port.UserSpiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserApiPort {

    private final UserSpiPort userSpiPort;

    @Override
    public UserDto login(UserInput input) {
        return userSpiPort.login(input);
    }

    @Override
    public void register(RegisterInput input) {
        userSpiPort.register(input);
    }
}
