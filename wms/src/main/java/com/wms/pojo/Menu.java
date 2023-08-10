package com.wms.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wms
 * @since 2023-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "菜单编码")
    @TableField("menuCode")
    private String menuCode;

    @ApiModelProperty(value = "菜单名字")
    @TableField("menuName")
    private String menuName;

    @ApiModelProperty(value = "菜单级别")
    @TableField("menuLevel")
    private String menuLevel;

    @ApiModelProperty(value = "菜单的父code")
    @TableField("menuParentCode")
    private String menuParentCode;

    @ApiModelProperty(value = "点击触发的函数")
    @TableField("menuClick")
    private String menuClick;

    @ApiModelProperty(value = "权限(多个以逗号隔开) 0超级管理 1管理 2普通用户")
    @TableField("menuRight")
    private String menuRight;

    @TableField("menuComponent")
    private String menuComponent;

    @TableField("menuIcon")
    private String menuIcon;


}
