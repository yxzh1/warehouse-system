package com.wms.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.Service.GoodsService;
import com.wms.Service.RecordService;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.pojo.Goods;
import com.wms.pojo.Record;
import com.wms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-08-08
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private GoodsService goodsService;

    //查询
    @PostMapping("/listPageP")
    public Result listPageP(@RequestBody QueryPageParam queryPageParam){
        Page<Record> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        String Goodsname = (String) queryPageParam.getParam().get("name");
        String storage =  (String)queryPageParam.getParam().get("storage");
        String goodsType =(String)queryPageParam.getParam().get("goodsType");


        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("r.goods=g.id and u.id=r.userId and u1.id=r.adminId and s.id=g.storage and g2.id=g.goodsType");
        if(StringUtils.isNotBlank(Goodsname) && !"null".equals(Goodsname)) {
//            queryWrapper.like("g1.name",Goodsname);
        }
//        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
//            lambdaQueryWrapper.eq(Goods::getStorage, storage);
//        }
//        if(StringUtils.isNotBlank(goodsType) && !"null".equals(goodsType)) {
//            lambdaQueryWrapper.eq(Goods::getGoodsType, goodsType);
//        }
        IPage result = recordService.pageC(page, queryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        Goods goods = goodsService.getById(record.getGoods());
        Integer n = record.getCount();
        //出库
        if(record.getAction().equals("2")){
            n = -n;
        }
        int i = goods.getCount()+ n;
        goods.setCount(i);
        goods.setRemark(record.getRemark());
        goodsService.updateById(goods);

        return recordService.save(record)? Result.suc() : Result.fail();
    }
}
