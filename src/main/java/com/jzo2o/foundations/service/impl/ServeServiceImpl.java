package com.jzo2o.foundations.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.expcetions.ForbiddenOperationException;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.enums.FoundationStatusEnum;
import com.jzo2o.foundations.mapper.RegionMapper;
import com.jzo2o.foundations.mapper.ServeItemMapper;
import com.jzo2o.foundations.mapper.ServeMapper;
import com.jzo2o.foundations.model.domain.Region;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.domain.ServeItem;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.request.ServeUpsertReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;
import com.jzo2o.foundations.service.IServeService;
import com.jzo2o.mysql.utils.PageHelperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Song Laixiong
 * @Create: 2024-09-13
 * @Description:
 */

@Service
public class ServeServiceImpl extends ServiceImpl<ServeMapper, Serve> implements IServeService {

    @Resource
    private ServeItemMapper serveItemMapper;

    @Resource
    private RegionMapper regionMapper;

    /**
     *  区域服务分页查询
     * @param servePageQueryReqDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<ServeResDTO> page(ServePageQueryReqDTO servePageQueryReqDTO) {
        // 调用mapper查询数据，这里由于继承了ServiceImpl<ServeMapper, Serve>，使用baseMapper相当于使用ServeMapper
        PageResult<ServeResDTO> serveResDTOPageResult = PageHelperUtils.selectPage(servePageQueryReqDTO, () -> baseMapper.queryServeListByRegionId(servePageQueryReqDTO.getRegionId()));
        return serveResDTOPageResult;
    }

    /**
     *  区域服务批量添加
     * @param serveUpsertReqDTOList 批量添加的服务信息
     */
    @Transactional
    @Override
    public void batchAdd(List<ServeUpsertReqDTO> serveUpsertReqDTOList) {
        for (ServeUpsertReqDTO serveUpsertReqDTO : serveUpsertReqDTOList) {
            // 1.校验服务项是否为启用状态，不是启用状态不能新增
            ServeItem serveItem = serveItemMapper.selectById(serveUpsertReqDTO.getServeItemId());
            // 如果服务项信息不存在或未启用
            if (ObjectUtil.isNull(serveItem) || serveItem.getActiveStatus() != FoundationStatusEnum.ENABLE.getStatus()) {
                throw new ForbiddenOperationException(serveItem.getName() + "服务项未启用，不能新增服务");
            }
            // 2.校验是否重复新增
            Integer count = lambdaQuery()
                    .eq(Serve::getRegionId, serveUpsertReqDTO.getRegionId())
                    .eq(Serve::getServeItemId, serveUpsertReqDTO.getServeItemId())
                    .count();
            if (count > 0) {
                throw new ForbiddenOperationException(serveItem.getName() + "服务已存在，不能重复新增");
            }
            // 3.新增服务
            Serve serve = BeanUtil.toBean(serveUpsertReqDTO, Serve.class);
            Region region = regionMapper.selectById(serveUpsertReqDTO.getRegionId());
            serve.setCityCode(region.getCityCode());
            baseMapper.insert(serve);
        }
    }

    /**
     *  区域服务价格修改
     * @param id 服务id
     * @param price 服务价格
     * @return 修改后的服务信息
     */
    @Transactional
    @Override
    public Serve update(Long id, BigDecimal price) {
        boolean update = lambdaUpdate()
                .eq(Serve::getId, id)
                .set(Serve::getPrice, price)
                .update();
        if (!update) {
            throw new ForbiddenOperationException("服务价格修改失败");
        }
        return baseMapper.selectById(id);
    }

    @Transactional
    @Override
    public Serve onSale(Long id) {
        Serve serve = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serve)) {
            throw new ForbiddenOperationException("区域服务不存在");
        }
        // 上架状态：0-草稿，1-启用，2-禁用
        Integer saleStatus = serve.getSaleStatus();
        if (saleStatus == FoundationStatusEnum.ENABLE.getStatus()) {
            throw new ForbiddenOperationException("服务已上架，不能重复上架");
        }

        // 草稿状态和禁用状态才能上架
        if (!(saleStatus == FoundationStatusEnum.INIT.getStatus() || saleStatus == FoundationStatusEnum.DISABLE.getStatus())) {
            throw new ForbiddenOperationException("草稿状态和禁用状态才能上架");
        }

        // 服务项id
        Long serveItemId = serve.getServeItemId();
        ServeItem serveItem = serveItemMapper.selectById(serveItemId);
        if (ObjectUtil.isNull(serveItem)) {
            throw new ForbiddenOperationException("服务项不存在");
        }

        // 服务项的启用状态
        Integer serveItemStatus = serveItem.getActiveStatus();
        if (serveItemStatus != FoundationStatusEnum.ENABLE.getStatus()) {
            throw new ForbiddenOperationException("服务项未启用，不能上架");
        }

        // 更新服务状态为上架
        boolean update = lambdaUpdate()
                .eq(Serve::getId, id)
                .set(Serve::getSaleStatus, FoundationStatusEnum.ENABLE.getStatus())
                .update();
        if (!update) {
            throw new ForbiddenOperationException("服务上架失败");
        }
        return baseMapper.selectById(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Serve serve = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serve)) {
            throw new ForbiddenOperationException("服务不存在");
        }
        if (serve.getSaleStatus() == FoundationStatusEnum.ENABLE.getStatus()) {
            throw new ForbiddenOperationException("服务已上架，不能删除");
        }
        baseMapper.deleteById(id);
    }

    @Transactional
    @Override
    public Serve offSale(Long id) {
        Serve serve = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serve)) {
            throw new ForbiddenOperationException("服务不存在");
        }
        // 服务只有上架，才能下架
        if (!(serve.getSaleStatus() == FoundationStatusEnum.ENABLE.getStatus())) {
            throw new ForbiddenOperationException("服务只有上架，才能下架");
        }
        boolean update = lambdaUpdate()
                .eq(Serve::getId, id)
                .set(Serve::getSaleStatus, FoundationStatusEnum.DISABLE.getStatus())
                .update();
        if (!update) {
            throw new ForbiddenOperationException("服务下架失败");
        }
        return baseMapper.selectById(id);
    }

    @Transactional
    @Override
    public Serve changeHotStatus(Long id, Integer flag) {
        boolean update = lambdaUpdate()
                .eq(Serve::getId, id)
                .set(Serve::getIsHot, flag)
                .set(Serve::getHotTimeStamp, System.currentTimeMillis())
                .update();
        if (!update) {
            throw new ForbiddenOperationException("服务热门状态修改失败");
        }
        return baseMapper.selectById(id);
    }

    @Override
    public int queryServeCountByRegionIdAndSaleStatus(Long regionId, Integer saleStatus) {
        Integer count = lambdaQuery()
                .eq(Serve::getRegionId, regionId)
                .eq(Serve::getSaleStatus, saleStatus)
                .count();
        return count;
    }

    @Override
    public int queryServeCountByServeItemIdAndSaleStatus(Long serveItemId, Integer saleStatus) {
        Integer count = lambdaQuery()
                .eq(Serve::getServeItemId, serveItemId)
                .eq(Serve::getSaleStatus, saleStatus)
                .count();
        return count;
    }

}
