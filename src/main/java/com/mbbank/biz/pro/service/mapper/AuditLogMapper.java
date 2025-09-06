package com.mbbank.biz.pro.service.mapper;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.AuditLog;
import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.service.dto.AppUserDTO;
import com.mbbank.biz.pro.service.dto.AuditLogDTO;
import com.mbbank.biz.pro.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditLog} and its DTO {@link AuditLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuditLogMapper extends EntityMapper<AuditLogDTO, AuditLog> {
    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    AuditLogDTO toDto(AuditLog s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
