package com.example.vidaplus.controller.v1.user.mapper;

import com.example.vidaplus.controller.v1.user.request.RegisterRequest;
import com.example.vidaplus.controller.v1.user.request.UserRequest;
import com.example.vidaplus.domain.user.model.RegisterInput;
import com.example.vidaplus.domain.user.model.UserInput;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapperController {

    RegisterInput toInput(RegisterRequest request);
    UserInput toInput(UserRequest request);

}
