package com.lcsz.abcde.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcsz.abcde.dtos.email.EmailCreateDto;
import com.lcsz.abcde.models.Email;
import com.lcsz.abcde.repositorys.projection.EmailProjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Email createToEntity(EmailCreateDto createDto, String senderEmail) {
        Email email = new Email();

        try {
            email.setIdClient(createDto.getIdClient());
            email.setIdClientUser(createDto.getIdClientUser());
            email.setType(createDto.getType());
            email.setSubject(createDto.getSubject());
            email.setOrigin(senderEmail);
            email.setDestiny(createDto.getDestiny());
            email.setDestinyName(createDto.getDestinyName());
            email.setTemplateId(createDto.getTemplateId());
            email.setStatusCode(null);
            email.setAppointmentDate(createDto.getAppointmentDate());

            // Se estiver nulo ou vazio, seta uma lista vazia
            List<String> attachments = createDto.getAttachments() == null || createDto.getAttachments().isEmpty() ? new ArrayList<>() : createDto.getAttachments();
            email.setAttachments(attachments);

            // Se estiver nulo ou vazio, seta um mapa vazio
            Map<String, Object> templateFields = createDto.getTemplateFields() == null || createDto.getTemplateFields().isEmpty() ? new HashMap<>() : createDto.getTemplateFields();
            email.setTemplateFields(templateFields);
        } catch (Exception e) {
            System.err.println("Erro ao converter createDto: " + e.getMessage());
            e.printStackTrace();
        }

        return email;
    }

    public static Email projectionToEntity(EmailProjection projection) {
        Email email = new Email();

        try {
            email.setId(projection.getId());
            email.setCreatedAt(projection.getCreatedAt());
            email.setUpdatedAt(projection.getUpdatedAt());
            email.setIdClient(projection.getIdClient());
            email.setIdClientUser(projection.getIdClientUser());
            email.setType(projection.getType());
            email.setSubject(projection.getSubject());
            email.setOrigin(projection.getOrigin());
            email.setDestiny(projection.getDestiny());
            email.setDestinyName(projection.getDestinyName());
            email.setStatusCode(projection.getStatusCode());
            email.setTemplateId(projection.getTemplateId());
            email.setAppointmentDate(projection.getAppointmentDate());

            // ---------- attachments (String JSON -> List<String>) ----------
            String attachmentsJson = projection.getAttachments();
            if (attachmentsJson != null && !attachmentsJson.isBlank()) {
                try {
                    // Primeiro tentamos ler como lista genérica
                    List<Object> rawList = objectMapper.readValue(
                            attachmentsJson,
                            new TypeReference<List<Object>>() {}
                    );

                    List<String> attachments = new ArrayList<>();
                    for (Object item : rawList) {
                        if (item == null) continue;

                        if (item instanceof String s) {
                            attachments.add(s);
                        } else if (item instanceof List<?> innerList) {
                            // caso raro: ['[["a.png","b.pdf"]]'] ou similar — achata a lista interna
                            for (Object inner : innerList) {
                                if (inner != null) attachments.add(inner.toString());
                            }
                        } else {
                            // fallback: converte qualquer outro tipo para string
                            attachments.add(item.toString());
                        }
                    }

                    email.setAttachments(attachments);
                } catch (Exception ex) {
                    // se falhar no parse, seta lista vazia (ou poderia setar null se preferir)
                    System.err.println("Falha ao parsear attachments JSON: " + ex.getMessage());
                    email.setAttachments(new ArrayList<>());
                }
            } else {
                // sem attachments -> lista vazia (mais seguro para envio)
                email.setAttachments(new ArrayList<>());
            }

            // ---------- templateFields (String JSON -> Map<String,Object>) ----------
            String templateFieldsJson = projection.getTemplateFields();
            if (templateFieldsJson != null && !templateFieldsJson.isBlank()) {
                try {
                    Map<String, Object> fieldsMap = objectMapper.readValue(
                            templateFieldsJson,
                            new TypeReference<Map<String, Object>>() {}
                    );
                    email.setTemplateFields(fieldsMap);
                } catch (Exception ex) {
                    System.err.println("Falha ao parsear templateFields JSON: " + ex.getMessage());
                    email.setTemplateFields(null);
                }
            } else {
                email.setTemplateFields(null);
            }

        } catch (Exception e) {
            System.err.println("Erro ao converter EmailProjection: " + e.getMessage());
            e.printStackTrace();
        }

        return email;
    }
}
