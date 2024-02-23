package com.xsalefter.killbill.client.playground.catalog.basic;

import com.xsalefter.killbill.client.playground.Playground;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.CatalogApi;
import org.killbill.billing.client.api.gen.TenantApi;
import org.killbill.billing.client.model.Catalogs;
import org.killbill.billing.client.model.DateTimes;
import org.killbill.billing.client.model.gen.*;

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
    void deleteCatalog() {
        try {
            final CatalogApi api = new CatalogApi(newHttpClient());
            api.deleteCatalog(requestOptions());
        } catch (KillBillClientException e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }

    @Test
    void createEmptyCatalog() {
        final String content = getXmlContent("/catalog/basic/BasicCatalogTest.xml");
        try {
            final CatalogApi api = new CatalogApi(newHttpClient());
            final CatalogValidation validation = api.validateCatalogXml(content, requestOptions());
            final List<CatalogValidationError> errors = validation.getCatalogValidationErrors();
            if (!errors.isEmpty()) {
                errors.forEach(System.out::println);
            } else {
                // api.uploadCatalogXml(content, requestOptions());
            }
        } catch (KillBillClientException e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }

    @Test
    void listCatalogVersions() {
        try {
            final CatalogApi api = new CatalogApi(newHttpClient());
            final DateTimes versions = api.getCatalogVersions(null, requestOptions());
            versions.forEach(System.out::println);
        } catch (KillBillClientException e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }

    @Test
    void getCatalogXML() {
        final DateTime catalogDate = DateTime.parse("2014-02-26T17:46:51.101Z");
        try {
            final CatalogApi api = new CatalogApi(newHttpClient());
            final String catalog = api.getCatalogXml(catalogDate, null, requestOptions());
            System.out.println("catalog = " + catalog);
        } catch (KillBillClientException e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }

    @Test
    void getCatalogJSON() {
        final DateTime catalogDate = DateTime.parse("2030-02-26T17:46:51.101Z");
        try {
            final CatalogApi api = new CatalogApi(newHttpClient());
            final Catalogs catalogs = api.getCatalogJson(catalogDate, null, requestOptions());
            System.out.println("catalogs = " + catalogs);

            for (final Catalog catalog : catalogs) {
                System.out.println("catalogName = " + catalog.getName());

                for (final PriceList priceList : catalog.getPriceLists()) {
                    System.out.println("priceListName = " + priceList.getName());
                    for (final String plan : priceList.getPlans()) {
                        System.out.println("priceList.planName = " + plan);
                    }
                }

                for (final Product product : catalog.getProducts()) {
                    System.out.println("productName = " + product.getName());
                    for (final Plan plan : product.getPlans()) {
                        System.out.println("product.planName = " + plan.getName());
                    }
                }
            }

        } catch (KillBillClientException e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }
}
