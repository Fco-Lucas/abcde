package com.lcsz.abcde.services;

import com.lcsz.abcde.AppProperties;
import com.lcsz.abcde.dtos.auditLog.AuditLogCreateDto;
import com.lcsz.abcde.dtos.clients.ClientResponseDto;
import com.lcsz.abcde.dtos.email.EmailCreateDto;
import com.lcsz.abcde.dtos.email.EmailSendResponseDto;
import com.lcsz.abcde.enums.auditLog.AuditAction;
import com.lcsz.abcde.enums.auditLog.AuditProgram;
import com.lcsz.abcde.mappers.EmailMapper;
import com.lcsz.abcde.models.Client;
import com.lcsz.abcde.models.Email;
import com.lcsz.abcde.repositorys.EmailRepository;
import com.lcsz.abcde.repositorys.projection.EmailProjection;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailService {
    private final static Logger log = LoggerFactory.getLogger(EmailService.class);
    private final EmailRepository emailRepository;
    private final AppProperties appProperties;
    private final ClientService clientService;
    private final ClientUserService clientUserService;
    private final AuditLogService auditLogService;

    public EmailService(
            EmailRepository emailRepository,
            AppProperties appProperties,
            @Lazy ClientService clientService,
            @Lazy ClientUserService clientUserService,
            @Lazy AuditLogService auditLogService
    ) {
        this.emailRepository = emailRepository;
        this.appProperties = appProperties;
        this.clientService = clientService;
        this.clientUserService = clientUserService;
        this.auditLogService = auditLogService;
    }

    public String formatEmailForLog(Email email) {
        return String.format(
                "{id='%d', idCliente='%s', idUsuarioCliente='%s', tipo='%s', assunto='%s', origem='%s', destino='%s', nomeDestino='%s', status='%d'}",
                email.getId(),
                email.getIdClient(),
                email.getIdClientUser(),
                email.getType(),
                email.getSubject(),
                email.getOrigin(),
                email.getDestiny(),
                email.getDestinyName(),
                email.getStatusCode()
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Email create(EmailCreateDto createDto) {
        Email entity = EmailMapper.createToEntity(createDto, appProperties.getSenderEmail());
        Email saved = emailRepository.save(entity);

        // Log de criação
        String details = String.format(
                "Novo email cadastrado no sistema | Dados do email cadastrado: %s",
                this.formatEmailForLog(saved)
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(
                AuditAction.CREATE,
                createDto.getIdClient(),
                AuditProgram.EMAIL,
                details
        );
        this.auditLogService.create(logDto);

        return saved;
    }

    @Transactional(readOnly = true)
    public List<Email> getAllForSend () {
        List<EmailProjection> projections = emailRepository.findAllForSend();
        return projections.stream().map(EmailMapper::projectionToEntity).collect(Collectors.toList());
    }

    private Personalization createPersonalization(com.sendgrid.helpers.mail.objects.Email to, Map<String, Object> fields) {
        Personalization personalization = new Personalization();
        personalization.addTo(to);

        if (fields != null && !fields.isEmpty()) {
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
            }
        }

        return personalization;
    }

    private void auditSendEmail (Email email) {
        String details = String.format(
                "Email processado/enviado ao seu destino | Dados do email processado/enviado: %s",
                this.formatEmailForLog(email)
        );
        AuditLogCreateDto logDto = new AuditLogCreateDto(AuditAction.PROCESSED, email.getIdClient(), AuditProgram.EMAIL, details);
        this.auditLogService.create(logDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public EmailSendResponseDto sendForClient(Client client) {
        List<Email> emails = getAllForSend();
        if (emails.isEmpty()) {
            log.info("Nenhum email pendente para envio para o cliente {}", client.getCnpj());
            return new EmailSendResponseDto(client.getCnpj(), List.of(), List.of(), 0, 0);
        }

        log.info("Enviando {} emails para o cliente {}", emails.size(), client.getCnpj());

        SendGrid sg = new SendGrid(appProperties.getSendGridApiKey());
        int sentCount = 0;
        List<Long> successIds = new ArrayList<>();
        List<Long> failedIds = new ArrayList<>();

        for (Email email : emails) {
            try {
                Mail mail = new Mail();
                com.sendgrid.helpers.mail.objects.Email from = new com.sendgrid.helpers.mail.objects.Email(appProperties.getSenderEmail());
                com.sendgrid.helpers.mail.objects.Email to = new com.sendgrid.helpers.mail.objects.Email(email.getDestiny(), email.getDestinyName());

                mail.setFrom(from);
                mail.setSubject(email.getSubject());
                mail.addPersonalization(createPersonalization(to, email.getTemplateFields()));
                mail.setTemplateId(email.getTemplateId());

                if (email.getAttachments() != null && !email.getAttachments().isEmpty()) {
                    for (String path : email.getAttachments()) {
                        Attachments attachment = new Attachments();
                        byte[] data = Files.readAllBytes(Paths.get(path));
                        String encoded = Base64.getEncoder().encodeToString(data);
                        attachment.setContent(encoded);
                        attachment.setType(Files.probeContentType(Paths.get(path)));
                        attachment.setFilename(Paths.get(path).getFileName().toString());
                        attachment.setDisposition("attachment");
                        mail.addAttachments(attachment);
                    }
                }

                Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                Response response = sg.api(request);
                email.setStatusCode(response.getStatusCode());
                email.setUpdatedAt(LocalDateTime.now());
                log.info("Sucesso: {}", email.toString());
                emailRepository.save(email);

                if (response.getStatusCode() == 202) {
                    sentCount++;
                    successIds.add(email.getId());
                } else {
                    failedIds.add(email.getId());
                }

                auditSendEmail(email);

            } catch (IOException ex) {
                log.error("Erro ao enviar email ID {}: {}", email.getId(), ex.getMessage());
                email.setStatusCode(500);
                email.setUpdatedAt(LocalDateTime.now());
                log.info("Erro: {}", email.toString());
                emailRepository.save(email);
                failedIds.add(email.getId());
                auditSendEmail(email);
            }
        }

        return new EmailSendResponseDto(client.getCnpj(), successIds, failedIds, emails.size(), sentCount);
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5m
    public List<EmailSendResponseDto> sendEmails () {
        List<Client> clients = clientService.getAll();
        List<EmailSendResponseDto> responseDtos = new ArrayList<>();

        for (Client client : clients) {
            try {
                responseDtos.add(sendForClient(client));
            } catch (Exception ex) {
                // logar e seguir com o próximo cliente
                log.error("Erro ao enviar e-mails para o cliente {}: {}", client.getCnpj(), ex.getMessage(), ex);
            }
        }

        return responseDtos;
    }
}
