package com.xsalefter.killbill.client.playground.cases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.xsalefter.killbill.client.playground.Playground;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.CatalogApi;
import org.killbill.billing.client.api.gen.TenantApi;
import org.killbill.billing.client.model.Catalogs;
import org.killbill.billing.client.model.gen.CatalogValidation;
import org.killbill.billing.client.model.gen.CatalogValidationError;
import org.killbill.billing.client.model.gen.Tenant;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CorrectBillingPeriodLocationTest extends Playground {

    private static final String TENANT_KEY = "billing-period-location-tester";

    private RequestOptions requestOptions() {
        return RequestOptions.builder()
                .withCreatedBy(TENANT_KEY)
                .withTenantApiKey(TENANT_KEY)
                .withTenantApiSecret(TENANT_KEY)
                .build();
    }

    private String nowAsString() {
        return DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    private KillBillHttpClient httpClient() {
        return newHttpClient("localhost", 8080, "bob", "lazar");
    }

    @Test
    void createTenantIfNotExist() throws KillBillClientException {
        final KillBillHttpClient httpClient = newHttpClient();
        final TenantApi tenantApi = new TenantApi(httpClient);
        final RequestOptions requestOptions = requestOptions();

        Tenant tenant;
        try {
            tenant = tenantApi.getTenantByApiKey(TENANT_KEY, requestOptions);
        } catch (KillBillClientException e) {
            tenant = new Tenant();
            tenant.setApiKey(TENANT_KEY);
            tenant.setApiSecret(TENANT_KEY);
            tenant.setExternalKey(TENANT_KEY);
            tenantApi.createTenant(tenant, requestOptions);
        }
    }

    @Test
    void createCatalog1() throws KillBillClientException {
        final CatalogApi api = new CatalogApi(httpClient());
        String xml = getXmlContent("/catalog/cases/CorrectBillingPeriodLocationTest1.xml");
        xml = xml.replace("{effectiveDate}", nowAsString());
        final CatalogValidation validation = api.validateCatalogXml(xml, requestOptions());
        final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        } else {
            api.uploadCatalogXml(xml, requestOptions());
        }
    }

    @Test
    void createCatalog2() throws KillBillClientException {
        final CatalogApi api = new CatalogApi(httpClient());
        String xml = getXmlContent("/catalog/cases/CorrectBillingPeriodLocationTest2.xml");
        xml = xml.replace("{effectiveDate}", nowAsString());
        final CatalogValidation validation = api.validateCatalogXml(xml, requestOptions());
        final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        } else {
            api.uploadCatalogXml(xml, requestOptions());
        }
    }

    @Test
    void createCatalog3() throws KillBillClientException {
        final CatalogApi api = new CatalogApi(httpClient());
        String xml = getXmlContent("/catalog/cases/CorrectBillingPeriodLocationTest3.xml");
        xml = xml.replace("{effectiveDate}", nowAsString());
        final CatalogValidation validation = api.validateCatalogXml(xml, requestOptions());
        final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        } else {
            api.uploadCatalogXml(xml, requestOptions());
        }
    }

    @Test
    void createCatalog4() throws KillBillClientException {
        final CatalogApi api = new CatalogApi(httpClient());
        String xml = getXmlContent("/catalog/cases/CorrectBillingPeriodLocationTest4.xml");
        xml = xml.replace("{effectiveDate}", nowAsString());
        final CatalogValidation validation = api.validateCatalogXml(xml, requestOptions());
        final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        } else {
            api.uploadCatalogXml(xml, requestOptions());
        }
    }

    @Test
    void getCatalogXML() throws KillBillClientException {
        final DateTime catalogDate = DateTime.parse("2024-04-09T02:43:05.342117733Z");
        final CatalogApi api = new CatalogApi(newHttpClient());
        final String catalog = api.getCatalogXml(catalogDate, null, requestOptions());
        System.out.println("catalog = " + catalog);
    }

    @Test
    void getCatalogJSON() throws KillBillClientException, JsonProcessingException {
        final DateTime catalogDate = DateTime.parse("2024-04-09T00:01:18.799816464Z");
        final CatalogApi api = new CatalogApi(newHttpClient());
        final Catalogs catalogs = api.getCatalogJson(catalogDate, null, requestOptions());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        System.out.println(objectMapper.writeValueAsString(catalogs));
    }
}
