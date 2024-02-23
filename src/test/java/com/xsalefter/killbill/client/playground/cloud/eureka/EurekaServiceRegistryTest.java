package com.xsalefter.killbill.client.playground.cloud.eureka;

import com.xsalefter.killbill.client.playground.Playground;
import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.AdminApi;

public class EurekaServiceRegistryTest extends Playground {

    @Test
    void putOutRotation() throws KillBillClientException {
        final AdminApi adminApi = new AdminApi(newHttpClient());
        adminApi.putOutOfRotation(RequestOptions.empty());
    }

    @Test
    void putInRotation() throws KillBillClientException {
        final AdminApi adminApi = new AdminApi(newHttpClient());
        adminApi.putInRotation(RequestOptions.empty());
    }
}
