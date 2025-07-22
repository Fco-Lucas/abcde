package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.lotImage.LotImageCreateDto;
import com.lcsz.abcde.dtos.lotImage.LotImageResponseDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionCreateDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.enums.lot_image.LotImageStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.LotImageMapper;
import com.lcsz.abcde.models.LotImage;
import com.lcsz.abcde.repositorys.LotImageRepository;
import com.lcsz.abcde.repositorys.projection.LotImageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LotImageService {
    private final Path raiz = Paths.get("C:/workspace/abcde/images/");

    private final LotImageRepository lotImageRepository;
    private final LotImageQuestionService lotImageQuestionService;

    LotImageService(LotImageRepository lotImageRepository, LotImageQuestionService lotImageQuestionService) {
        this.lotImageRepository = lotImageRepository;
        this.lotImageQuestionService = lotImageQuestionService;
    }

    private String generateRandomKey(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    @Transactional(readOnly = false)
    public LotImageResponseDto create(LotImageCreateDto dto) {
        String key = this.generateRandomKey(100);

        LotImage lotImage = new LotImage();
        lotImage.setLotId(dto.getLotId());
        lotImage.setKey(key);
        lotImage.setStatus(LotImageStatus.ACTIVE);

        LotImage saved = this.lotImageRepository.save(lotImage);

        List<LotImageQuestionResponseDto> questions = this.lotImageQuestionService.getAllByImageId(saved.getId());

        LotImageResponseDto responseDto = LotImageMapper.toDto(saved);
        responseDto.setQuestions(questions);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public Page<LotImageResponseDto> getAllPageable(
            Pageable pageable,
            Long lotId
    ) {
        Page<LotImageProjection> lotImages = this.lotImageRepository.findAllPageable(pageable, lotId);
        return lotImages.map(lotImage -> {
            LotImage entity = this.getById(lotImage.getId());
            List<LotImageQuestionResponseDto> questions = this.lotImageQuestionService.getAllByImageId(entity.getId());
            LotImageResponseDto responseDto = LotImageMapper.toDto(entity);
            responseDto.setQuestions(questions);
            return responseDto;
        });
    }

    @Transactional(readOnly = true)
    public LotImage getById(Long id) {
        return this.lotImageRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Imagem com id '%s' não encontrada", id))
        );
    }

    @Transactional(readOnly = true)
    public LotImageResponseDto getByIdDto(Long id) {
        LotImage lotImage = this.getById(id);
        List<LotImageQuestionResponseDto> questions = this.lotImageQuestionService.getAllByImageId(id);
        LotImageResponseDto responseDto = LotImageMapper.toDto(lotImage);
        responseDto.setQuestions(questions);
        return responseDto;
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        LotImage lotImage = this.getById(id);
        lotImage.setStatus(LotImageStatus.INACTIVE);
        this.lotImageRepository.save(lotImage);
    }

    @Transactional(readOnly = false)
    private Map<String, String> scanImage(String pathImage) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("path_image", pathImage);
        request.put("debug", true);

        String url = "http://localhost:8000/scanImage";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return (Map<String, String>) response.getBody().get("respostas");
            } else {
                throw new RuntimeException("Erro ao scanear imagem com Python");
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao chamar API Python: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public LotImageResponseDto createImage(MultipartFile file, Long lotId) throws IOException {
        // Salva a imagem no banco de dados
        LotImageCreateDto lotImageCreateDto = new LotImageCreateDto(lotId);
        LotImageResponseDto lotImage = this.create(lotImageCreateDto);

        // Cria pasta do lote
        Path pastaLote = raiz.resolve(lotId.toString());
        Files.createDirectories(pastaLote);

        // Monta o nome do arquivo
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i >= 0) extension = originalFileName.substring(i); // inclui o ponto, ex: ".png"
        String fileName = lotImage.getKey() + extension;

        // Salva o arquivo na pasta
        Path path_destiny = pastaLote.resolve(fileName);
        Files.copy(file.getInputStream(), path_destiny, StandardCopyOption.REPLACE_EXISTING);

        // Envia o arquivo para o PYTHON
        Map<String, String> answers = this.scanImage(path_destiny.toString());

        // Salva as respostas obtidas no banco de dados
        answers.forEach((key, value) -> {
            LotImageQuestionCreateDto questionCreateDto = new LotImageQuestionCreateDto(
                    lotImage.getId(),
                    Integer.parseInt(key),
                    value
            );
            this.lotImageQuestionService.create(questionCreateDto);
        });

        return this.getByIdDto(lotImage.getId());
    }
}
