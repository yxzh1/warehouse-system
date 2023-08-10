package com.wms.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.pojo.Record;
import com.wms.Mapper.RecordMapper;
import com.wms.Service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2023-08-08
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;
    @Override
    public IPage pageC(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.PageC(page,wrapper);
    }
}
