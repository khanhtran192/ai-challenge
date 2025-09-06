package com.mbbank.biz.pro.service.mapper;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.domain.DocumentShare;
import com.mbbank.biz.pro.service.dto.AppUserDTO;
import com.mbbank.biz.pro.service.dto.DocumentDTO;
import com.mbbank.biz.pro.service.dto.DocumentShareDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentShare} and its DTO {@link DocumentShareDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentShareMapper extends EntityMapper<DocumentShareDTO, DocumentShare> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    DocumentShareDTO toDto(DocumentShare s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
