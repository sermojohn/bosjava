package com.bankrs.bosjava;

import com.bankrs.bosjava.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * UserClient is a client used for interacting with services in the context of
 * a registered application and a valid user session. It is safe for
 * concurrent use by multiple goroutines.
 */
@Slf4j
public class UserClient {

    private String token; // obtained after successful user login
    private WebClient wc;

    private AccessService accessService;
    private TransactionService transactionService;
    private ScheduledTransactionsService scheduledTransactionsService;
    private RepeatedTransactionsService repeatedTransactionsService;

    private UserClient() {}

    private void setToken(final String token) {
        this.wc = this.wc.mutate()
            .defaultHeader("x-token", token)
                .build();
        this.token = token;
    }

    protected static UserClient newUserClient(final WebClient wc, final String token) {
        UserClient uc = new UserClient();
        uc.wc = wc;
        uc.setToken(token);
        uc.accessService = new AccessService(uc.wc);
        uc.transactionService = new TransactionService(uc.wc);
        uc.scheduledTransactionsService = new ScheduledTransactionsService(uc.wc);
        uc.repeatedTransactionsService = new RepeatedTransactionsService(uc.wc);
        return uc;
    }

    public AccessService accessService() {
        return this.accessService;
    }

    public TransactionService transactionService() {
        return transactionService;
    }

    public ScheduledTransactionsService scheduledTransactionsService() {
        return scheduledTransactionsService;
    }

    public RepeatedTransactionsService repeatedTransactionsService() {
        return repeatedTransactionsService;
    }

    /**
     * AccessesService provides access to bank access related API services.
     */
    public static class AccessService {

        private WebClient wc;

        private AccessService(WebClient wc) {
            this.wc = wc;
        }

        public Mono<BankAccess[]> listAccesses() {
            return wc
                    .get()
                    .uri("/accesses")
                    .retrieve()
                    .bodyToMono(BankAccess[].class)
                    .doOnError(WebClientResponseException.class, e -> LOGGER.debug("list accesses failed with '{}'", e.getMessage()));

        }

        public Mono<BankAccess> getAccess(long id) {
            return wc
                    .get()
                    .uri("/accesses/" + id)
                    .retrieve()
                    .bodyToMono(BankAccess.class)
                    .doOnError(WebClientResponseException.class, e -> LOGGER.debug("get access failed with '{}'", e.getMessage()));
        }

    }

    /**
     * TransactionsService provides access to transaction related API services.
     */
    public static class TransactionService {

        private final WebClient wc;

        private TransactionService(WebClient wc) {
            this.wc = wc;
        }

        public Mono<TransactionPage> list(final ListTransactionsReq req) {
            MultiValueMap params = new LinkedMultiValueMap<>();
            if(req.getAccessId() != 0) {
                params.add("access_id", String.valueOf(req.getAccessId()));
            }
            if(req.getLimit() != 0) {
                params.add("limit", String.valueOf(req.getLimit()));
            }
            if(req.getOffset() != 0) {
                params.add("offset", String.valueOf(req.getOffset()));
            }
            if(!StringUtils.isEmpty(req.getSince())) {
                params.add("since", req.getSince());
            }

            return wc
                    .get()
                    .uri(b -> b.path("/transactionss").queryParams(params).build())
                    .header("X-Client-Id", req.getClientId())
                    .retrieve()
                    .bodyToMono(TransactionPage.class)
                    .doOnError(WebClientResponseException.class, e -> LOGGER.debug("list transactions failed with '{}'", e.getMessage()));
        }

    }

    /**
     * ScheduledTransactionsService provides access to scheduled transaction related API services.
     */
    public static class ScheduledTransactionsService {

        private final WebClient wc;

        public ScheduledTransactionsService(WebClient wc) {
            this.wc = wc;
        }

        public Mono<TransactionPage.Transaction[]> list(final ListScheduledTransactionsReq req) {
            MultiValueMap params = new LinkedMultiValueMap<>();
            if(req.getAccessId() != 0) {
                params.add("access_id", String.valueOf(req.getAccessId()));
            }

            return wc
                    .get()
                    .uri(b -> b.path("/scheduled_transactions").queryParams(params).build())
                    .header("X-Client-Id", req.getClientId())
                    .retrieve()
                    .bodyToMono(TransactionPage.Transaction[].class)
                    .doOnError(WebClientResponseException.class, e -> LOGGER.debug("list scheduled transactions failed with '{}'", e.getMessage()));
        }
    }

    /**
     * RepeatedTransactionsService provides access to repeated transaction related API services.
     */
    public static class RepeatedTransactionsService {

        private final WebClient wc;

        public RepeatedTransactionsService(WebClient wc) {
            this.wc = wc;
        }
        public Mono<RepeatedTransactionPage> list(final ListTransactionsReq req) {
            MultiValueMap params = new LinkedMultiValueMap<>();
            if(req.getAccessId() != 0) {
                params.add("access_id", String.valueOf(req.getAccessId()));
            }
            if(req.getLimit() != 0) {
                params.add("limit", String.valueOf(req.getLimit()));
            }
            if(req.getOffset() != 0) {
                params.add("offset", String.valueOf(req.getOffset()));
            }
            if(!StringUtils.isEmpty(req.getSince())) {
                params.add("since", req.getSince());
            }

            return wc
                    .get()
                    .uri(b -> b.path("/repeated_transactions").queryParams(params).build())
                    .header("X-Client-Id", req.getClientId())
                    .retrieve()
                    .bodyToMono(RepeatedTransactionPage.class)
                    .doOnError(WebClientResponseException.class, e -> LOGGER.debug("list repeated transactions failed with '{}'", e.getMessage()));
        }
    }
}
