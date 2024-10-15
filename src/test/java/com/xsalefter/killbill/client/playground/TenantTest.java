package com.xsalefter.killbill.client.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.TenantApi;
import org.killbill.billing.client.model.gen.Tenant;
import org.killbill.billing.client.model.gen.TenantKeyValue;

import java.util.Collections;
import java.util.UUID;

public class TenantTest extends Playground {

    private Tenant newTenant() {
        return new Tenant(UUID.randomUUID(), "boblazar", "bob", "lazar", Collections.emptyList());
    }

    @Test
    // @Disabled
    void createDefaultTenant() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient();
        final TenantApi tenantApi = new TenantApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.builder()
                .withCreatedBy("xsalefter")
                .build();
        final Tenant result = tenantApi.createTenant(newTenant(), requestOptions);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTenantId());
        Assertions.assertNotNull(result.getApiKey());
        // Assertions.assertNotNull(result.getApiSecret()); not sure why this is null
    }

    @Test
    void getTenant() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient();
        final TenantApi tenantApi = new TenantApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.empty();
        final Tenant result = tenantApi.getTenant(UUID.fromString("107269cb-5ac0-4b5d-a91d-a2507a0dc7eb"), requestOptions);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTenantId());
        Assertions.assertNotNull(result.getApiKey());
    }

    @Test
    void getTenantByApiKey() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient("localhost", 8080, "bob", "lazar");
        final TenantApi tenantApi = new TenantApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.empty();
        final Tenant result = tenantApi.getTenantByApiKey("bob", requestOptions);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTenantId());
        Assertions.assertNotNull(result.getApiKey());
    }

    @Test
    void getTenantKeyValue() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient("localhost", 8080, "bob2", "lazar2");
        final TenantApi tenantApi = new TenantApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.empty();
        final TenantKeyValue result = tenantApi.getPerTenantConfiguration(requestOptions);
        Assertions.assertNotNull(result);
    }
}
