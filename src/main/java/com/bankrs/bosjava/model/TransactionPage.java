package com.bankrs.bosjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TransactionPage {
    private int total, limit, offset;
    private Transaction[] data;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Transaction {
        private long id;
        @JsonProperty("user_bank_account_id")
        private long accountId;
        private long categoryId;
        private long repeatedTransactionId;
        private long remoteId;
        private ZonedDateTime entryDate;
        private ZonedDateTime SettlementDate;
        private Counterparty counterparty;
        private Amount amount;
        private Amount originalAmount;
        private String usage;
        private String transactionType;
        private String gvcode;
    }

    @Data
    public static class Amount {
        private String value, currency;
    }

    @Data
    public static class OriginalAmount {
        private Amount value;
        private String exchangeRate;
    }

    @Data
    public static class Counterparty {
        private String name;
        private AccountRef account;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class AccountRef {
        @JsonProperty("provider_id")
        private String providerId;
        private String IBAN;
        private String label;
    }
}
