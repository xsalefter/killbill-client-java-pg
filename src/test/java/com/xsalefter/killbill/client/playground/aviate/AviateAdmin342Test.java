package com.xsalefter.killbill.client.playground.aviate;

import com.xsalefter.killbill.client.playground.Playground;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.CatalogApi;
import org.killbill.billing.client.model.gen.CatalogValidation;
import org.killbill.billing.client.model.gen.CatalogValidationError;

import java.util.List;

public class AviateAdmin342Test extends Playground {

    private final String createdBy = apiKey.concat("xsalefter@demo");

    public AviateAdmin342Test() {
    }

    private RequestOptions requestOptions() {
        return RequestOptions.builder()
                .withCreatedBy(createdBy)
                .withTenantApiKey(apiKey)
                .withTenantApiSecret(apiSecret)
                .build();
    }

    @Test
    void validateCatalog() {
        try {
            final String content = getXmlContent("/catalog/aviate/aviate-admin-342-adjusted.xml");
            // "CatalogValidation-v4-valid.xml" copy of
            // https://github.com/killbill/killbill/blob/master/beatrix/src/test/resources/catalogs/testCatalogValidation/CatalogValidation-v4-valid.xml
            // with some adjustment (explained in the file)
            // final String content = getXmlContent("/catalog/_references/CatalogValidation-v4-valid.xml");
            final CatalogApi api = new CatalogApi(newHttpClient());
            final CatalogValidation validation = api.validateCatalogXml(content, requestOptions());
            final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
            if (!errors.isEmpty()) {
                errors.forEach(System.out::println);
            } else {
                System.out.println("Success!");
            }
        } catch (KillBillClientException e) {
            System.out.println("e = " + e.getBillingException());
        }
    }

    @Test
    void uploadCatalog() {
        try {
            final String content = getXmlContent("/catalog/aviate/aviate-admin-342-adjusted.xml");
            // "CatalogValidation-v4-valid.xml" copy of
            // https://github.com/killbill/killbill/blob/master/beatrix/src/test/resources/catalogs/testCatalogValidation/CatalogValidation-v4-valid.xml
            // with some adjustment (explained in the file)
            // final String content = getXmlContent("/catalog/_references/CatalogValidation-v4-valid.xml");
            final CatalogApi api = new CatalogApi(newHttpClient());
            final String result = api.uploadCatalogXml(content, requestOptions());
            System.out.println("result = " + result);
        } catch (KillBillClientException e) {
            System.out.println("e = " + e.getBillingException());
        }
    }
}
