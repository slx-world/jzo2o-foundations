package com.jzo2o.foundations.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.request.ServeUpsertReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Song Laixiong
 * @Create: 2024-09-13
 * @Description: 服务类
 */

public interface IServeService extends IService<Serve> {

    /**
     * 服务分页查询
     * @param servePageQueryReqDTO 查询条件
     * @return 分页结果
     */
    PageResult<ServeResDTO> page(ServePageQueryReqDTO servePageQueryReqDTO);

    /**
     *  批量添加服务
     * @param serveUpsertReqDTOList 批量添加的服务信息
     */
    void batchAdd(List<ServeUpsertReqDTO> serveUpsertReqDTOList);

    /**
     * 服务价格修改
     * @param id 服务id
     * @param price 服务价格
     * @return 服务信息
     */
    Serve update(Long id, BigDecimal price);

    /**
     * 服务上架
     * @param id 服务id
     * @return 服务信息
     */
    Serve onSale(Long id);

    /**
     * 区域服务删除
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 服务下架
     * @param id 服务id
     * @return 服务信息
     */
    Serve offSale(Long id);

    /**
     * 服务设置/取消热门
     * @param id 服务id
     * @param flag 是否为热门，0：非热门，1：热门
     * @return
     */
    Serve changeHotStatus(Long id, Integer flag);

    /**
     * 根据区域id和上架状态查询服务数量
     * @param regionId 区域id
     * @param saleStatus 上架状态
     * @return 服务数量
     */
    int queryServeCountByRegionIdAndSaleStatus(Long regionId, Integer saleStatus);

    /**
     * 根据服务项id和上架状态查询服务数量
     * @param serveItemId 服务项id
     * @param saleStatus 上架状态
     * @return 服务数量
     */
    int queryServeCountByServeItemIdAndSaleStatus(Long serveItemId, Integer saleStatus);

}
