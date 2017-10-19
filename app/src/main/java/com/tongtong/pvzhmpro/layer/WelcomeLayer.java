package com.tongtong.pvzhmpro.layer;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

/**
 * 欢迎页面图层
 * Created by allen on 2017/10/19.
 */

public class WelcomeLayer extends CCLayer {

    private CGSize winSize; //屏幕尺寸

    /*构造函数*/
    public WelcomeLayer() {
        init();
    }

    /*初始化*/
    private void init() {
        logo();
    }

    private void logo() {
        //创建Logo精灵
        CCSprite logo = CCSprite.sprite("image/popcap_logo.png");
        //获取屏幕尺寸
        winSize = CCDirector.sharedDirector().getWinSize();
        //设置Logo位于屏幕中间位置
        logo.setPosition(winSize.width / 2, winSize.height / 2);
        //添加到图层
        this.addChild(logo);
        /*让Logo执行动作（展示一秒隐藏）*/
        //隐藏
        CCHide ccHide = CCHide.action();
        //停留一秒
        CCDelayTime delayTime = CCDelayTime.action(1);
        //动作串行
        CCSequence ccSequence = CCSequence.actions(delayTime, delayTime,
                ccHide, delayTime, CCCallFunc.action(this, "loadWelcome"));
        //运行动作
        logo.runAction(ccSequence);
    }

    /**
     * 当动作执行完了之后调用
     */
    public void loadWelcome() {
        //创建背景精灵
        CCSprite bg = CCSprite.sprite("image/welcome.jpg");
        //设置背景精灵锚点为左下角
        bg.setAnchorPoint(0, 0);
        //添加到图层
        this.addChild(bg);
        //添加加载中图片精灵
        loading();
    }

    /**
     * 加载中
     */
    private void loading() {
        //新建加载中第一张图片精灵
        CCSprite loading = CCSprite.sprite("image/loading/loading_01.png");
        //设置图片位置
        loading.setPosition(winSize.width / 2, 30);
        //添加至图层
        this.addChild(loading);
        /*播放序列帧*/
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        String format = "image/loading/loading_%02d.png";
        for (int i = 1; i <= 9; i++) {
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(format, i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        //创建action所需要的anim
        CCAnimation anim = CCAnimation.animation("", 0.2f, frames);
        //创建动作，第二个参数为false表示不需要循环播放
        CCAnimate action = CCAnimate.action(anim, false);
        //运行动作
        loading.runAction(action);
    }
}
