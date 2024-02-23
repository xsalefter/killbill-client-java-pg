package com.xsalefter.killbill.client.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.TenantApi;
import org.killbill.billing.client.model.gen.Tenant;

import java.util.Collections;
import java.util.UUID;

public class TenantTest extends Playground {

    private Tenant newTenant() {
        return new Tenant(UUID.randomUUID(), "EXT-123", "ext", "123", Collections.emptyList());
    }

    @Test
    @Disabled
    void createEmptyTenant() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient();
        final TenantApi tenantApi = new TenantApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.builder()
                .withCreatedBy("someone2")
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
        final Tenant result = tenantApi.getTenant(UUID.fromString("de5e838a-af34-4a43-8c7b-9601b73e7b74"), requestOptions);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTenantId());
        Assertions.assertNotNull(result.getApiKey());
    }

    @Test
    void getTenantByApiKey() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient("34.229.49.215", 8080, "bob", "lazar");
        final TenantApi tenantApi = new TenantApi(httpClient);
        final RequestOptions requestOptions = RequestOptions.empty();
        final Tenant result = tenantApi.getTenantByApiKey("bob", requestOptions);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTenantId());
        Assertions.assertNotNull(result.getApiKey());
    }
}
