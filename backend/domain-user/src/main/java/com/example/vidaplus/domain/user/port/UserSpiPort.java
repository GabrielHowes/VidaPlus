package com.example.vidaplus.domain.user.port;

import com.example.vidaplus.domain.user.model.RegisterInput;
import com.example.vidaplus.domain.user.model.UserDto;
import com.example.vidaplus.domain.user.model.UserInput;


public interface UserSpiPort {

    UserDto login(UserInput input);

    void register(RegisterInput input);

}
