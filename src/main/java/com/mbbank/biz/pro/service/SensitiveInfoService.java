package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.SensitiveInfo;
import com.mbbank.biz.pro.repository.SensitiveInfoRepository;
import com.mbbank.biz.pro.service.dto.SensitiveInfoDTO;
import com.mbbank.biz.pro.service.mapper.SensitiveInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mbbank.biz.pro.domain.SensitiveInfo}.
 */
@Service
@Transactional
public class SensitiveInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(SensitiveInfoService.class);

    private final SensitiveInfoRepository sensitiveInfoRepository;

    private final SensitiveInfoMapper sensitiveInfoMapper;

    public SensitiveInfoService(SensitiveInfoRepository sensitiveInfoRepository, SensitiveInfoMapper sensitiveInfoMapper) {
        this.sensitiveInfoRepository = sensitiveInfoRepository;
        this.sensitiveInfoMapper = sensitiveInfoMapper;
    }

    /**
     * Save a sensitiveInfo.
     *
     * @param sensitiveInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public SensitiveInfoDTO save(SensitiveInfoDTO sensitiveInfoDTO) {
        LOG.debug("Request to save SensitiveInfo : {}", sensitiveInfoDTO);
        SensitiveInfo sensitiveInfo = sensitiveInfoMapper.toEntity(sensitiveInfoDTO);
        sensitiveInfo = sensitiveInfoRepository.save(sensitiveInfo);
        return sensitiveInfoMapper.toDto(sensitiveInfo);
    }

    /**
     * Update a sensitiveInfo.
     *
     * @param sensitiveInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public SensitiveInfoDTO update(SensitiveInfoDTO sensitiveInfoDTO) {
        LOG.debug("Request to update SensitiveInfo : {}", sensitiveInfoDTO);
        SensitiveInfo sensitiveInfo = sensitiveInfoMapper.toEntity(sensitiveInfoDTO);
        sensitiveInfo = sensitiveInfoRepository.save(sensitiveInfo);
        return sensitiveInfoMapper.toDto(sensitiveInfo);
    }

    /**
     * Partially update a sensitiveInfo.
     *
     * @param sensitiveInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SensitiveInfoDTO> partialUpdate(SensitiveInfoDTO sensitiveInfoDTO) {
        LOG.debug("Request to partially update SensitiveInfo : {}", sensitiveInfoDTO);

        return sensitiveInfoRepository
            .findById(sensitiveInfoDTO.getId())
            .map(existingSensitiveInfo -> {
                sensitiveInfoMapper.partialUpdate(existingSensitiveInfo, sensitiveInfoDTO);

                return existingSensitiveInfo;
            })
            .map(sensitiveInfoRepository::save)
            .map(sensitiveInfoMapper::toDto);
    }

    /**
     * Get one sensitiveInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SensitiveInfoDTO> findOne(String id) {
        LOG.debug("Request to get SensitiveInfo : {}", id);
        return sensitiveInfoRepository.findById(id).map(sensitiveInfoMapper::toDto);
    }

    /**
     * Delete the sensitiveInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete SensitiveInfo : {}", id);
        sensitiveInfoRepository.deleteById(id);
    }
}
