package com.xsalefter.killbill.client.playground.catalog.basic;

import com.xsalefter.killbill.client.playground.Playground;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.CatalogApi;
import org.killbill.billing.client.api.gen.TenantApi;
import org.killbill.billing.client.model.gen.CatalogValidation;
import org.killbill.billing.client.model.gen.CatalogValidationError;
import org.killbill.billing.client.model.gen.Tenant;

import java.util.List;
import java.util.UUID;

public class BasicCatalogTest extends Playground {

    private RequestOptions requestOptions() {
        return RequestOptions.builder()
                .withCreatedBy("basic-catalog-tester")
                .build();
    }

    Tenant getTenant() throws KillBillClientException {
        final UUID tenantId = UUID.fromString("de5e838a-af34-4a43-8c7b-9601b73e7b74");
        final KillBillHttpClient httpClient = newHttpClient();
        final TenantApi tenantApi = new TenantApi(httpClient);
        return tenantApi.getTenant(tenantId, requestOptions());
    }

    @Test
    void createEmptyCatalog() {
        final String content = getXmlContent("/catalog/basic/MinimumCatalog_01.xml");
        try {
            final CatalogApi api = new CatalogApi(newHttpClient());
            final CatalogValidation validation = api.validateCatalogXml(content, requestOptions());
            final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
            if (!errors.isEmpty()) {
                errors.forEach(System.out::println);
            } else {
                api.uploadCatalogXml(content, requestOptions());
            }
        } catch (KillBillClientException e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }
}
