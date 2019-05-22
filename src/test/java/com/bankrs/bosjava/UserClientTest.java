package com.bankrs.bosjava;

import com.bankrs.bosjava.model.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.bankrs.bosjava.ClientTest.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class UserClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(MOCK_SERVER_PORT); // No-args constructor defaults to port 8080

    public UserClient initUserClient() {
        UserLoginParams params =
                UserLoginParams.builder()
                        .username(MOCK_USER_NAME)
                        .password(MOCK_USER_PASSWORD)
                        .build();

        AppClient appClient = Client.newClient(MOCK_SERVER_URL, USER_AGENT)
                .newAppClient(MOCK_APPLICATION_ID);

        UserLoginResponse response =
                appClient.loginUser(params).block();

        return appClient.newUserClient(response.getToken());
    }


    @Test
    public void testAccesses() {
        BankAccess access = initUserClient()
                .accessService()
                .getAccess(2)
                .block();

        assertThat(access, IsNull.notNullValue());
        assertThat(access.getAccounts()[0].getName(), Is.is("Girokonto"));
        assertThat(access.getAccounts().length, Is.is(1));
    }

    @Test
    @Ignore
    public void testListTransactions() {
        final long accessId = 2;
        final int limit = 10;
        final UserClient userClient = initUserClient();
        TransactionPage firstPage = userClient.transactionService()
                .list(
                        ListTransactionsReq.builder()
                                .accessId(accessId)
                                .limit(0)
                                .offset(0)
                                .build()
                )
                .block();
        int pages = (int) (Math.floor(firstPage.getTotal() / limit) +
                (Math.ceil(firstPage.getTotal() % limit) > 0 ? 1 : 0));

        List<TransactionPage.Transaction> trxs = IntStream.rangeClosed(1, pages)
                .mapToObj(i -> {
                            Assert.assertTrue((i * limit) < firstPage.getTotal() + limit);
                            return Arrays.stream(userClient.transactionService()
                                    .list(ListTransactionsReq.builder()
                                            .accessId(accessId)
                                            .limit(limit)
                                            .offset((i - 1) * limit)
                                            .build()
                                    )
                                    .block()
                                    .getData());
                        }
                )
                .flatMap(Function.identity())
                .collect(Collectors.toList());

        Assert.assertTrue("expected transactions should equal retrieved transactions",
                firstPage.getTotal() == trxs.size());


    }

    @Test
    @Ignore
    public void testListRepeatedTransactions() {
        final long accessId = 2;
        RepeatedTransactionPage repTrxs = initUserClient().repeatedTransactionsService()
                .list(ListTransactionsReq.builder()
                        .accessId(accessId)
                        .build())
                .block();

        Assert.assertTrue("expected 1 repeated transactions", repTrxs.getTotal() == 1);
    }

    @Test
    @Ignore
    public void testListScheduledTransactions() {
        final long accessId = 2;
        TransactionPage.Transaction[] schTrxs = initUserClient().scheduledTransactionsService()
                .list(ListScheduledTransactionsReq.builder()
                        .accessId(accessId)
                        .build())
                .block();

        Assert.assertTrue("expected 0 scheduled transactions", schTrxs.length == 0);
    }


}
