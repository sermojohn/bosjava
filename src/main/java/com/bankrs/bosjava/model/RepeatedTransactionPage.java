package com.bankrs.bosjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class RepeatedTransactionPage {
    private int total, limit, offset;
    private RepeatedTransaction[] data;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class RepeatedTransaction {
        private long id;
        @JsonProperty("user_bank_account_id")
        private long accountId;
        private AccountRef userAccount;
        private AccountRef remoteAccount;
        private RecurringRule schedule;
        private Amount amount;
        private String usage;
    }

    @Data
    public static class Amount {
        private String value, currency;
    }

    @Data
    public static class AccountRef {
        private String providerId;
        private String IBAN;
        private String label;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class RecurringRule {
        private ZonedDateTime start;
        private ZonedDateTime until;
        private Frequency frequency;
        private int interval;
        private int byDay;
    }

    public static enum Frequency {
        once, daily, weekly, monthly, yearly;
    }
}
