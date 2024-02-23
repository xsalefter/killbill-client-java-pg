package com.xsalefter.killbill.client.playground.cases;

import com.xsalefter.killbill.client.playground.Playground;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.CatalogApi;
import org.killbill.billing.client.model.Catalogs;
import org.killbill.billing.client.model.gen.CatalogValidation;
import org.killbill.billing.client.model.gen.CatalogValidationError;

import java.util.List;

public class GetCatalogNeverNullTest extends Playground {

    private RequestOptions requestOptions() {
        return RequestOptions.builder()
                .withCreatedBy("get-catalog-never-null-tester")
                .build();
    }

    private void createSpyCarAdvanced(final String fileName) throws KillBillClientException {
        final CatalogApi api = new CatalogApi(newHttpClient());
        final String xml = getXmlContent("/catalog/cases/" + fileName);
        final CatalogValidation validation = api.validateCatalogXml(xml, requestOptions());
        final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        } else {
            api.uploadCatalogXml(xml, requestOptions());
        }
    }

    @Test
    void createSpyCarAdvanced1() throws KillBillClientException {
        createSpyCarAdvanced("GetCatalogNeverNullTest1.xml");
    }

    @Test
    void createSpyCarAdvanced2() throws KillBillClientException {
        createSpyCarAdvanced("GetCatalogNeverNullTest2.xml");
    }

    @Test
    void getCatalogXML() throws KillBillClientException {
        final DateTime catalogDate = DateTime.parse("2013-02-20T00:00:00Z");
        final CatalogApi api = new CatalogApi(newHttpClient());
        final String catalog = api.getCatalogXml(catalogDate, null, requestOptions());
        System.out.println("catalog = " + catalog);
    }

    @Test
    void getCatalogJSON() throws KillBillClientException {
        final DateTime catalogDate = DateTime.parse("2000-01-01T00:00:00+00:00");
        final CatalogApi api = new CatalogApi(newHttpClient());
        final Catalogs catalogs = api.getCatalogJson(catalogDate, null, requestOptions());
        System.out.println("catalog length = " + catalogs.size());
    }
}
