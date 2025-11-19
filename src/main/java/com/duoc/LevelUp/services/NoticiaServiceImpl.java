package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.NoticiaCreateDTO;
import com.duoc.LevelUp.dtos.NoticiaImagenResponseDTO;
import com.duoc.LevelUp.dtos.NoticiaResponseDTO;
import com.duoc.LevelUp.exceptions.NotFoundException;
import com.duoc.LevelUp.models.Noticia;
import com.duoc.LevelUp.repositories.NoticiaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticiaServiceImpl implements NoticiaService {

    private final NoticiaRepository noticiaRepo;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    private NoticiaResponseDTO toDTO(Noticia n) {
        return NoticiaResponseDTO.builder()
                .idNoticia(n.getIdNoticia())
                .titulo(n.getTitulo())
                .descripcion(n.getDescripcion())
                .autor(n.getAutor())
                .fechaPublicacion(n.getFechaPublicacion().format(DATE_FMT)) // yyyy-MM-dd
                .imagenes(n.getImagenes().stream().map(img->
                        NoticiaImagenResponseDTO.builder()
                                .idImagenNoticia(img.getIdImagenNoticia())
                                .url("/api/noticias/imagenes/" + img.getIdImagenNoticia())
                                .contentType(img.getContentType())
                                .sizeBytes(img.getSizeBytes())
                                .nombreArchivo(img.getNombreArchivo())
                                .build()
                ).toList())
                .build();

    }

    @Override
    public NoticiaResponseDTO crear(NoticiaCreateDTO dto) {
        Noticia n = new Noticia();
        n.setTitulo(dto.getTitulo());
        n.setDescripcion(dto.getDescripcion());
        n.setAutor(dto.getAutor());
        n.setFechaPublicacion(dto.getFechaPublicacion());
        Noticia guardada = noticiaRepo.save(n);
        return toDTO(guardada);
    }

    @Override
    public NoticiaResponseDTO actualizar(Long id, NoticiaCreateDTO dto) {
        Noticia n = noticiaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));
        n.setTitulo(dto.getTitulo());
        n.setDescripcion(dto.getDescripcion());
        n.setAutor(dto.getAutor());
        n.setFechaPublicacion(dto.getFechaPublicacion());
        return toDTO(n);
    }

    @Override
    public void eliminar(Long id) {
        if (!noticiaRepo.existsById(id)) {
            throw new NotFoundException("Noticia no encontrada");
        }
        noticiaRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public NoticiaResponseDTO obtenerPorId(Long id) {
        return noticiaRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticiaResponseDTO> listarTodas() {
        return noticiaRepo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
