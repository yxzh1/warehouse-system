package com.wms.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.pojo.Goods;
import com.wms.pojo.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-08-08
 */
public interface RecordService extends IService<Record> {
    IPage pageC(IPage<Record> page, Wrapper wrapper);
}
