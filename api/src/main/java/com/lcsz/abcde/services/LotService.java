package com.lcsz.abcde.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.exportData.ExportAbcdeDataDto;
import com.lcsz.abcde.dtos.exportData.ExportAbcdeImagesDto;
import com.lcsz.abcde.dtos.exportData.ExportVtbDataDto;
import com.lcsz.abcde.dtos.exportData.ExportVtbImagesDto;
import com.lcsz.abcde.dtos.imageInfoAbcde.ImageInfoAbcdeResponseDto;
import com.lcsz.abcde.dtos.imageInfoVtb.ImageInfoVtbResponseDto;
import com.lcsz.abcde.dtos.lot.LotCreateDto;
import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.dtos.lot.LotUpdateDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.dtos.permissions.PermissionResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.enums.client.ClientStatus;
import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.enums.lot.LotType;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.ExportMapper;
import com.lcsz.abcde.mappers.LotMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.models.Lot;
import com.lcsz.abcde.models.LotImage;
import com.lcsz.abcde.repositorys.LotRepository;
import com.lcsz.abcde.repositorys.projection.LotProjection;
import com.lcsz.abcde.security.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LotService {
    private final LotRepository lotRepository;
    private final LotImageService lotImageService;
    private final ClientService clientService;
    private final ClientUserService clientUserService;
    private final AuditLogService auditLogService;
    private final AuthenticatedUserProvider provider;
    private final ObjectMapper objectMapper;

    LotService(LotRepository lotRepository, LotImageService lotImageService, ClientService clientService, ClientUserService clientUserService, AuditLogService auditLogService, AuthenticatedUserProvider provider, ObjectMapper objectMapper) {
        this.lotRepository = lotRepository;
        this.lotImageService = lotImageService;
        this.clientService = clientService;
        this.clientUserService = clientUserService;
        this.auditLogService = auditLogService;
        this.provider = provider;
        this.objectMapper = objectMapper;
    }

    public String formatLotForLog(LotResponseDto dto) {
        return String.format("{id='%s', nome='%s', tipo='%s'}",
                dto.getId(), dto.getName(), dto.getType());
    }

    @Transactional(readOnly = false)
    public LotResponseDto create(LotCreateDto dto) {
        // Verifica se o usuário possui permissão para criar lote
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para criar lotes");

        Client client;

        ClientUser clientUser = this.clientUserService.getByIdOrNull(dto.getUserId());
        client = clientUser == null ? this.clientService.getByIdOrNull(dto.getUserId()) : this.clientService.getByIdOrNull(clientUser.getClientId());
        if(client == null) throw new EntityNotFoundException("Cliente não encontrado com base no id do usuário informado");
        String userName = clientUser == null ? client.getName() : clientUser.getName();

        // Verifica se já existe um lote criado com o nome informado pro cliente
        Optional<Lot> lotExistis = this.lotRepository.findExistsLot(dto.getName(), client.getCnpj(), dto.getType());
        if(lotExistis.isPresent())
            throw new EntityExistsException(String.format("Lote com nome '%s' já cadastrado", dto.getName()));

        Lot lot = new Lot();
        lot.setUserId(dto.getUserId());
        lot.setUserCnpj(client.getCnpj());
        lot.setName(dto.getName());
        lot.setType(dto.getType());
        lot.setStatus(LotStatus.INCOMPLETED);

        Lot saved = this.lotRepository.save(lot);

        String userRole = this.provider.getAuthenticatedUserRole();
        Boolean createdByComputex = userRole != null && userRole.equals("COMPUTEX");

        LotResponseDto responseDto = LotMapper.toDto(saved);
        responseDto.setUserName(userName);
        responseDto.setNumberImages(0);
        responseDto.setCreatedByComputex(createdByComputex);

        // Log
        String details = String.format(
                "Novo lote cadastrado no sistema | Dados do lote cadastrado: %s",
                this.formatLotForLog(responseDto)
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.CREATE, AuditProgram.LOT, details);
        this.auditLogService.create(logDto);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public Page<LotResponseDto> getAllPageable(
            Pageable pageable,
            String filterName,
            String filterClientUser,
            String filterClient,
            LotStatus status
    ) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        UUID authUserId = this.provider.getAuthenticatedUserId();
        String authUserRole = this.provider.getAuthenticatedUserRole();
        Boolean isClient = this.provider.authenticatedUserIsClient();
        Boolean isClientUser = this.provider.authenticatedUserIsClientUser();
        Client authUserClient = this.provider.getClientAuthenticatedUser();

        // Inicia as variáveis necessárias
        Page<LotProjection> lots;
        String clientCnpj = authUserClient.getCnpj();

        // Parametros para filtragem
        String nameParam = (filterName == null || filterName.isBlank()) ? null : "%" + filterName + "%";
        String clientUserParam = (filterClientUser == null || filterClientUser.isBlank()) ? null : "%" + filterClientUser + "%";
        String clientParam = (filterClient == null || filterClient.isBlank()) ? null : "%" + filterClient + "%";

        if(authUserRole.equals("COMPUTEX")) lots = this.lotRepository.findAllPageableComputex(pageable, nameParam, clientParam, status);
        else if(isClient) lots = this.lotRepository.findAllPageableClient(pageable, nameParam, clientCnpj, clientUserParam, status);
        else if(isClientUser) lots = this.lotRepository.findAllPageableClientUser(pageable, nameParam, clientCnpj, authUserId, status);
        else throw new RuntimeException("Cargo do usuário na qual está buscando os lotes não encontrado");

        return lots.map(lot -> {
            // Define o número de imagens do lote
            Integer numberImagesLot = this.lotImageService.getAllImagesLot(lot.getId()).size();

            // Define o nome e o cargo do usuário que criou o lote
            String userName = null;
            String userRole = null;
            Client client = this.clientService.getByIdOrNull(lot.getUserId());
            if(client != null) {
                userName = client.getName();
                userRole = this.provider.getAuthenticatedUserRole(client.getId());
            } else {
                ClientUser clientUser = this.clientUserService.getByIdOrNull(lot.getUserId());
                if(clientUser != null) {
                    userName = clientUser.getName();
                    userRole = this.provider.getAuthenticatedUserRole(clientUser.getClientId());
                }
            }

            Boolean createdByComputex = userRole != null && userRole.equals("COMPUTEX");

            LotResponseDto dto = LotMapper.toPageableDto(lot);
            dto.setNumberImages(numberImagesLot);
            dto.setUserName(userName);
            dto.setCreatedByComputex(createdByComputex);
            return dto;
        });
    }

    @Transactional(readOnly = true)
    public Lot getLotById(Long id) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        return this.lotRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Lote com id '%s' não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public LotResponseDto getLotByIdDto(Long id) {
        Lot lot = this.getLotById(id);

        Client client = this.clientService.getByIdOrNull(lot.getUserId());
        ClientUser clientUser = this.clientUserService.getByIdOrNull(lot.getUserId());
        String userName = client != null ? client.getName() : clientUser.getName();

        Integer numberImages = this.lotImageService.getAllImagesLot(lot.getId()).size();

        String userRole = this.provider.getAuthenticatedUserRole();
        Boolean createdByComputex = userRole != null && userRole.equals("COMPUTEX");

        LotResponseDto responseDto = LotMapper.toDto(lot);
        responseDto.setUserName(userName);
        responseDto.setNumberImages(numberImages);
        responseDto.setCreatedByComputex(createdByComputex);

        return responseDto;
    }

    @Transactional(readOnly = false)
    public void update(Long lotId, LotUpdateDto dto) {
        // Verifica se o usuário possui permissão para atualizar lote
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para atualizar lotes");

        Lot lot = this.getLotById(lotId);

        if(dto.getName() != null) lot.setName(dto.getName());
        if(dto.getStatus() != null) lot.setStatus(dto.getStatus());

        Lot updated = this.lotRepository.save(lot);

        // Log
        String details;
        if(dto.getStatus() != null && dto.getStatus() == LotStatus.COMPLETED) {
            details = String.format(
                    "Lote com ID: %s finalizado pelo usuário",
                    updated.getId()
            );
        }else {
            details = String.format(
                "Lote atualizado com ID: %s | Novos dados -> Nome: %s | Status: %s",
                updated.getId(), updated.getName(), updated.getStatus()
            );
        }
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.UPDATE, AuditProgram.LOT, details);
        this.auditLogService.create(logDto);
    }

    @Transactional(readOnly = false)
    public void delete(Long lotId) {
        // Verifica se o usuário possui permissão para excluir lote
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getUpload_files()) throw new RuntimeException("Usuário sem autorização para excluir lotes");

        Lot lot = this.getLotById(lotId);
        lot.setStatus(LotStatus.DELETED);
        Lot updated = this.lotRepository.save(lot);

        String details = String.format(
            "Lote com ID: %s teve o status alterado para EXCLUÍDO (exclusão lógica).", updated.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.DELETE, AuditProgram.LOT, details);
        this.auditLogService.create(logDto);
    }

    // Retorna o campo 'image_active_days' do cliente/usuário que criou o lote
    @Transactional(readOnly = true)
    public Integer getImageActiveDaysFromLotId(Long lotId) {
        Lot lot = this.getLotById(lotId);
        Client client = this.clientService.getByCnpj(lot.getUserCnpj(), ClientStatus.ACTIVE);
        return client.getImageActiveDays();
    }

    // Retorna o cargo do cliente/usuário que criou o lote
    @Transactional(readOnly = true)
    public String getRoleFromLotId(Long lotId) {
        Lot lot = this.getLotById(lotId);
        UUID userId = lot.getUserId();
        return this.provider.getAuthenticatedUserRole(userId);
    }

    private void exportData(String urlToPost, LotResponseDto lot, String json) {
        try {
            // Cria o request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlToPost))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // Envia de forma assíncrona (não bloqueia)
            HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.discarding());

            // Confirma o envio, não se importando com o resultado
            String details = String.format(
                    "POST para '%s' disparado para baixar o .txt do lote contendo as seguintes informações: %s, Dados enviados: %s",
                    urlToPost, this.formatLotForLog(lot), json
            );
            AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.DOWNLOADTXT, AuditProgram.LOT, details);
            this.auditLogService.create(logDto);
        } catch (Exception e) {
            String details = String.format(
                    "Erro ao preparar POST para a url '%s' ao baixar o .txt do lote contendo as seguintes informações: %s. ERRO: %s",
                    urlToPost, this.formatLotForLog(lot), e.getMessage()
            );
            AuditLogCreateDto logDto = new AuditLogCreateDto(
                    AuditAction.DOWNLOADTXT, AuditProgram.LOT, details
            );
            this.auditLogService.create(logDto);
        }
    }

    private void exportAbcdeData(String urlToPost, LotResponseDto lot, ExportAbcdeDataDto dto) {
        // Converte para JSON
        String json;
        try {
            json = objectMapper.writeValueAsString(dto);
            // System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao conveter ExportAbcdeDataDto em JSON");
        }
        this.exportData(urlToPost, lot, json);
    }

    private void exportVtbData(String urlToPost, LotResponseDto lot, ExportVtbDataDto dto) {
        String json;
        try {
            json = objectMapper.writeValueAsString(dto);
            // System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao conveter ExportVtbDataDto em JSON");
        }
        this.exportData(urlToPost, lot, json);
    }

    public void exportDataToEndpoint(Long lotId) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        // Busca as informações do lote e suas imagens
        LotResponseDto lot = this.getLotByIdDto(lotId);
        LotType type = lot.getType();
        List<LotImage> lotImages = this.lotImageService.getAllImagesLot(lotId);

        // Verifica se o lote está finalizado
        if(lot.getStatus() != LotStatus.COMPLETED) throw new RuntimeException("Para enviar as informações a URL informada, o lote deve está concluído");

        // Verifica se o cliente possui URL informada
        Client client = this.clientService.getByCnpj(lot.getUserCnpj(), ClientStatus.ACTIVE);
        String urlToPost = client.getUrlToPost();
        if(urlToPost == null || urlToPost.isBlank()) throw new RuntimeException("O cliente na qual pertence este lote não possui URL informada");

        if(type == LotType.ABCDE) {
            List<ExportAbcdeImagesDto> exportAbcdeImagesDtos = new ArrayList<>();
            for (LotImage lotImage : lotImages) {
                ImageInfoAbcdeResponseDto imageInfo = this.lotImageService.getImageAbcdeInfo(lotImage.getId());
                List<LotImageQuestionResponseDto> questions = this.lotImageService.getAllQuestionsLotImage(lotImage.getId());
                exportAbcdeImagesDtos.add(ExportMapper.toExportAbcdeImageDto(lotImage, imageInfo, questions));
            }
            ExportAbcdeDataDto exportAbcdeDataDto = ExportMapper.toExportAbcdeDto(lot, exportAbcdeImagesDtos);
            this.exportAbcdeData(urlToPost, lot, exportAbcdeDataDto);
        } else {
            List<ExportVtbImagesDto> exportVtbImagesDtos = new ArrayList<>();
            for (LotImage lotImage : lotImages) {
                ImageInfoVtbResponseDto imageInfo = this.lotImageService.getImageVtbInfo(lotImage.getId());
                List<LotImageQuestionResponseDto> questions = this.lotImageService.getAllQuestionsLotImage(lotImage.getId());
                exportVtbImagesDtos.add(ExportMapper.toExportVtbImageDto(lotImage, imageInfo, questions));
            }
            ExportVtbDataDto exportVtbDataDto = ExportMapper.toExportVtbDto(lot, exportVtbImagesDtos);
            this.exportVtbData(urlToPost, lot, exportVtbDataDto);
        }
    }

    public byte[] generateTxt(Long lotId) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        // Verifica se o lote é do tipo ABCDE
        Lot lot = this.getLotById(lotId);
        if(lot.getType() != LotType.ABCDE) throw new RuntimeException("O .txt só pode ser gerado caso o lote seja do tipo ABCDE");

        // Verifica se o lote está finalizado
        if(lot.getStatus() != LotStatus.COMPLETED) throw new RuntimeException("Para baixar o .txt, o lote deve está concluído");

        List<LotImage> lotImages = this.lotImageService.getAllImagesLot(lotId);

        StringBuilder content = new StringBuilder("imagem,matricula");

        // Sempre seta 90 colunas para as questões
        for (int i = 1; i <= 90; i++) {
            content.append(",").append(i);
        }

        for (LotImage lotImage : lotImages) {
            // Busca as informações da imagem
            ImageInfoAbcdeResponseDto imageInfo = this.lotImageService.getImageAbcdeInfo(lotImage.getId());
            String matricula = String.format("%08d", lotImage.getMatricula()); // 8 dígitos com 0 à esquerda
            String etapa = imageInfo.getEtapa();
            String prova = String.format("%02d", imageInfo.getProva());         // 2 dígitos com 0 à esquerda
            String gabarito = imageInfo.getGabarito();
            Integer presenca = lotImage.getPresenca();                         // 1 ou 0
            List<LotImageQuestionResponseDto> questions = this.lotImageService.getAllQuestionsLotImage(lotImage.getId());

            // Linha: nome da imagem + dados do QR Code
            String linha = "\n" + imageInfo.getOriginalName() + "," +
                    matricula + etapa + prova + gabarito + presenca;

            content.append(linha);

            // Adiciona respostas das questões se o aluno está presente, caso contrário somente as vírgulas
            if(presenca == 1) {
                for (LotImageQuestionResponseDto question : questions) {
                    String alt = question.getAlternative();
                    String alternative = (alt != null && alt.length() == 1) ? alt : "W";
                    content.append(",").append(alternative);
                }

                // Completa com 'Z' o que falta
                int quantidadeRespondida = questions.size();
                int quantidadeFaltante = 90 - quantidadeRespondida;

                content.append(",Z".repeat(Math.max(0, quantidadeFaltante)));
            }else {
                content.append(",".repeat(90));
            }
        }

        return content.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] generateDat(Long lotId) {
        // Verifica se o usuário possui permissão para visualizar lotes
        PermissionResponseDto userPermission = this.provider.getAuthenticatedUserPermissions();
        if(!userPermission.getRead_files()) throw new RuntimeException("Usuário sem autorização para visualizar lotes");

        // Verifica se o lote é do tipo VTB
        Lot lot = this.getLotById(lotId);
        if(lot.getType() != LotType.VTB) throw new RuntimeException("O .dat só pode ser gerado caso o lote seja do tipo VTB");

        // Verifica se o lote está finalizado
        if(lot.getStatus() != LotStatus.COMPLETED) throw new RuntimeException("Para baixar o .dat, o lote deve está concluído");

        List<LotImage> lotImages = this.lotImageService.getAllImagesLot(lotId);

        StringBuilder content = new StringBuilder();

        for (LotImage lotImage : lotImages) {
            // Busca as informações da imagem
            ImageInfoVtbResponseDto imageInfo = this.lotImageService.getImageVtbInfo(lotImage.getId());

            String matricula = String.format("%08d", lotImage.getMatricula()); // 8 dígitos com 0 à esquerda
            String vtbFracao = String.format("%2s", imageInfo.getVtbFracao()).replace(' ', '0'); // 2 dígitos com 0 à esquerda
            String faseGab = imageInfo.getFaseGab().toString();
            String prova = imageInfo.getProva().toString();
            Integer presenca = lotImage.getPresenca();
            List<LotImageQuestionResponseDto> questions = this.lotImageService.getAllQuestionsLotImage(lotImage.getId());

            String linha = matricula + vtbFracao + faseGab + prova + presenca;
            content.append(linha);

            // Adiciona respostas das questões se o aluno está presente, caso contrário somente as vírgulas
            if(presenca == 1) {
                for (LotImageQuestionResponseDto question : questions) {
                    String alt = question.getAlternative();
                    String alternative = (alt != null && alt.length() == 1) ? alt : "W";
                    content.append(",").append(alternative);
                }

                // Completa com 'Z' o que falta
                int quantidadeRespondida = questions.size();
                int quantidadeFaltante = 90 - quantidadeRespondida;

                content.append(",Z".repeat(Math.max(0, quantidadeFaltante)));
            }else {
                content.append(",".repeat(90));
            }
        }

        return content.toString().getBytes(StandardCharsets.UTF_8);
    }
}
