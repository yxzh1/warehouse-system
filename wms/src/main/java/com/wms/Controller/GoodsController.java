package com.wms.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.Service.GoodsService;
import com.wms.Service.GoodstypeService;
import com.wms.Service.StorageService;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.pojo.Goods;
import com.wms.pojo.GoodsRes;
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
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody Goods goods){
        return goodsService.saveOrUpdate(goods) ? Result.suc() : Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)? Result.suc() : Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        return goodsService.updateById(goods) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(Integer id){
        return goodsService.removeById(id) ? Result.suc() : Result.fail();
    }
    //查询
    @PostMapping("/listPageP")
    public Result listPageP(@RequestBody QueryPageParam queryPageParam){
//        Page<User> page = new Page<>(queryPageParam.getPageNum(),queryPageParam.getPageSize());
        Page<Goods> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        String Goodsname = (String) queryPageParam.getParam().get("name");
        String storage =  (String)queryPageParam.getParam().get("storage");
        String goodsType =(String)queryPageParam.getParam().get("goodsType");

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("g1.storage = s.id and g1.goodstype = g2.id");
        if(StringUtils.isNotBlank(Goodsname) && !"null".equals(Goodsname)) {
            queryWrapper.like("g1.name",Goodsname);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            queryWrapper.eq("s.id",Integer.parseInt(storage));
        }
        if(StringUtils.isNotBlank(goodsType) && !"null".equals(goodsType)) {
            queryWrapper.eq("g2.id",Integer.parseInt(goodsType));
        }
        IPage result = goodsService.pageC(page, queryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
    @GetMapping("findByName")
    public Result findByName(String name){
        List<Goods> list = goodsService.lambdaQuery().eq(Goods::getName, name).list();
        return list.size()>0 ? Result.suc(list) : Result.fail();
    }
}
