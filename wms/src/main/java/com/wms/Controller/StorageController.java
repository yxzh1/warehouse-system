package com.wms.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.Service.StorageService;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.pojo.Goods;
import com.wms.pojo.Storage;
import com.wms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody Storage storage){
        return storageService.saveOrUpdate(storage) ? Result.suc() : Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){
        return storageService.save(storage)? Result.suc() : Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage){
        return storageService.updateById(storage) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(Integer id){
        return storageService.removeById(id) ? Result.suc() : Result.fail();
    }
    //查询
    @PostMapping("/listPageP")
    public Result listPageP(@RequestBody QueryPageParam queryPageParam){
//        Page<User> page = new Page<>(1,2);
        Page<Storage> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        String name = (String) queryPageParam.getParam().get("name");
        String remark = (String) queryPageParam.getParam().get("remark");

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Storage::getName, name);
        }
        if(StringUtils.isNotBlank(remark) && !"null".equals(remark)) {
            lambdaQueryWrapper.eq(Storage::getRemark, remark);
        }
        IPage result = storageService.page(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
    @GetMapping("findByName")
    public Result findByName(String name){
        List<Storage> list = storageService.lambdaQuery().eq(Storage::getName, name).list();
        return list.size()>0 ? Result.suc(list) : Result.fail();
    }
    @GetMapping("list")
    public Result list(){
        List<Storage> list = storageService.list();
        return list.size()>0 ? Result.suc(list) : Result.fail();
    }
}
