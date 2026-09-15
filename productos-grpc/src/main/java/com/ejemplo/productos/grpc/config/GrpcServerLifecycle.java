package com.ejemplo.productos.grpc.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.ejemplo.productos.grpc.transport.ProductoGrpcService;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

@Component
@ConditionalOnProperty(name = "grpc.server.enabled", havingValue = "true", matchIfMissing = true)
public class GrpcServerLifecycle implements SmartLifecycle {

    private final ProductoGrpcService productoGrpcService;
    private final int puerto;
    private Server server;
    private volatile boolean running;

    public GrpcServerLifecycle(
            ProductoGrpcService productoGrpcService,
            @Value("${grpc.server.port:9090}") int puerto) {
        this.productoGrpcService = productoGrpcService;
        this.puerto = puerto;
    }

    @Override
    public void start() {
        try {
            server = ServerBuilder.forPort(puerto)
                    .addService(productoGrpcService)
                    .addService(ProtoReflectionService.newInstance())
                    .build()
                    .start();
            running = true;
            Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
            System.out.println("Servidor gRPC iniciado en el puerto " + puerto);
        } catch (IOException ex) {
            throw new IllegalStateException("No fue posible iniciar el servidor gRPC", ex);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            server.shutdown();
            try {
                if (!server.awaitTermination(5, TimeUnit.SECONDS)) {
                    server.shutdownNow();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                server.shutdownNow();
            }
        }
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
