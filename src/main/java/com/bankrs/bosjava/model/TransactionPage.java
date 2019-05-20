package com.bankrs.bosjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TransactionPage {
    private int total, limit, offset;
    private Transaction[] data;

    @Data
    public static class Transaction {
        private long id;
        @JsonProperty("user_bank_account_id")
        private long accountId;
        @JsonProperty("category_id")
        private long categoryId;
        @JsonProperty("repeated_transaction_id")
        private long repeatedTransactionId;
        private long remoteId;
        @JsonProperty("entry_date")
        private ZonedDateTime entryDate;
        @JsonProperty("settlement_date")
        private ZonedDateTime SettlementDate;
        private Counterparty counterparty;
        private Amount amount;
        @JsonProperty("original_amount")
        private Amount originalAmount;
        private String usage;
        @JsonProperty("transaction_type")
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
    public static class AccountRef {
        @JsonProperty("provider_id")
        private String providerId;
        private String IBAN;
        private String label;
    }
}
