package com.tongtong.pvzhmpro.base;

/**
 * 生产类型的植物
 *
 * @author Administrator
 */
public abstract class ProductPlant extends Plant {

    public ProductPlant(String filepath) {
        super(filepath);
    }

    /**
     * 生产植物
     */
    public abstract void create();


}
