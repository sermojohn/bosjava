package com.bankrs.bosjava;

import com.bankrs.bosjava.model.UserLoginParams;
import com.bankrs.bosjava.model.UserLoginResponse;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.base.CharMatcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.core.AllOf.*;
import static org.junit.Assert.assertThat;

public class ClientTest {

    public static final String USER_AGENT = "bosjava/tests";
    public static final Integer MOCK_SERVER_PORT = 8089;
    public static final String MOCK_SERVER_URL = "http://localhost:"+MOCK_SERVER_PORT+"/";

    public static final String MOCK_USER_NAME = "user@bankrs.com";
    public static final String MOCK_USER_PASSWORD = "1234";
    public static final String MOCK_APPLICATION_ID = "1234";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(MOCK_SERVER_PORT); // No-args constructor defaults to port 8080

    @Test
    public void testUserLogin() {
        UserLoginResponse response =
            Client.newClient(MOCK_SERVER_URL, USER_AGENT)
                .newAppClient(MOCK_APPLICATION_ID)
                    .loginUser(UserLoginParams.builder()
                            .username(MOCK_USER_NAME)
                            .password(MOCK_USER_PASSWORD)
                            .build())
                    .block();

        System.out.println("token: " + response.getToken());

        assertThat(response, allOf(IsNull.notNullValue()));
        assertThat(response.getToken(), allOf(IsNot.not(""), IsNull.notNullValue()));
    }
}
