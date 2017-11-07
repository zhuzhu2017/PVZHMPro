package com.tongtong.pvzhmpro.sprite;

import com.tongtong.pvzhmpro.base.Bullet;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by allen on 2017/11/7.
 */

public class Pease extends Bullet {
    public Pease() {
        super("image/fight/bullet.png");
    }

    @Override
    public void move() {
        //获取当前子弹的坐标
        CGPoint position = getPosition();
        CGPoint targetPosition = CCNode.ccp(CCDirector.sharedDirector().winSize().width, position.y);
        float t = CGPointUtil.distance(position, targetPosition) / speed;
        CCMoveTo ccMoveTo = CCMoveTo.action(t, targetPosition);
        this.runAction(ccMoveTo);
    }
}
