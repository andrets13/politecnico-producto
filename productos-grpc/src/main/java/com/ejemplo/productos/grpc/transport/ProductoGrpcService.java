package com.ejemplo.productos.grpc.transport;

import java.math.BigDecimal;
import java.util.function.Supplier;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ejemplo.productos.grpc.exception.ProductoNoEncontradoException;
import com.ejemplo.productos.grpc.model.Producto;
import com.ejemplo.productos.grpc.proto.ActualizarProductoSolicitud;
import com.ejemplo.productos.grpc.proto.ListaProductos;
import com.ejemplo.productos.grpc.proto.OperacionRespuesta;
import com.ejemplo.productos.grpc.proto.ProductoId;
import com.ejemplo.productos.grpc.proto.ProductoRespuesta;
import com.ejemplo.productos.grpc.proto.ProductoServiceGrpc;
import com.ejemplo.productos.grpc.proto.ProductoSolicitud;
import com.ejemplo.productos.grpc.proto.Vacio;
import com.ejemplo.productos.grpc.service.ProductoCrudService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.validation.ConstraintViolationException;

@Component
public class ProductoGrpcService extends ProductoServiceGrpc.ProductoServiceImplBase {

    private final ProductoCrudService productoService;

    public ProductoGrpcService(ProductoCrudService productoService) {
        this.productoService = productoService;
    }

    @Override
    public void listar(Vacio request, StreamObserver<ListaProductos> observer) {
        responder(observer, () -> ListaProductos.newBuilder()
                .addAllProductos(productoService.listar().stream().map(this::convertir).toList())
                .build());
    }

    @Override
    public void obtener(ProductoId request, StreamObserver<ProductoRespuesta> observer) {
        responder(observer, () -> convertir(productoService.obtener(request.getId())));
    }

    @Override
    public void crear(ProductoSolicitud request, StreamObserver<ProductoRespuesta> observer) {
        responder(observer, () -> convertir(productoService.crear(convertir(request))));
    }

    @Override
    public void actualizar(
            ActualizarProductoSolicitud request,
            StreamObserver<ProductoRespuesta> observer) {
        responder(observer, () -> convertir(
                productoService.actualizar(request.getId(), convertir(request.getProducto()))));
    }

    @Override
    public void eliminar(ProductoId request, StreamObserver<OperacionRespuesta> observer) {
        responder(observer, () -> {
            productoService.eliminar(request.getId());
            return OperacionRespuesta.newBuilder()
                    .setExitosa(true)
                    .setMensaje("Producto eliminado correctamente")
                    .build();
        });
    }

    private Producto convertir(ProductoSolicitud request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        try {
            producto.setPrecio(new BigDecimal(request.getPrecio()));
        } catch (NumberFormatException ex) {
            throw Status.INVALID_ARGUMENT
                    .withDescription("El precio debe ser un número decimal válido")
                    .withCause(ex)
                    .asRuntimeException();
        }
        return producto;
    }

    private ProductoRespuesta convertir(Producto producto) {
        return ProductoRespuesta.newBuilder()
                .setId(producto.getId())
                .setNombre(producto.getNombre())
                .setDescripcion(producto.getDescripcion())
                .setPrecio(producto.getPrecio().toPlainString())
                .build();
    }

    private <T> void responder(StreamObserver<T> observer, Supplier<T> operacion) {
        try {
            observer.onNext(operacion.get());
            observer.onCompleted();
        } catch (ProductoNoEncontradoException ex) {
            observer.onError(Status.NOT_FOUND.withDescription(ex.getMessage()).asRuntimeException());
        } catch (ConstraintViolationException | IllegalArgumentException ex) {
            observer.onError(Status.INVALID_ARGUMENT.withDescription(ex.getMessage()).asRuntimeException());
        } catch (DataAccessException ex) {
            observer.onError(Status.UNAVAILABLE
                    .withDescription("No fue posible acceder a la base de datos")
                    .withCause(ex)
                    .asRuntimeException());
        } catch (io.grpc.StatusRuntimeException ex) {
            observer.onError(ex);
        } catch (Exception ex) {
            observer.onError(Status.INTERNAL
                    .withDescription("Ocurrió un error interno en el servidor")
                    .withCause(ex)
                    .asRuntimeException());
        }
    }
}
