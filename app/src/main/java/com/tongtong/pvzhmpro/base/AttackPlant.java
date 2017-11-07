package com.tongtong.pvzhmpro.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 攻击类型的植物
 *
 * @author Administrator
 */
public abstract class AttackPlant extends Plant {
    // 攻击产物集合（子弹）
    protected List<Bullet> bullets = new CopyOnWriteArrayList<>();

    public AttackPlant(String filepath) {
        super(filepath);
    }

    /**
     * 产生子弹
     *
     * @return
     */
    public abstract Bullet createBullet();

    /**
     * 获取子弹集合
     *
     * @return
     */
    public List<Bullet> getBullets() {
        return bullets;
    }

}
