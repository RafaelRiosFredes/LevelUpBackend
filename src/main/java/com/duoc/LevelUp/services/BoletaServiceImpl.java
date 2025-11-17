package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.BoletaCreateDTO;
import com.duoc.LevelUp.dtos.BoletaItemDTO;
import com.duoc.LevelUp.dtos.BoletaResponseDTO;
import com.duoc.LevelUp.dtos.DetalleBoletaResponseDTO;
import com.duoc.LevelUp.exceptions.NotFoundException;
import com.duoc.LevelUp.models.*;
import com.duoc.LevelUp.repositories.BoletaRepository;
import com.duoc.LevelUp.repositories.DetalleBoletaRepository;
import com.duoc.LevelUp.repositories.ProductoRepository;
import com.duoc.LevelUp.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class BoletaServiceImpl implements BoletaService{
    private final BoletaRepository boletaRepo;
    private final DetalleBoletaRepository detalleRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    // helpers
    private Usuario getUsuarioActual(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            throw new IllegalStateException("No hay usuario autenticado");
        }

        String correo = auth.getName(); // correo como username
        return usuarioRepo.findByCorreo(correo)
                .orElseThrow(()-> new IllegalStateException("Usuario autenticado no existe en la BD"));
    }

    private BoletaResponseDTO toDTO(Boleta boleta){
        //Calcula total sin descuento a partir de detalles
        long totalSinDescuento = boleta.getDetalles().stream()
                .mapToLong(d->d.getProducto().getPrecio() * d.getCantidad())
                .sum();

        List<DetalleBoletaResponseDTO> detalleDTOs = boleta.getDetalles().stream()
                .map(d-> DetalleBoletaResponseDTO.builder()
                        .idProducto(d.getProducto().getIdProducto())
                        .nombreProducto(d.getProducto().getNombreProducto())
                        .precioUnitario(d.getProducto().getPrecio())
                        .cantidad(d.getCantidad())
                        .subtotal(d.getProducto().getPrecio() * d.getCantidad())
                        .build()
                ).collect(Collectors.toList());

        Usuario u = boleta.getUsuario();

        return BoletaResponseDTO.builder()
                .idBoleta(boleta.getIdBoleta())
                .fechaEmision(boleta.getFechaEmision())
                .idUsuario(u.getIdUsuario())
                .nombreUsuario(u.getNombres() + " " + u.getApellidos())
                .totalSinDescuento(totalSinDescuento)
                .descuento(boleta.getDescuento())
                .total(boleta.getTotal())
                .descuentoDuocAplicado(boleta.getDescuento() != null && boleta.getDescuento() > 0)
                .detalles(detalleDTOs)
                .build();
    }

    // implementacion

    @Override
    public BoletaResponseDTO crearBoleta(BoletaCreateDTO dto){
        Usuario usuario = getUsuarioActual();

        if(dto.getItems() == null || dto.getItems().isEmpty()){
            throw new IllegalArgumentException("La boleta debe tener al menos un producto");
        }

        // cache de productos
        Map<Long, Producto> productos = new HashMap<>();

        // validar productos y stock
        for (BoletaItemDTO item : dto.getItems()){
            Long idProd = item.getIdProducto();
            int cantidad = item.getCantidad();

            if(cantidad <= 0){
                throw new IllegalArgumentException("Cantidad inválida para producto " + idProd);
            }

            Producto p = productos.computeIfAbsent(idProd, id->
                    productoRepo.findById(id)
                            .orElseThrow(()-> new NotFoundException("Producto " + id + " no existe"))
            );

            if(p.getStock() < cantidad){
                throw new IllegalArgumentException("Stock insuficiente para " + p.getNombreProducto());
            }

        }

        // crear boleta

        Boleta boleta = new Boleta();
        boleta.setUsuario(usuario);
        boleta.setFechaEmision(LocalDateTime.now());
        boleta.setDescuento(dto.getDescuento());
        boleta.setTotal(dto.getTotal());

        Boleta savedBoleta = boletaRepo.save(boleta);

        // crear detalles, actualizar stock
        List<DetalleBoleta> detalles = new ArrayList<>();

        for(BoletaItemDTO item : dto.getItems()){
            Long idProd = item.getIdProducto();
            int cantidad = item.getCantidad();

            Producto p = productos.get(idProd);

            // actualizar stock
            p.setStock(p.getStock() - cantidad);
            productoRepo.save(p);

            DetalleBoletaId detId = new DetalleBoletaId();
            detId.setIdBoleta(savedBoleta.getIdBoleta());
            detId.setIdProducto(p.getIdProducto());

            DetalleBoleta det = new DetalleBoleta();
            det.setId(detId);
            det.setBoleta(savedBoleta);
            det.setProducto(p);
            det.setCantidad(cantidad);

            detalles.add(detalleRepo.save(det));
        }

        // asociar detalles en memoria para el mapeo
        savedBoleta.setDetalles(detalles);

        return toDTO(savedBoleta);

    }

    @Override
    @Transactional(readOnly = true)
    public BoletaResponseDTO obtenerPorId(Long id){
        Boleta b = boletaRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Boleta " + id + " no existe"));

        // forzar carga de detalles
        b.getDetalles().size();

        return toDTO(b);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<BoletaResponseDTO> listarTodas(Pageable pageable) {
        Page<Boleta> page = boletaRepo.findAll(pageable);

        // forzar carga de detalles
        page.forEach(b-> b.getDetalles().size());

        return page.map(this::toDTO);
    }


}
