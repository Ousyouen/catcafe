package com.example.demo.model;

public class MenuInfoPO extends BaseModel {

    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单描述
     */
    private String menuDesc;
    /**
     * 菜单价格
     */
    private Integer price;

    /**
     * 菜单图片
     */
    private String menuImage;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }
}
