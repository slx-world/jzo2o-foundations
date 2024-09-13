package com.jzo2o.foundations.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.request.ServeUpsertReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;

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


}
