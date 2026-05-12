package com.example.vidaplus.infrastructure.user.adapter;

import com.example.vidaplus.domain.user.model.RegisterInput;
import com.example.vidaplus.domain.user.model.TokenDto;
import com.example.vidaplus.domain.user.model.UserDto;
import com.example.vidaplus.infrastructure.administrator.entity.AdministratorEntity;
import com.example.vidaplus.infrastructure.medic.entity.MedicEntity;
import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import com.example.vidaplus.infrastructure.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.util.Map;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(TokenDto response);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", source = "role")
    UserEntity toUserEntity(RegisterInput input);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "cpf",    source = "credentialInfo", qualifiedByName = "extractCpf")
    @Mapping(target = "crm", source = "credentialInfo", qualifiedByName = "extractCrm")
    MedicEntity toMedicEntity(RegisterInput input);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "cpf",    source = "credentialInfo", qualifiedByName = "extractCpf")
    PatientEntity toPatientEntity(RegisterInput input);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "cpf",    source = "credentialInfo", qualifiedByName = "extractCpf")
    @Mapping(target = "cardId", source = "credentialInfo", qualifiedByName = "extractCardId")
    AdministratorEntity toAdminEntity(RegisterInput input);

    @Named("extractCpf")
    default String extractCpf(Map<String, Object> credentials) {
        return (String) credentials.get("cpf");
    }

    @Named("extractCrm")
    default String extractCrm(Map<String, Object> credentials) {
        return (String) credentials.get("crm");
    }

    @Named("extractCardId")
    default String extractCardId(Map<String, Object> credentials) {
        return (String) credentials.get("cardId");
    }

}
