package com.lcsz.abcde.services;

import com.lcsz.abcde.AppProperties;
import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.auditLogQuestion.AuditLogQuestionCreateDto;
import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeCreateDto;
import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeResponseDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbCreateDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbResponseDto;
import com.lcsz.abcde.dtos.lotImage.*;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionCreateDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionUpdateDto;
import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.dtos.scanImage.ScanImageAbcdeResponseDto;
import com.lcsz.abcde.dtos.scanImage.ScanImageDadosAbcdeResponseDto;
import com.lcsz.abcde.dtos.scanImage.ScanImageDadosVtbResponseDto;
import com.lcsz.abcde.dtos.scanImage.ScanImageVtbResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.enums.lot.LotType;
import com.lcsz.abcde.enums.lot_image.LotImageStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.LotImageMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.Lot;
import com.lcsz.abcde.models.LotImage;
import com.lcsz.abcde.models.LotImageQuestion;
import com.lcsz.abcde.repositorys.LotImageRepository;
import com.lcsz.abcde.repositorys.projection.LotImageProjection;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class LotImageService {
    private final Path raiz = Paths.get("uploads/gabaritos/");

    private final LotImageRepository lotImageRepository;
    private final LotService lotService;
    private final LotImageQuestionService lotImageQuestionService;
    private final ImageInfoAbcdeService imageInfoAbcdeService;
    private final ImageInfoVtbService imageInfoVtbService;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final AuditLogService auditLogService;
    private final AuditLogQuestionService auditLogQuestionService;
    private final AppProperties appProperties;

    LotImageService(
        LotImageRepository lotImageRepository,
        @Lazy LotService lotService,
        LotImageQuestionService lotImageQuestionService,
        ImageInfoAbcdeService imageInfoAbcdeService,
        ImageInfoVtbService imageInfoVtbService,
        AuthenticatedUserProvider authenticatedUserProvider,
        AuditLogService auditLogService,
        AuditLogQuestionService auditLogQuestionService,
        AppProperties appProperties
    ) {
        this.lotImageRepository = lotImageRepository;
        this.lotService = lotService;
        this.lotImageQuestionService = lotImageQuestionService;
        this.imageInfoAbcdeService = imageInfoAbcdeService;
        this.imageInfoVtbService = imageInfoVtbService;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.auditLogService = auditLogService;
        this.auditLogQuestionService = auditLogQuestionService;
        this.appProperties = appProperties;
    }

    private String getImageUrl(Long lotId, String key) {
        return appProperties.getBaseImagesUrl() + lotId + "/" + key;
    }

    private String getAbsoluteImagePath(Long lotId, String key) {
        return appProperties.getBaseImagesPath() + lotId + "/" + key;
    }

    @Transactional(readOnly = false)
    public LotImageResponseDto create(LotImageCreateDto dto) {
        LotImage lotImage = LotImageMapper.toEntityCreate(dto);
        lotImage.setHaveModification(false);
        lotImage.setStatus(LotImageStatus.ACTIVE);

        LotImage saved = this.lotImageRepository.save(lotImage);

        List<LotImageQuestionResponseDto> questions = this.lotImageQuestionService.getAllByImageId(saved.getId());

        LotImageResponseDto responseDto = LotImageMapper.toDto(saved);
        responseDto.setQuestions(questions);
        responseDto.setUrl(this.getImageUrl(dto.getLotId(), dto.getKey()));

        return responseDto;
    }

    private void excludeFile(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) Files.delete(path);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir imagem: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<LotImagePageableResponseDto> getAllPageable(
            Pageable pageable,
            Long lotId,
            String student
    ) {
        String filterStudent = (student == null || student.isBlank()) ? null : "%" + student + "%";
        Page<LotImageProjection> lotImages = this.lotImageRepository.findAllPageable(pageable, lotId, filterStudent);

        // Busca o campo 'image_active_days' do cliente de acordo com o usuário que criou o lote
        Integer imageActiveDays = this.lotService.getImageActiveDaysFromLotId(lotId);

        // Busca o cargo do usuário que criou o lote
        String userRole = this.lotService.getRoleFromLotId(lotId);

        return lotImages.map(lotImage -> {
            // Cria o dto
            LotImagePageableResponseDto responseDto = LotImageMapper.toPageableDto(lotImage);

            LocalDateTime createdAt = lotImage.getCreatedAt();
            String pathImage = this.getAbsoluteImagePath(lotId, lotImage.getKey());

            LocalDateTime expirationDate = null;

            if (createdAt != null && imageActiveDays != null) {
                expirationDate = createdAt.plusDays(imageActiveDays);

                // Se o cargo for diferente de COMPUTEX exclui
                if (!userRole.equals("COMPUTEX")) {
                    long daysSinceCreation = ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
                    if (daysSinceCreation >= imageActiveDays) this.excludeFile(pathImage);
                }
            }

            responseDto.setExpirationImageDate(expirationDate);

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
        LotImageResponseDto responseDto = LotImageMapper.toDto(lotImage);

        Lot lot = this.lotService.getLotById(lotImage.getLotId());
        LotType type = lot.getType();

        if(type == LotType.ABCDE) responseDto.setAbcdeInfo(this.getImageAbcdeInfo(lotImage.getId()));
        else responseDto.setVtbInfo(this.getImageVtbInfo(lotImage.getId()));

        // Busca as questões da imagem
        List<LotImageQuestionResponseDto> questions = this.lotImageQuestionService.getAllByImageId(id);

        // Verifica se a imagem existe no diretório, se não existir seta null na URL
        String imageUrl = this.getImageUrl(lotImage.getLotId(), lotImage.getKey());
        String imageAbsolutePath = this.getAbsoluteImagePath(lotImage.getLotId(), lotImage.getKey());
        String url = Files.exists(Paths.get(imageAbsolutePath)) ? imageUrl : null;

        responseDto.setQuestions(questions);
        responseDto.setUrl(url);
        return responseDto;
    }

    @Transactional(readOnly = true)
    public ImageInfoAbcdeResponseDto getImageAbcdeInfo(Long lotImageId) {
        return this.imageInfoAbcdeService.getByImageId(lotImageId);
    }

    @Transactional(readOnly = true)
    public ImageInfoVtbResponseDto getImageVtbInfo(Long lotImageId) {
        return this.imageInfoVtbService.getByImageId(lotImageId);
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        // Busca e verifica as permissões do usuário autenticado
        PermissionResponseDto permission = this.authenticatedUserProvider.getAuthenticatedUserPermissions();
        if(!permission.getUpload_files()) throw new RuntimeException("Você não tem permissão para excluir gabaritos");

        LotImage lotImage = this.getById(id);
        lotImage.setStatus(LotImageStatus.INACTIVE);
        LotImage updated = this.lotImageRepository.save(lotImage);

        String details = String.format(
                "Gabarito com ID: %s teve o status alterado para INATIVO (exclusão lógica).", updated.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.DELETE, AuditProgram.LOT_IMAGE, details);
        this.auditLogService.create(logDto);
    }

    public String formatLotImageForLog(LotImageResponseDto dto, StringBuilder questionsLog) {
        if(dto.getAbcdeInfo() != null) {
            ImageInfoAbcdeResponseDto abcdeInfo = dto.getAbcdeInfo();

            String abcdeInfoLog = String.format(
                "{id='%s', nome_original_da_imagem='%s', codigo_escola='%s', ano='%s', grauSerie='%s', turno='%s', turma='%s', etapa='%s', prova='%s', gabarito='%s'}",
                abcdeInfo.getId(), abcdeInfo.getOriginalName(), abcdeInfo.getCodigoEscola(), abcdeInfo.getAno(), abcdeInfo.getGrauSerie(), abcdeInfo.getTurno(), abcdeInfo.getTurma(), abcdeInfo.getEtapa(), abcdeInfo.getProva(), abcdeInfo.getGabarito()
            );

            return String.format("{id='%s', id_do_Lote='%s' matricula='%s', presenca='%s', quantidade_de_questões='%s', informações_do_abcde='%s', questões='%s'}",
                    dto.getId(), dto.getLotId(), dto.getMatricula(), dto.getPresenca(), dto.getQtdQuestoes(), abcdeInfoLog, questionsLog);
        }else if(dto.getVtbInfo() != null) {
            ImageInfoVtbResponseDto vtbInfo = dto.getVtbInfo();

            String vtbInfoLog = String.format(
                "{id='%s', vtbCodigo='%s', vtbFracao='%s', faseGab='%s', prova='%s'}",
                vtbInfo.getId(), vtbInfo.getVtbCodigo(), vtbInfo.getVtbFracao(), vtbInfo.getFaseGab(), vtbInfo.getProva()
            );

            return String.format("{id='%s', id_do_Lote='%s' matricula='%s', presenca='%s', quantidade_de_questões='%s', informações_do_vestibular='%s', questões='%s'}",
                    dto.getId(), dto.getLotId(), dto.getMatricula(), dto.getPresenca(), dto.getQtdQuestoes(), vtbInfoLog, questionsLog);
        }else throw new RuntimeException("Erro ao registrar log da imagem do lote, tipo inválido");
    }

    @Transactional(readOnly = false)
    private <T> T scanImage(String pathImage, LotType type, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("path_image", pathImage);
        request.put("lot_type", type);
        request.put("debug", false);

        String url = appProperties.getApiPythonUrl();

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(request, getJsonHeaders()),
                    responseType
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Erro ao scanear imagem com Python: status " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao chamar API Python: " + e.getMessage(), e);
        }
    }

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ScanImageAbcdeResponseDto scanAbcdeImage(String pathImage, LotType type) {
        return scanImage(pathImage, type, ScanImageAbcdeResponseDto.class);
    }

    private ScanImageVtbResponseDto scanVtbImage(String pathImage, LotType type) {
        return scanImage(pathImage, type, ScanImageVtbResponseDto.class);
    }

    // Função na qual verifica se já existe a imagem no lote evitando duplicatas
    private Boolean existsByFileKey(String fileKey, Long lotId, LotImageStatus status) {
        return !this.lotImageRepository.findAllByKeyAndLotIdAndStatus(fileKey, lotId, status).isEmpty();
    }

    // Função na qual calcula a HASH do arquivo recebido com base no seu conteúdo
    private String generateHashKey(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream is = file.getInputStream()) {
            byte[] buffer = new byte[8192]; // 8 KB
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }

        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Transactional(readOnly = false)
    public LotImageResponseDto processImage(MultipartFile file, Long lotId) throws IOException, NoSuchAlgorithmException, RuntimeException {
        // Busca e verifica as permissões do usuário autenticado
        PermissionResponseDto permission = this.authenticatedUserProvider.getAuthenticatedUserPermissions();
        if(!permission.getUpload_files()) throw new RuntimeException("Você não tem permissão para processar gabaritos");

        // Verifica se o lote da imagem já está fechado
        Lot lot = this.lotService.getLotById(lotId);
        if(Objects.equals(lot.getStatus().toString(), "COMPLETED")) throw new RuntimeException("O lote já está concluido portanto não é possível enviar novas imagens");
        LotType type = lot.getType();

        // Monta o nome do arquivo
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i >= 0) extension = originalFileName.substring(i); // inclui o ponto, ex: ".png"
        String fileKey = this.generateHashKey(file);
        String fileName = fileKey + extension;

        // Verifica se já existe alguma imagem no lote com a mesma key
        // Imagem já existente, não processar novamente
        if(this.existsByFileKey(fileName, lotId, LotImageStatus.ACTIVE)) {
            throw new RuntimeException(String.format("Imagem duplicada encontrada com a HASH '%s'", fileKey));
        }

        // Cria pasta do lote
        Path pastaLote = raiz.resolve(lotId.toString());
        Files.createDirectories(pastaLote);

        // Salva o arquivo na pasta
        Path path_destiny = pastaLote.resolve(fileName);
        Files.copy(file.getInputStream(), path_destiny, StandardCopyOption.REPLACE_EXISTING);

        LotImageResponseDto responseDto;
        StringBuilder questionsLog = new StringBuilder();

        // Envia o arquivo para o PYTHON
        String pathImage = this.getAbsoluteImagePath(lotId, fileName);
        if(type == LotType.ABCDE) {
            ScanImageAbcdeResponseDto response = this.scanAbcdeImage(pathImage, type);

            Map<String, String> answers = response.getRespostas();
            ScanImageDadosAbcdeResponseDto dados = response.getDados();

            // Salva a imagem no banco de dados
            LotImageCreateDto lotImageCreateDto = new LotImageCreateDto(lotId, fileName, dados.getMatricula(), dados.getNomeAluno(), dados.getPresenca(), dados.getQtdQuestoes());
            LotImageResponseDto lotImage = this.create(lotImageCreateDto);

            // Salva as respostas obtidas no banco de dados
            answers.forEach((key, value) -> {
                LotImageQuestionCreateDto questionCreateDto = new LotImageQuestionCreateDto(
                        lotImage.getId(),
                        Integer.parseInt(key),
                        value
                );
                this.lotImageQuestionService.create(questionCreateDto);
                questionsLog.append(String.format("{questão=%s, alternativa=%s}", Integer.parseInt(key), value));
            });

            // Salva as informações da imagem
            ImageInfoAbcdeCreateDto imageInfoAbcdeCreateDto = new ImageInfoAbcdeCreateDto(lotImage.getId(), originalFileName, dados.getCodigoEscola(), dados.getAno(), dados.getGrauSerie(), dados.getTurno(), dados.getTurma(), dados.getEtapa(), dados.getProva(), dados.getGabarito());
            this.imageInfoAbcdeService.create(imageInfoAbcdeCreateDto);

            responseDto = this.getByIdDto(lotImage.getId());
        } else {
            ScanImageVtbResponseDto response = this.scanVtbImage(pathImage, type);

            Map<String, String> answers = response.getRespostas();
            ScanImageDadosVtbResponseDto dados = response.getDados();

            // Salva a imagem no banco de dados
            LotImageCreateDto lotImageCreateDto = new LotImageCreateDto(lotId, fileName, dados.getMatricula(), dados.getNomeAluno(), dados.getPresenca(), dados.getQtdQuestoes());
            LotImageResponseDto lotImage = this.create(lotImageCreateDto);

            // Salva as respostas obtidas no banco de dados
            answers.forEach((key, value) -> {
                LotImageQuestionCreateDto questionCreateDto = new LotImageQuestionCreateDto(
                        lotImage.getId(),
                        Integer.parseInt(key),
                        value
                );
                questionsLog.append(String.format("{questão=%s, alternativa=%s}", Integer.parseInt(key), value));
                this.lotImageQuestionService.create(questionCreateDto);
            });

            // Salva as informações da imagem
            ImageInfoVtbCreateDto imageInfoVtbCreateDto = new ImageInfoVtbCreateDto(lotImage.getId(), dados.getVtbCodigo(), dados.getVtbFracao(), dados.getFaseGab(), dados.getProva());
            this.imageInfoVtbService.create(imageInfoVtbCreateDto);

            responseDto = this.getByIdDto(lotImage.getId());
        }

        // Log
        String details = String.format(
                "Gabarito pertencente ao lote com ID: %s e TIPO: %s processado com sucesso | Dados do gabarito processado: %s",
                responseDto.getLotId(), type, this.formatLotImageForLog(responseDto, questionsLog)
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.PROCESSED, AuditProgram.LOT_IMAGE, details);
        this.auditLogService.create(logDto);

        return responseDto;
    }

    public List<LotImage> getAllImagesLot(Long lotId) {
        return this.lotImageRepository.findAllByLotIdAndStatus(lotId, LotImageStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<LotImageHashResponseDto> getAllImagesLotHash(Long lotId) {
        List<LotImage> images = this.getAllImagesLot(lotId);
        List<LotImageHashResponseDto> response = new ArrayList<>();
        for(LotImage image : images) {
            // Garante pegar apenas a hash da imagem
            String key = image.getKey();
            String hash = key.contains(".") ? key.substring(0, key.lastIndexOf('.')) : key;

            LotImageHashResponseDto hashResponseDto = new LotImageHashResponseDto(hash, image.getMatricula(), image.getNomeAluno());
            response.add(hashResponseDto);
        }
        return response;
    }

    public void updateImageQuestions(Long lotImageId, List<LotImageUpdateQuestionDto> dto) {
        // Verifica se existe algum registro informado cujo a alternativa seja vazia ou null
        boolean haveEmptyAlternative = dto.stream()
                .anyMatch(question -> question.getAlternative() == null || question.getAlternative().isBlank());
        if(haveEmptyAlternative) throw new RuntimeException("Não é permitido alterar questões com alternativa em branco");

        LotImage lotImage = this.getById(lotImageId);

        // Busca e verifica as permissões do usuário autenticado
        PermissionResponseDto permission = this.authenticatedUserProvider.getAuthenticatedUserPermissions();
        if(!permission.getUpload_files()) throw new RuntimeException("Você não tem permissão para atualizar as respotas do gabarito");

        // Verifica se o lote da imagem já está fechado
        Lot lot = this.lotService.getLotById(lotImage.getLotId());
        if(Objects.equals(lot.getStatus().toString(), "COMPLETED")) throw new RuntimeException("O lote já está concluido portanto não é possível alterar suas questões");

        // Verifica se o aluno faltou
        if(lotImage.getPresenca() != 1) throw new RuntimeException("Não é permitido alterar questões do gabarito na qual o aluno faltou");

        StringBuilder questionsUpdated = new StringBuilder();

        dto.forEach((question) -> {
            LotImageQuestionUpdateDto updateDto = new LotImageQuestionUpdateDto(question.getAlternative(), question.getPreviousAlternative());
            LotImageQuestion saved = this.lotImageQuestionService.update(question.getLotImageQuestionId(), updateDto);

            questionsUpdated.append(String.format("{questão=%s, anterior=%s, atual=%s}, ",
                    saved.getNumber(),
                    question.getPreviousAlternative(),
                    question.getAlternative()
            ));
        });

        lotImage.setHaveModification(true);
        this.lotImageRepository.save(lotImage);

        // Remove a última vírgula e espaço, se necessário
        String updatedStr = questionsUpdated.toString().replaceAll(", $", "");

        // Insere o log na tabela de auditoria geral
        String details = String.format(
            "Questões do gabarito com ID: '%s' atualizadas manualmente | Questões atualizadas: %s",
            lotImage.getId(), updatedStr
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.UPDATE, AuditProgram.LOT_IMAGE, details);
        this.auditLogService.create(logDto);

        // Busca o cliente do usuário autenticado (se for o próprio cliente retorna ele mesmo, caso contrário retorna o equivalente ao usuário do cliente)
        Client authClient = this.authenticatedUserProvider.getClientAuthenticatedUser();

        // Insere o log na tabela somente de atualização das questões
        String detailsQuestion = String.format(
                "Questões do gabarito atualizadas manualmente | Questões atualizadas: %s",
                updatedStr
        );
        AuditLogQuestionCreateDto logQuestionDto = new AuditLogQuestionCreateDto(authClient.getId(), lotImageId, detailsQuestion);
        this.auditLogQuestionService.create(logQuestionDto);
    }

    public List<LotImageQuestionResponseDto> getAllQuestionsLotImage(Long lotImageId) {
        return this.lotImageQuestionService.getAllByImageId(lotImageId);
    }

    // Gera o .zip para baixar as imagens do lote
    public void downloadAllImages(Long lotId, OutputStream outputStream) {
        List<LotImage> images = this.getAllImagesLot(lotId);

        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
            for (LotImage image : images) {
                String absolutePath = getAbsoluteImagePath(lotId, image.getKey());
                File file = new File(absolutePath);

                if (file.exists() && file.isFile()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        ZipEntry zipEntry = new ZipEntry(image.getKey()); // ou file.getName()
                        zipOut.putNextEntry(zipEntry);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) >= 0) {
                            zipOut.write(buffer, 0, length);
                        }
                        zipOut.closeEntry();
                    }
                }
            }
            zipOut.finish();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar ZIP", e);
        }
    }
}
