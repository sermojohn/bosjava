package com.bankrs.bosjava.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BankAccess {
    private String ID;
    private String name;
    private boolean enabled;
    private boolean authPossible;
    private String providerId;
    private Account[] accounts;
    private AccessCapabilities capabilities;
    private Beneficiary[] beneficiaries;
    private String consentExpiration;


    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Account {
        private long ID;
        private String providerId;
        private String name;
        private String type;
        private String number;
        private String balance;
        private String balanceDate;
        private String availableBalance;
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
