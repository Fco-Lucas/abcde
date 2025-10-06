package com.lcsz.abcde;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImageCompressor {
    private final Logger log = LoggerFactory.getLogger(ImageCompressor.class);

    public void processarImagens(File diretorio) {
        if (!diretorio.exists() || !diretorio.isDirectory()) {
            log.error("Diretório inválido: {}", diretorio.getAbsolutePath());
            return;
        }

        File[] arquivos = diretorio.listFiles();
        if (arquivos == null) return;

        for (File arquivo : arquivos) {
            if (arquivo.isDirectory()) {
                // recursão para subdiretórios
                processarImagens(arquivo);
            } else if (isImagem(arquivo)) {
                try {
                    BufferedImage img = ImageIO.read(arquivo);
                    if (img != null) {
                        log.info("Reduzindo: {}", arquivo.getAbsolutePath());

                        Thumbnails.of(arquivo)
                                .size(Math.min(img.getWidth(), 1920), Math.min(img.getHeight(), 1080))
                                .outputQuality(0.75) // 75% de qualidade
                                .toFile(arquivo);    // sobrescreve a original
                    }
                } catch (IOException e) {
                    log.error("Erro ao processar: {}", arquivo.getAbsolutePath());
                }
            }
        }
    }

    public void comprimirImagem(File arquivo) {
        if (arquivo == null || !arquivo.exists() || !arquivo.isFile()) {
            log.error("Arquivo inválido: {}", (arquivo != null ? arquivo.getAbsolutePath() : "null"));
            return;
        }

        if (!isImagem(arquivo)) {
            log.warn("O arquivo não é uma imagem: {}", arquivo.getAbsolutePath());
            return;
        }

        try {
            BufferedImage img = ImageIO.read(arquivo);
            if (img != null) {
                // log.info("Comprimindo imagem: {}", arquivo.getAbsolutePath());

                // Reduz para no máximo 1920x1080 e aplica compressão 75%
                Thumbnails.of(arquivo)
                        .size(Math.min(img.getWidth(), 1920), Math.min(img.getHeight(), 1080))
                        .outputQuality(0.75) // 75% de qualidade
                        .toFile(arquivo);    // sobrescreve a original

                // log.info("Compressão concluída: {}", arquivo.getName());
            } else {
                log.warn("Não foi possível ler a imagem: {}", arquivo.getAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Erro ao comprimir imagem {}: {}", arquivo.getAbsolutePath(), e.getMessage());
        }
    }

    private boolean isImagem(File file) {
        String nome = file.getName().toLowerCase();
        return nome.endsWith(".jpg") || nome.endsWith(".jpeg") ||
                nome.endsWith(".png") || nome.endsWith(".webp") ||
                nome.endsWith(".bmp") || nome.endsWith(".gif") ||
                nome.endsWith(".tiff");
    }
}
