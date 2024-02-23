package com.xsalefter.killbill.client.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.AccountApi;
import org.killbill.billing.client.model.gen.Account;

import java.util.UUID;

public class AccountTest extends Playground {

    private Account newAccount() {
        final Account result = new Account();
        result.setName("resa");
        result.setEmail("resa@xsalefter.id");
        result.setNotes("resa @ bob/lazar");
        return result;
    }

    @Test
    void createAccount() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient();
        final AccountApi api = new AccountApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.builder()
                .withCreatedBy("someone")
                .build();

        final Account result = api.createAccount(newAccount(), requestOptions);

        System.out.println("New Account. ID = " + result.getAccountId());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getName());
        Assertions.assertEquals("resa", result.getName());
        Assertions.assertNotNull(result.getEmail());
    }

    @Test
    void getAccount() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient();
        final AccountApi api = new AccountApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.empty();

        final Account result = api.getAccount(UUID.fromString("98299c6d-a033-41f7-8dc1-785683198c5a"), requestOptions);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getName());
        Assertions.assertEquals("resa", result.getName());
        Assertions.assertNotNull(result.getEmail());
    }

    @Test
    void updateAccount() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient();
        final AccountApi api = new AccountApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.empty();

        final Account result = api.getAccount(UUID.fromString("965c6edc-2b47-43b7-820d-1a4baa60dfaa"), requestOptions);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getName());
        Assertions.assertEquals("new account", result.getName());
        Assertions.assertNotNull(result.getEmail());

        result.setEmail("newaccount002updated@mailinator.com");
        api.updateAccount(result.getAccountId(), result, RequestOptions.builder().withCreatedBy("xsalefter").build());

        final Account upToDate = api.getAccount(UUID.fromString("965c6edc-2b47-43b7-820d-1a4baa60dfaa"), requestOptions);

        Assertions.assertNotNull(upToDate);
        Assertions.assertNotNull(upToDate.getName());
        Assertions.assertEquals("newaccount002updated@mailinator.com", upToDate.getEmail());
    }
}
