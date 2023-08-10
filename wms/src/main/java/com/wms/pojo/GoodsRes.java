package com.wms.pojo;

import lombok.Data;

@Data
public class GoodsRes extends Goods{
    private String storageName;
    private String goodstypeName;
}
