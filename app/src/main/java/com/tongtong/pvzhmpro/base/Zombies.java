package com.tongtong.pvzhmpro.base;

import com.tongtong.pvzhmpro.base.BaseElement;

import org.cocos2d.types.CGPoint;

/**
 * 僵尸对象
 *
 * @author Administrator
 */
public abstract class Zombies extends BaseElement {

    protected int life = 50;// 生命值
    protected int attack = 10;// 攻击力
    protected int speed = 20;// 移动速度

    protected CGPoint startPoint;// 开始位置
    protected CGPoint endPoint;// 结束位置

    public Zombies(String filepath) {
        super(filepath);

        setScale(0.5);
        setAnchorPoint(0.5f, 0);// 设置锚点
    }

    /**
     * 僵尸移动
     */
    public abstract void move();

    /**
     * 僵尸攻击
     *
     * @param element:僵尸攻击的对象
     */
    public abstract void attack(BaseElement element);

    /**
     * 僵尸被攻击
     */
    public abstract void attacked(int attack);

}
