package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.ScanImageDadosResponseDto;
import com.lcsz.abcde.dtos.ScanImageResponseDto;
import com.lcsz.abcde.dtos.lotImage.LotImageCreateDto;
import com.lcsz.abcde.dtos.lotImage.LotImageResponseDto;
import com.lcsz.abcde.dtos.lotImage.LotImageUpdateQuestionDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionCreateDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionUpdateDto;
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
import java.util.UUID;

@Service
public class LotImageService {
    private final Path raiz = Paths.get("uploads/gabaritos/");

    private final LotImageRepository lotImageRepository;
    private final LotImageQuestionService lotImageQuestionService;

    LotImageService(LotImageRepository lotImageRepository, LotImageQuestionService lotImageQuestionService) {
        this.lotImageRepository = lotImageRepository;
        this.lotImageQuestionService = lotImageQuestionService;
    }

    private String getImageUrl(Long lotId, String key) {
        return "http://localhost:8181/gabaritos/" + lotId.toString() + "/" + key;
    }

    private String generateRandomKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional(readOnly = false)
    public LotImageResponseDto create(LotImageCreateDto dto) {
        LotImage lotImage = new LotImage();
        lotImage.setLotId(dto.getLotId());
        lotImage.setKey(dto.getKey());
        lotImage.setOriginalName(dto.getOriginalName());
        lotImage.setMatricula(dto.getMatricula());
        lotImage.setNomeAluno(dto.getNomeAluno());
        lotImage.setEtapa(dto.getEtapa());
        lotImage.setProva(dto.getProva());
        lotImage.setGabarito(dto.getGabarito());
        lotImage.setPresenca(dto.getPresenca());
        lotImage.setStatus(LotImageStatus.ACTIVE);

        LotImage saved = this.lotImageRepository.save(lotImage);

        List<LotImageQuestionResponseDto> questions = this.lotImageQuestionService.getAllByImageId(saved.getId());

        LotImageResponseDto responseDto = LotImageMapper.toDto(saved);
        responseDto.setQuestions(questions);
        responseDto.setUrl(this.getImageUrl(dto.getLotId(), dto.getKey()));

        return responseDto;
    }

    @Transactional(readOnly = true)
    public Page<LotImageResponseDto> getAllPageable(
            Pageable pageable,
            Long lotId,
            String student
    ) {
        String filterStudent = (student == null || student.isBlank()) ? null : "%" + student + "%";
        Page<LotImageProjection> lotImages = this.lotImageRepository.findAllPageable(pageable, lotId, filterStudent);
        return lotImages.map(lotImage -> {
            LotImage entity = this.getById(lotImage.getId());
            List<LotImageQuestionResponseDto> questions = this.lotImageQuestionService.getAllByImageId(entity.getId());

            LotImageResponseDto responseDto = LotImageMapper.toDto(entity);
            responseDto.setUrl(this.getImageUrl(lotImage.getLotId(), lotImage.getKey()));
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
        responseDto.setUrl(this.getImageUrl(lotImage.getLotId(), lotImage.getKey()));
        return responseDto;
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        LotImage lotImage = this.getById(id);
        lotImage.setStatus(LotImageStatus.INACTIVE);
        this.lotImageRepository.save(lotImage);
    }

    @Transactional(readOnly = false)
    private ScanImageResponseDto scanImage(String pathImage) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("path_image", pathImage);
        request.put("debug", true);

        String url = "http://localhost:8000/scanImage";

        try {
            ResponseEntity<ScanImageResponseDto> response = restTemplate.postForEntity(
                    url, request, ScanImageResponseDto.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Erro ao scanear imagem com Python");
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao chamar API Python: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public LotImageResponseDto processImage(MultipartFile file, Long lotId) throws IOException {
        // Cria pasta do lote
        Path pastaLote = raiz.resolve(lotId.toString());
        Files.createDirectories(pastaLote);

        // Monta o nome do arquivo
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i >= 0) extension = originalFileName.substring(i); // inclui o ponto, ex: ".png"
        String fileKey = this.generateRandomKey();
        String fileName = fileKey + extension;

        // Salva o arquivo na pasta
        Path path_destiny = pastaLote.resolve(fileName);
        Files.copy(file.getInputStream(), path_destiny, StandardCopyOption.REPLACE_EXISTING);

        // Envia o arquivo para o PYTHON
        String pathImage = "C:/workspace/abcde/api/uploads/gabaritos/" + lotId.toString() + "/" + fileName;
        ScanImageResponseDto response = this.scanImage(pathImage);
        Map<String, String> answers = response.getRespostas();
        ScanImageDadosResponseDto dados = response.getDados();

        // Salva a imagem no banco de dados
        LotImageCreateDto lotImageCreateDto = new LotImageCreateDto(
                lotId,
                fileName,
                originalFileName,
                dados.getMatricula(),
                dados.getNomeAluno(),
                dados.getEtapa(),
                dados.getProva(),
                dados.getGabarito(),
                dados.getPresenca()
        );
        LotImageResponseDto lotImage = this.create(lotImageCreateDto);

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

    public Integer getQtdImagesLot(Long lotId) {
        return this.lotImageRepository.findAllByLotId(lotId).size();
    }

    public void updateImageQuestion(LotImageUpdateQuestionDto dto) {
        LotImageQuestionUpdateDto updateDto = new LotImageQuestionUpdateDto(dto.getAlternative());
        this.lotImageQuestionService.update(dto.getLotImageQuestionId(), updateDto);
    }
}
