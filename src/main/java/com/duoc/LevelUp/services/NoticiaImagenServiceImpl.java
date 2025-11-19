package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.NoticiaImagenCreateDTO;
import com.duoc.LevelUp.dtos.NoticiaImagenResponseDTO;
import com.duoc.LevelUp.exceptions.NotFoundException;
import com.duoc.LevelUp.models.Noticia;
import com.duoc.LevelUp.models.NoticiaImagen;
import com.duoc.LevelUp.repositories.NoticiaImagenRepository;
import com.duoc.LevelUp.repositories.NoticiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticiaImagenServiceImpl implements NoticiaImagenService {

    private final NoticiaRepository noticiaRepo;
    private final NoticiaImagenRepository imagenRepo;

    @Override
    public NoticiaImagenResponseDTO agregarImagenBase64(Long idNoticia, NoticiaImagenCreateDTO dto) {

        Noticia noticia = noticiaRepo.findById(idNoticia)
                .orElseThrow(() -> new NotFoundException("Noticia " + idNoticia + " no existe"));

        byte[] bytes = Base64.getDecoder().decode(dto.getBase64());

        NoticiaImagen img = new NoticiaImagen();
        img.setNoticia(noticia);
        img.setNombreArchivo(dto.getNombreArchivo());
        img.setContentType(dto.getContentType());
        img.setSizeBytes((long) bytes.length);
        img.setData(bytes);

        NoticiaImagen saved = imagenRepo.save(img);

        return NoticiaImagenResponseDTO.builder()
                .idImagenNoticia(saved.getIdImagenNoticia())
                .url("/api/noticias/imagenes/" + saved.getIdImagenNoticia())
                .contentType(saved.getContentType())
                .sizeBytes(saved.getSizeBytes())
                .nombreArchivo(saved.getNombreArchivo())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] obtenerDataImagen(Long idImagenNoticia) {
        return imagenRepo.findById(idImagenNoticia)
                .orElseThrow(() -> new NotFoundException("Imagen noticia " + idImagenNoticia + " no existe"))
                .getData();
    }

    @Override
    @Transactional(readOnly = true)
    public String obtenerContentType(Long idImagenNoticia) {
        return imagenRepo.findById(idImagenNoticia)
                .orElseThrow(() -> new NotFoundException("Imagen noticia " + idImagenNoticia + " no existe"))
                .getContentType();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticiaImagenResponseDTO> listarPorNoticia(Long idNoticia) {
        return imagenRepo.findByNoticia_IdNoticia(idNoticia)
                .stream()
                .map(img -> NoticiaImagenResponseDTO.builder()
                        .idImagenNoticia(img.getIdImagenNoticia())
                        .url("/api/noticias/imagenes/" + img.getIdImagenNoticia())
                        .contentType(img.getContentType())
                        .sizeBytes(img.getSizeBytes())
                        .nombreArchivo(img.getNombreArchivo())
                        .build())
                .toList();
    }

    @Override
    public void eliminarImagen(Long idImagenNoticia) {
        if (!imagenRepo.existsById(idImagenNoticia)) {
            throw new NotFoundException("Imagen noticia " + idImagenNoticia + " no existe");
        }
        imagenRepo.deleteById(idImagenNoticia);
    }
}
