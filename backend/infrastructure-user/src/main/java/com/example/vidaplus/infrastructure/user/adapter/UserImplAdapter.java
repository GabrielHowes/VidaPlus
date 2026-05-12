package com.example.vidaplus.infrastructure.user.adapter;

import com.example.vidaplus.domain.user.model.RegisterInput;
import com.example.vidaplus.domain.user.model.TokenDto;
import com.example.vidaplus.domain.user.model.UserDto;
import com.example.vidaplus.domain.user.model.UserInput;
import com.example.vidaplus.domain.user.port.UserSpiPort;
import com.example.vidaplus.infrastructure.administrator.repository.AdministratorRepositoryHandler;
import com.example.vidaplus.infrastructure.medic.repository.MedicRepositoryHandler;
import com.example.vidaplus.infrastructure.patient.repository.PatientRepositoryHandler;
import com.example.vidaplus.infrastructure.user.repository.UserRepositoryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserImplAdapter implements UserSpiPort {

    private final UserRepositoryHandler repositoryHandler;

    @Value("${keycloak.token-uri}")
    private String keycloakTokenUri;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.admin-uri}")
    private String keycloakAdminUri;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.scope}")
    private String scope;

    private final MedicRepositoryHandler medicRepository;
    private final AdministratorRepositoryHandler adminRepository;
    private final PatientRepositoryHandler patientRepository;

    private final UserMapper mapper;

    private final RestClient restClient = RestClient.create();

    @Override
    public UserDto login(UserInput input) {
        repositoryHandler.findByUsername(input.username())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado na base local"));

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "password");
        formData.add("username", input.username());
        formData.add("password", input.password());
        formData.add("scope", scope);

        try {
            TokenDto response = restClient.post()
                    .uri(keycloakTokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(TokenDto.class);

            if (response != null) {
                return mapper.toUserDto(response);
            }
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new RuntimeException("Credenciais inválidas no Keycloak");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar login no Keycloak: " + e.getMessage());
        }

        throw new RuntimeException("Falha ao recuperar token de acesso");
    }

    @Override
    public void register(RegisterInput input) {
        String adminToken = getServiceAccountToken();

        Map<String, Object> keycloakUser = new HashMap<>();
        keycloakUser.put("username", input.username());
        keycloakUser.put("email", input.email());
        keycloakUser.put("firstName", input.name());
        keycloakUser.put("lastName", "");
        keycloakUser.put("enabled", true);
        keycloakUser.put("emailVerified", true);
        keycloakUser.put("credentials", List.of(
                Map.of(
                        "type", "password",
                        "value", input.password(),
                        "temporary", false
                )
        ));

        ResponseEntity<Void> response = restClient.post()
                .uri(keycloakAdminUri)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(keycloakUser)
                .retrieve()
                .toBodilessEntity();

        String location = response.getHeaders().getFirst("Location");
        String keycloakUserId = location.substring(location.lastIndexOf("/") + 1);

        String role = Optional.ofNullable(input.credentialInfo()).map(value -> {
            if (value.containsKey("crm") && value.containsKey("cpf")) {
                this.medicRepository.save(mapper.toMedicEntity(input));
                return "medic";
            }
            if (value.containsKey("cardId") && value.containsKey("cpf")) {
                this.adminRepository.save(mapper.toAdminEntity(input));
                return "admin";
            }
            if (value.containsKey("cpf")) {
                this.patientRepository.save(mapper.toPatientEntity(input));
                return "user";
            }
           return null;
        }).orElseThrow(() -> new RuntimeException("error"));

        assignRole(adminToken, keycloakUserId, role);

        repositoryHandler.save(
                mapper.toUserEntity(input)
        );
    }

    private String getServiceAccountToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("grant_type", "client_credentials");

        TokenDto token = restClient.post()
                .uri(keycloakTokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(TokenDto.class);

        return token.accessToken();
    }

    private void assignRole(String adminToken, String userId, String roleName) {
        String realmBase = keycloakAdminUri.replace("/users", "");
        String roleUri = realmBase + "/roles/" + roleName;
        String mappingUri = keycloakAdminUri + "/" + userId + "/role-mappings/realm";

        log.info("GET role URI: {}", roleUri);
        log.info("POST mapping URI: {}", mappingUri);

        Map<?, ?> role = restClient.get()
                .uri(roleUri)
                .header("Authorization", "Bearer " + adminToken)
                .retrieve()
                .body(Map.class);

        log.info("Role encontrada: {}", role);

        restClient.post()
                .uri(mappingUri)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(List.of(role))
                .retrieve()
                .toBodilessEntity();
    }

}
