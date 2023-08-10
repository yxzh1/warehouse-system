package com.wms.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.Service.MenuService;
import com.wms.Service.UserService;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.pojo.Menu;
import com.wms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-08-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @GetMapping("1")
    public List<User> list(){
        return userService.list();
    }
    @GetMapping("findByNo")
    public Result findByNo(String no){
        List<User> list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size()>0 ? Result.suc(list) : Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)? Result.suc() : Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List<User> list = userService.lambdaQuery()
                .eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword()).list();

        if(list.size()>0){
            User user1 = list.get(0);
            List<Menu> list1 = menuService.lambdaQuery().like(Menu::getMenuRight, user1.getRoleId()).list();
            HashMap<String,Object> hashMap = new HashMap<String,Object>();
            hashMap.put("user",user1);
            hashMap.put("menu",list1);
            return Result.suc(hashMap);
        }
        return Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }
    //删除
    @GetMapping("/del")
    public Result del(Integer id){
        return userService.removeById(id) ? Result.suc() : Result.fail();
    }
    //查询（模糊、匹配）
    @PostMapping("/listP")
    public List<User> listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }
        return userService.list(lambdaQueryWrapper);
    }

    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam queryPageParam){
//        Page<User> page = new Page<>(1,2);
        Page<User> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        String name = (String) queryPageParam.getParam().get("name");

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page, lambdaQueryWrapper);

        return result.getRecords();
    }
    @PostMapping("/listPageP")
    public Result listPageP(@RequestBody QueryPageParam queryPageParam){
//        Page<User> page = new Page<>(1,2);
        Page<User> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        String name = (String) queryPageParam.getParam().get("name");
        String sex = (String) queryPageParam.getParam().get("sex");
        String roleId = (String) queryPageParam.getParam().get("roleId");

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }
        if(StringUtils.isNotBlank(sex) && !"null".equals(sex)) {
            lambdaQueryWrapper.eq(User::getSex, sex);
        }
        if(StringUtils.isNotBlank(roleId) && !"null".equals(roleId)) {
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }
        IPage result = userService.page(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }





}
