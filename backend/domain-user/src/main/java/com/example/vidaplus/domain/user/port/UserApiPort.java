package com.example.vidaplus.domain.user.port;

import com.example.vidaplus.domain.user.model.RegisterInput;
import com.example.vidaplus.domain.user.model.UserDto;
import com.example.vidaplus.domain.user.model.UserInput;


public interface UserApiPort {

    UserDto login(UserInput input);

    void register(RegisterInput request);

}
