package com.mbbank.biz.pro.service.mapper;

import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.domain.SensitiveInfo;
import com.mbbank.biz.pro.service.dto.DocumentDTO;
import com.mbbank.biz.pro.service.dto.SensitiveInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SensitiveInfo} and its DTO {@link SensitiveInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SensitiveInfoMapper extends EntityMapper<SensitiveInfoDTO, SensitiveInfo> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    SensitiveInfoDTO toDto(SensitiveInfo s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
