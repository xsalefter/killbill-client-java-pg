package com.xsalefter.killbill.client.playground.aviate;

import com.xsalefter.killbill.client.playground.Playground;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.CatalogApi;
import org.killbill.billing.client.api.gen.TenantApi;
import org.killbill.billing.client.model.gen.CatalogValidation;
import org.killbill.billing.client.model.gen.CatalogValidationError;
import org.killbill.billing.client.model.gen.Tenant;

import java.util.List;

public class AviateAdmin375 extends Playground {

    private final String apiKey = "bob";
    private final String apiSecret = "lazar";
    private final String externalKey = apiKey.concat("-external-key");
    private final String createdBy = apiKey.concat("@xsalefter.io");

    private RequestOptions requestOptions() {
        return RequestOptions.builder()
                .withCreatedBy(createdBy)
                .withTenantApiKey(apiKey)
                .withTenantApiSecret(apiSecret)
                .build();
    }

    @Test
    void createAviateTenant() throws KillBillClientException {
        // Not using this class's HTTP client because it's not yet created.
        final TenantApi tenantApi = new TenantApi(newHttpClient());
        final Tenant tenant = new Tenant();
        tenant.setExternalKey(externalKey);
        tenant.setApiKey(apiKey);
        tenant.setApiSecret(apiSecret);
        final Tenant result = tenantApi.createTenant(tenant, requestOptions());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getTenantId());
        Assertions.assertNotNull(result.getApiKey());
    }

    @Test
    void createCatalog() throws KillBillClientException {
        final String content = getXmlContent("/catalog/aviate/aviate-admin-375-firearms.xml");

        final CatalogApi api = new CatalogApi(newHttpClient(apiKey, apiSecret));
        final CatalogValidation validation = api.validateCatalogXml(content, requestOptions());
        final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        } else {
            System.out.println("No error found.");
            // api.uploadCatalogXml(content, requestOptions());
        }
    }
}
