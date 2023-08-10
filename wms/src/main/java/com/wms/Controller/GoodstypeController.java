package com.wms.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.Service.GoodstypeService;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.pojo.Goodstype;
import com.wms.pojo.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-08-07
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {
    @Autowired
    private GoodstypeService goodstypeService;

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody Goodstype goodstype){
        return goodstypeService.saveOrUpdate(goodstype) ? Result.suc() : Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype){
        return goodstypeService.save(goodstype)? Result.suc() : Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(Integer id){
        return goodstypeService.removeById(id) ? Result.suc() : Result.fail();
    }
    //查询
    @PostMapping("/listPageP")
    public Result listPageP(@RequestBody QueryPageParam queryPageParam){
//        Page<User> page = new Page<>(1,2);
        Page<Goodstype> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        String name = (String) queryPageParam.getParam().get("name");
        String remark = (String) queryPageParam.getParam().get("remark");

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Goodstype::getName, name);
        }
        if(StringUtils.isNotBlank(remark) && !"null".equals(remark)) {
            lambdaQueryWrapper.eq(Goodstype::getRemark, remark);
        }
        IPage result = goodstypeService.page(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
    @GetMapping("findByName")
    public Result findByName(String name){
        List<Goodstype> list = goodstypeService.lambdaQuery().eq(Goodstype::getName, name).list();
        return list.size()>0 ? Result.suc(list) : Result.fail();
    }
    @GetMapping("list")
    public Result list(){
        List<Goodstype> list = goodstypeService.list();
        return list.size()>0 ? Result.suc(list) : Result.fail();
    }
}
