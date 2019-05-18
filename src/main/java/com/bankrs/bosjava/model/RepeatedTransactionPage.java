package com.bankrs.bosjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class RepeatedTransactionPage {
    private int total, limit, offset;
    private RepeatedTransaction[] data;

    @Data
    public static class RepeatedTransaction {
        private long id;
        @JsonProperty("user_bank_account_id")
        private long accountId;
        @JsonProperty("user_account")
        private AccountRef userAccount;
        @JsonProperty("remote_account")
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
        @JsonProperty("provider_id")
        private String providerId;
        private String IBAN;
        private String label;
    }

    @Data
    public static class RecurringRule {
        private ZonedDateTime start;
        private ZonedDateTime until;
        private Frequency frequency;
        private int interval;
        @JsonProperty("by_day")
        private int byDay;
    }

    public static enum Frequency {
        once, daily, weekly, monthly, yearly;
    }
}
