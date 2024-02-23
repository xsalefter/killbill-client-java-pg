package com.xsalefter.killbill.client.playground;

import org.killbill.billing.client.KillBillHttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Playground {

    protected int defaultConnectionTimeoutSec = 10;
    protected int defaultReadTimeoutSec = 60;
    protected String username = "admin";
    protected String password = "password";
    protected String apiKey = "bob"; // additional_key
    protected String apiSecret = "lazar"; // additional_secret
    protected String serverHost = "localhost";
    protected int serverPort;

    protected String proxyHost = null;
    protected Integer proxyPort = null;

    protected KillBillHttpClient newHttpClient() {
        return newHttpClient(8080);
    }

    protected KillBillHttpClient newHttpClient(int serverPort) {
        this.serverPort = serverPort;
        String kbServerUrl = String.format("http://%s:%d", serverHost, this.serverPort);
        return new KillBillHttpClient(
                kbServerUrl,
                username,
                password,
                apiKey,
                apiSecret,
                proxyHost,
                proxyPort,
                defaultConnectionTimeoutSec * 1000,
                defaultReadTimeoutSec * 1000);
    }

    protected KillBillHttpClient newHttpClient(String host, int serverPort, String apiKey, String apiSecret) {
        this.serverHost = host;
        this.serverPort = serverPort;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        String kbServerUrl = String.format("http://%s:%d", this.serverHost, this.serverPort);
        System.out.println(kbServerUrl);
        return new KillBillHttpClient(
                kbServerUrl,
                username,
                password,
                this.apiKey,
                this.serverHost,
                proxyHost,
                proxyPort,
                defaultConnectionTimeoutSec * 1000,
                defaultReadTimeoutSec * 1000);
    }

    protected String getXmlContent(String resourceSubPath) {
        final Path filePath = Path.of("src/test/resources", resourceSubPath);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read path " + filePath, e);
        }
    }
}
