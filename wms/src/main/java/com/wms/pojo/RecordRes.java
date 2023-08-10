package com.wms.pojo;

import lombok.Data;

@Data
public class RecordRes extends Record{
    private String storageName;
    private String goodstypeName;
    private String userIdName;
    private String adminIdName;
    private String goodsName;
}
