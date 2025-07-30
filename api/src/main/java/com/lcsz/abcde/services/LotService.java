package com.lcsz.abcde.services;

import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.lot.LotCreateDto;
import com.lcsz.abcde.dtos.lot.LotResponseDto;
import com.lcsz.abcde.dtos.lot.LotUpdateDto;
import com.lcsz.abcde.dtos.lotImageQuestion.LotImageQuestionResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.enums.lot.LotStatus;
import com.lcsz.abcde.exceptions.customExceptions.EntityExistsException;
import com.lcsz.abcde.exceptions.customExceptions.EntityNotFoundException;
import com.lcsz.abcde.mappers.LotMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.ClientUser;
import com.lcsz.abcde.models.Lot;
import com.lcsz.abcde.models.LotImage;
import com.lcsz.abcde.repositorys.LotRepository;
import com.lcsz.abcde.repositorys.projection.LotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
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

    LotService(LotRepository lotRepository, LotImageService lotImageService, ClientService clientService, ClientUserService clientUserService, AuditLogService auditLogService) {
        this.lotRepository = lotRepository;
        this.lotImageService = lotImageService;
        this.clientService = clientService;
        this.clientUserService = clientUserService;
        this.auditLogService = auditLogService;
    }

    public String formatLotForLog(LotResponseDto dto) {
        return String.format("{id='%s', nome='%s'}",
                dto.getId(), dto.getName());
    }

    @Transactional(readOnly = false)
    public LotResponseDto create(LotCreateDto dto) {
        Client client;

        ClientUser clientUser = this.clientUserService.getByIdOrNull(dto.getUserId());
        client = clientUser == null ? this.clientService.getByIdOrNull(dto.getUserId()) : this.clientService.getByIdOrNull(clientUser.getClientId());
        if(client == null) throw new EntityNotFoundException("Cliente não encontrado com base no id do usuário informado");
        String userName = clientUser == null ? client.getName() : clientUser.getName();

        // Verifica se já existe um lote criado com o nome informado pro cliente
        Optional<Lot> lotExistis = this.lotRepository.findByNameAndUserId(dto.getName(), client.getCnpj());
        if(lotExistis.isPresent())
            throw new EntityExistsException(String.format("Lote com nome '%s' já cadastrado", dto.getName()));

        Lot lot = new Lot();
        lot.setUserId(dto.getUserId());
        lot.setUserCnpj(client.getCnpj());
        lot.setName(dto.getName());
        lot.setStatus(LotStatus.INCOMPLETED);

        Lot saved = this.lotRepository.save(lot);

        LotResponseDto responseDto = LotMapper.toDto(saved);
        responseDto.setUserName(userName);
        responseDto.setNumberImages(0);

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
            LotStatus status,
            UUID authUserId
    ) {
        // Inicia as variáveis necessárias
        final String COMPUTEX_CNPJ = "12302493000101";
        Page<LotProjection> lots;
        String clientCnpj;
        boolean isComputexUser;
        boolean isClient;

        // Parametros para filtragem
        String nameParam = (filterName == null || filterName.isBlank()) ? null : "%" + filterName + "%";
        String clientUserParam = (filterClientUser == null || filterClientUser.isBlank()) ? null : "%" + filterClientUser + "%";
        String clientParam = (filterClient == null || filterClient.isBlank()) ? null : "%" + filterClient + "%";

        // Verifica o tipo e seta as variáveis necessárias
        ClientUser clientUser = this.clientUserService.getByIdOrNull(authUserId);
        if (clientUser != null) {
            Client client = this.clientService.getByIdOrNull(clientUser.getClientId());

            clientCnpj = client.getCnpj();
            isComputexUser = COMPUTEX_CNPJ.equals(client.getCnpj());
            isClient = false;
        }else {
            Client client = this.clientService.getByIdOrNull(authUserId);

            clientCnpj = client.getCnpj();
            isComputexUser = COMPUTEX_CNPJ.equals(client.getCnpj());
            isClient = true;
        }

        if(isComputexUser) lots = this.lotRepository.findAllPageableComputex(pageable, nameParam, clientParam, status);
        else if(isClient) lots = this.lotRepository.findAllPageableClient(pageable, nameParam, clientCnpj, clientUserParam, status);
        else lots = this.lotRepository.findAllPageableClientUser(pageable, nameParam, clientCnpj, authUserId, status);

        return lots.map(lot -> {
           return this.getLotByIdDto(lot.getId());
        });
    }

    @Transactional(readOnly = true)
    public Lot getLotById(Long id) {
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

        LotResponseDto responseDto = LotMapper.toDto(lot);
        responseDto.setUserName(userName);
        responseDto.setNumberImages(numberImages);

        return responseDto;
    }

    @Transactional(readOnly = false)
    public void update(Long lotId, LotUpdateDto dto) {
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
        Lot lot = this.getLotById(lotId);
        lot.setStatus(LotStatus.DELETED);
        Lot updated = this.lotRepository.save(lot);

        String details = String.format(
            "Lote com ID: %s teve o status alterado para DELETED (exclusão lógica).", updated.getId()
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.DELETE, AuditProgram.LOT, details);
        this.auditLogService.create(logDto);
    }

    public byte[] generateTxt(Long lotId) {
        Lot lot = this.getLotById(lotId);
        List<LotImage> lotImages = this.lotImageService.getAllImagesLot(lotId);

        StringBuilder content = new StringBuilder("imagem,matricula");

        // Sempre seta 90 colunas para as questões
        for (int i = 1; i <= 90; i++) {
            content.append(",").append(i);
        }

        for (LotImage lotImage : lotImages) {
            String matricula = String.format("%08d", lotImage.getMatricula()); // 8 dígitos com 0 à esquerda
            String etapa = lotImage.getEtapa();
            String prova = String.format("%02d", lotImage.getProva());         // 2 dígitos com 0 à esquerda
            String gabarito = lotImage.getGabarito();
            Integer presenca = lotImage.getPresenca();                         // 1 ou 0

            // Linha: nome da imagem + dados do QR Code
            String linha = "\n" + lotImage.getOriginalName() + "," +
                    matricula + etapa + prova + gabarito + presenca;

            content.append(linha);

            // Adiciona respostas das questões se o aluno está presente, caso contrário somente as vírgulas
            if(presenca == 1) {
                List<LotImageQuestionResponseDto> questions = this.lotImageService.getAllQuestionsLotImage(lotImage.getId());
                for (LotImageQuestionResponseDto question : questions) {
                    String alt = question.getAlternative();
                    String alternative = (alt != null && alt.length() == 1) ? alt : "W";
                    content.append(",").append(alternative);
                }

                // Completa com 'Z' o que falta
                int quantidadeRespondida = questions.size();
                int quantidadeFaltante = 90 - quantidadeRespondida;

                for (int i = 0; i < quantidadeFaltante; i++) {
                    content.append(",Z");
                }
            }else {
                content.append(",".repeat(90));
            }

        }

        return content.toString().getBytes(StandardCharsets.UTF_8);
    }
}
