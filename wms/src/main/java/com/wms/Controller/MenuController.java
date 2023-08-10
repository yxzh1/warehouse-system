package com.wms.Controller;


import com.wms.Service.MenuService;
import com.wms.Service.UserService;
import com.wms.common.Result;
import com.wms.pojo.Menu;
import com.wms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-08-06
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("list")
    public Result list(@RequestParam String roleId){
        List<Menu> list = menuService.lambdaQuery().like(Menu::getMenuRight, roleId).list();
        return list.size()>0 ? Result.suc(list) : Result.fail();
    }
}
