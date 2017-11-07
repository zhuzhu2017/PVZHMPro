package com.tongtong.pvzhmpro.sprite;

import com.tongtong.pvzhmpro.base.BaseElement;
import com.tongtong.pvzhmpro.base.Zombies;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by allen on 2017/11/7.
 */

public class PrimaryZombies extends Zombies {

    private CGPoint startPoint; //起点
    private CGPoint endPoint;   //终点

    public PrimaryZombies(CGPoint startPoint, CGPoint endPoint) {
        super("image/zombies/zombies_1/walk/z_1_01.png");
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        setPosition(startPoint);
        move();
    }

    @Override
    public void baseAction() {

    }

    @Override
    public void move() {
        CCAction animate = CommonUtil.getAnimate("image/zombies/zombies_1/walk/z_1_%02d.png", 7, true);
        this.runAction(animate);
        //移动
        float t = CGPointUtil.distance(getPosition(), endPoint) / speed;
        CCMoveTo moveTo = CCMoveTo.action(t, endPoint);
        CCSequence ccSequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "endGame"));
        this.runAction(ccSequence);
    }

    public void endGame() {
        this.destroy();
    }

    @Override
    public void attack(BaseElement element) {

    }

    @Override
    public void attacked(int attack) {

    }
}
