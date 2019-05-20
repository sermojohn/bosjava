package com.bankrs.bosjava.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListScheduledTransactionsReq {
    private String clientId;
    private long accessId;
}
