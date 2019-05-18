package com.bankrs.bosjava.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListTransactionsReq {
    private String clientId;
    private long accessId;
    private int limit;
    private int offset;
    private String since; //RFC3339
}
