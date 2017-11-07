package com.tongtong.pvzhmpro.sprite;

import com.tongtong.pvzhmpro.base.AttackPlant;
import com.tongtong.pvzhmpro.base.Bullet;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCNode;

/**
 * Created by allen on 2017/11/7.
 */

public class PeasePlant extends AttackPlant {
    public PeasePlant() {
        super("image/plant/pease/p_2_01.png");
        baseAction();
    }

    @Override
    public Bullet createBullet() {
        //每次只能发射一发子弹
        if (bullets.size() < 1) {   //之前没有创建子弹
            Pease pease = new Pease();
            pease.setPosition(CCNode.ccp(this.getPosition().x + 50, this.getPosition().y + 50));
            this.getParent().addChild(pease);
            bullets.add(pease);
        }
        return null;
    }

    @Override
    public void baseAction() {
        CCAction animate = CommonUtil.getAnimate("image/plant/pease/p_2_%02d.png", 8, true);
        this.runAction(animate);
    }
}
