package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.OpinionCreateDTO;
import com.duoc.LevelUp.dtos.OpinionResponseDTO;
import com.duoc.LevelUp.exceptions.NotFoundException;
import com.duoc.LevelUp.models.*;
import com.duoc.LevelUp.repositories.OpinionRepository;
import com.duoc.LevelUp.repositories.ProductoRepository;
import com.duoc.LevelUp.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OpinionServiceImpl implements OpinionService{

    private final OpinionRepository opinionRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    private Usuario getUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
        String correo = auth.getName();
        return usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no existe en la BD"));
    }

    private OpinionResponseDTO toDTO(Opinion op) {
        Usuario u = op.getUsuario();
        return OpinionResponseDTO.builder()
                .idProducto(op.getProducto().getIdProducto())
                .idUsuario(u.getIdUsuario())
                .nombreUsuario(u.getNombres() + " " + u.getApellidos())
                .comentario(op.getComentario())
                .estrellas(op.getEstrellas().intValue())
                .build();
    }



    @Override
    public OpinionResponseDTO crearOActualizar(Long idProducto, OpinionCreateDTO dto) {
        Usuario usuario = getUsuarioActual();

        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new NotFoundException("Producto " + idProducto + " no existe"));

        OpinionId id = new OpinionId(producto.getIdProducto(), usuario.getIdUsuario());

        Opinion op = opinionRepo.findById(id).orElseGet(() -> {
            Opinion nueva = new Opinion();
            nueva.setIdOpinion(id);
            nueva.setProducto(producto);
            nueva.setUsuario(usuario);
            return nueva;
        });

        op.setComentario(dto.getComentario());
        op.setEstrellas(dto.getEstrellas().shortValue());

        Opinion saved = opinionRepo.save(op);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpinionResponseDTO> listarPorProducto(Long idProducto) {
        // valida que el producto exista
        if (!productoRepo.existsById(idProducto)) {
            throw new NotFoundException("Producto " + idProducto + " no existe");
        }
        return opinionRepo.findByProducto_IdProducto(idProducto)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public void eliminarOpinionActual(Long idProducto) {
        Usuario u = getUsuarioActual();
        OpinionId id = new OpinionId(idProducto, u.getIdUsuario());
        if (!opinionRepo.existsById(id)) {
            throw new NotFoundException("No existe opinión de este usuario para el producto " + idProducto);
        }
        opinionRepo.deleteById(id);
    }

    @Override
    public void eliminarOpinionDeUsuario(Long idProducto, Long idUsuario) {
        OpinionId id = new OpinionId(idProducto, idUsuario);
        if (!opinionRepo.existsById(id)) {
            throw new NotFoundException("No existe opinión del usuario " + idUsuario + " para el producto " + idProducto);
        }
        opinionRepo.deleteById(id);
    }
}

