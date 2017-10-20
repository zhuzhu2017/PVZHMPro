package com.tongtong.pvzhmpro.layer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.tongtong.pvzhmpro.base.BaseLayer;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * 欢迎页面图层
 * Created by allen on 2017/10/19.
 */

public class WelcomeLayer extends BaseLayer {

    private CCSprite start; //启动图片精灵

    /*构造函数*/
    public WelcomeLayer() {
        //模拟一个异步耗时动作
        new AsyncTask<Void, Void, Void>() {
            //在子线程中执行
            @Override
            protected Void doInBackground(Void... voids) {
                //模拟后台耗时动作
                SystemClock.sleep(6000);
                return null;
            }

            //在子线程之后执行的代码
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (start != null) {
                    start.setVisible(true);
                }
                //打开触摸事件
                setIsTouchEnabled(true);
            }
        }.execute();
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
        CCAction animate = CommonUtil.getAnimate("image/loading/loading_%02d.png", 9, false);
        //运行动作
        loading.runAction(animate);
        /*添加加载完成之后的图片精灵*/
        start = CCSprite.sprite("image/loading/loading_start.png");
        //设置位置
        start.setPosition(winSize.width / 2, 30);
        //默认不显示
        start.setVisible(false);
        //添加到图层
        this.addChild(start);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        //将Android坐标系中的点转换成cocos2d坐标系中的点
        CGPoint cgPoint = this.convertTouchToNodeSpace(event);
        //获取开始图片所在的矩形
        CGRect boundingBox = start.getBoundingBox();
        if (CGRect.containsPoint(boundingBox, cgPoint)) {  //包含
            /*处理点击事件*/
            //点击后打开新的场景
            CommonUtil.changeLayer(new MenuLayer());
        }
        return super.ccTouchesBegan(event);
    }
}
