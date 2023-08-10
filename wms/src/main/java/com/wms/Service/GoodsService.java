package com.wms.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-08-07
 */
public interface GoodsService extends IService<Goods> {
    IPage pageC(IPage<Goods> page,Wrapper wrapper);
}
