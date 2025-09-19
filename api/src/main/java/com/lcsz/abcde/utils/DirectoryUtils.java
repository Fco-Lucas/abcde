package com.lcsz.abcde.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectoryUtils {
    private static final Logger log = LoggerFactory.getLogger(DirectoryUtils.class);

    /**
     * Exclui um diretório e todo o seu conteúdo.
     *
     * @param directoryPath Caminho do diretório a ser excluído
     * @throws IOException caso ocorra erro grave na exclusão
     */
    public static void deleteDirectory(String directoryPath) throws IOException {
        if (directoryPath == null || directoryPath.isBlank()) {
            throw new IllegalArgumentException("O caminho do diretório não pode ser nulo ou vazio");
        }

        Path path = Paths.get(directoryPath);

        if (!Files.exists(path)) {
            log.info("Diretório não existe: {}", path);
            return;
        }

        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                try {
                    Files.delete(file);
                    log.debug("Arquivo excluído: {}", file);
                } catch (IOException e) {
                    log.error("Falha ao excluir arquivo: {}", file, e);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                try {
                    Files.delete(dir);
                    log.debug("Diretório excluído: {}", dir);
                } catch (IOException e) {
                    log.error("Falha ao excluir diretório: {}", dir, e);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Exclui um único arquivo.
     *
     * @param filePath Caminho do arquivo a ser excluído
     */
    public static void excludeFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode ser nulo ou vazio");
        }

        Path path = Paths.get(filePath);

        try {
            if (Files.exists(path)) {
                Files.delete(path);
                log.debug("Arquivo excluído: {}", path);
            } else {
                log.info("Arquivo não encontrado: {}", path);
            }
        } catch (IOException e) {
            log.error("Erro ao excluir arquivo: {}", path, e);
        }
    }
}
