package com.jzo2o.foundations.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.mapper.ServeMapper;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;
import com.jzo2o.foundations.service.IServeService;
import com.jzo2o.mysql.utils.PageHelperUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: Song Laixiong
 * @Create: 2024-09-13
 * @Description:
 */

@Service
public class ServeServiceImpl extends ServiceImpl<ServeMapper, Serve> implements IServeService {

    /**
     *  服务分页查询
     * @param servePageQueryReqDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<ServeResDTO> page(ServePageQueryReqDTO servePageQueryReqDTO) {
        //调用mapper查询数据，这里由于继承了ServiceImpl<ServeMapper, Serve>，使用baseMapper相当于使用ServeMapper
        PageResult<ServeResDTO> serveResDTOPageResult = PageHelperUtils.selectPage(servePageQueryReqDTO, () -> baseMapper.queryServeListByRegionId(servePageQueryReqDTO.getRegionId()));
        return serveResDTOPageResult;
    }


}
