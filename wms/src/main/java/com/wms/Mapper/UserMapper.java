package com.wms.Mapper;

import com.wms.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2023-08-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    public List<User> selectAll();

}
