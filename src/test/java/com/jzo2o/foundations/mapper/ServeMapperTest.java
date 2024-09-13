package com.jzo2o.foundations.mapper;

import com.jzo2o.foundations.model.dto.response.ServeResDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Song Laixiong
 * @Create: 2024-09-13
 * @Description:
 */

@SpringBootTest
@Slf4j
public class ServeMapperTest {

    @Resource
    private ServeMapper serveMapper;

    @Test
    public void test_queryServeListByRegionId(){
        List<ServeResDTO> serveResDTOS = serveMapper.queryServeListByRegionId(1692010607909900289L);
        Assert.notEmpty(serveResDTOS, "查询服务列表为空");
        log.info("serveResDTOS:{}",serveResDTOS);
    }

}
