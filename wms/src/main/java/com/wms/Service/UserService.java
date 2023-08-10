package com.wms.Service;

import com.wms.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-08-03
 */
public interface UserService extends IService<User> {
    public List<User> listAll();
}
