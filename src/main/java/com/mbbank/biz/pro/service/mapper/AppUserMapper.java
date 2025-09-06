package com.mbbank.biz.pro.service.mapper;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.service.dto.AppUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {}
