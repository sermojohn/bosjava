package com.bankrs.bosjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BankAccess {
    private String ID;
    private String name;
    private boolean enabled;
    private boolean authPossible;
    @JsonProperty("provider_id")
    private String providerId;
    private Account[] accounts;
    private AccessCapabilities capabilities;
    private Beneficiary[] beneficiaries;
    private String consentExpiration;


    @Data
    public static class Account {
        private long ID;
        @JsonProperty("provider_id")
        private String providerId;
        private String name;
        private String type;
        private String number;
        private String balance;
        private String balanceDate;
        @JsonProperty("available_balance")
        private String availableBalance;
        @JsonProperty("credit_line")
        private String creditLine;
        private boolean removed;
        private String currency;
        private String IBAN;
        private String alias;
        private AccountCapabilities capabilities;
        private String bin;
        private long[] beneficiaries;
    }

    @Data
    public static class AccessCapabilities {

    }

    @Data
    public static class Beneficiary {

    }

    @Data
    public static class AccountCapabilities {

    }
}
