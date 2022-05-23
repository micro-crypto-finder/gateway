package com.jjbeto.microservice.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static org.testcontainers.containers.wait.strategy.Wait.forLogMessage;
import static org.testcontainers.utility.DockerImageName.parse;

public final class Containers {

    private static final Logger LOGGER = LoggerFactory.getLogger(Containers.class);

    public static final GenericContainer<?> IP_LOCATOR = new GenericContainer<>(parse("jjbeto/micro-crypto-finder-iplocator:latest"))
            .withExposedPorts(8080)
            .waitingFor(forLogMessage(".*Started IpLocatorApplication.*", 1))
            .withLogConsumer(new Slf4jLogConsumer(LOGGER));

    public static final GenericContainer<?> CRYPTO_LOCATOR = new GenericContainer<>(parse("jjbeto/micro-crypto-finder-cryptolocator:latest"))
            .withExposedPorts(8080)
            .waitingFor(forLogMessage(".*Started CryptolocatorApplication.*", 1))
            .withLogConsumer(new Slf4jLogConsumer(LOGGER));

}
