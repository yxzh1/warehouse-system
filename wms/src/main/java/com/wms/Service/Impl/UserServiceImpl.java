package com.wms.Service.Impl;

import com.wms.pojo.User;
import com.wms.Mapper.UserMapper;
import com.wms.Service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2023-08-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listAll() {
        return userMapper.selectAll();
    }
}
