package com.bankrs.bosjava;

import com.bankrs.bosjava.model.UserLoginParams;
import com.bankrs.bosjava.model.UserLoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


/**
 * AppClient is a client used for interacting with services in the context of
 * a registered application without a valid user or developer session. It is safe
 * for concurrent use by multiple threads.
 */
@Slf4j
public class AppClient {
    private String applicationId;
    private WebClient wc;

    protected static AppClient newAppClient(WebClient wc, final String applicationId) {
        AppClient ac = new AppClient();
        ac.wc = wc;
        ac.setApplicationId(applicationId);
        return ac;
    }

    private void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
        this.wc = this.wc.mutate()
                .defaultHeader("X-Application-Id", applicationId).build();
    }

    public Mono<UserLoginResponse> loginUser(final UserLoginParams params) {
        return this.wc
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromObject(params))
                .retrieve()
                .bodyToMono(UserLoginResponse.class)
                .doOnError(WebClientResponseException.class, e -> LOGGER.debug("login user failed with '{}'", e.getMessage()));
    }

    /**
     * Creates a UserClient with the supplied user token.
     */
    public UserClient newUserClient(final String token) {
        return UserClient.newUserClient(this.wc, token);
    }
}
