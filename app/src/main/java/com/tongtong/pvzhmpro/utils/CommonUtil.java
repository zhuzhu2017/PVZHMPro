package com.tongtong.pvzhmpro.utils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by allen on 2017/10/20.
 */

public class CommonUtil {

    /**
     * 打开新的图层
     *
     * @param newLayer 新图层
     */
    public static void changeLayer(CCLayer newLayer) {
        //获取导演对象
        CCDirector ccDirector = CCDirector.sharedDirector();
        //创建场景
        CCScene scene = CCScene.node();
        scene.addChild(newLayer);
        //添加转场动画，参数1：时间，参数2：场景，参数3：Flip开始方向
        CCFlipXTransition transition = CCFlipXTransition.transition(1, scene, 1);
        ccDirector.replaceScene(transition);
    }

    /**
     * 解析地图上的点
     *
     * @param map  地图
     * @param name 解析的对象
     * @return
     */
    public static List<CGPoint> getMapPoints(CCTMXTiledMap map, String name) {
        List<CGPoint> points = new ArrayList<>();
        CCTMXObjectGroup objectGroupNamed = map.objectGroupNamed(name);
        ArrayList<HashMap<String, String>> objects = objectGroupNamed.objects;
        for (HashMap<String, String> hasMap : objects) {
            int x = Integer.parseInt(hasMap.get("x"));
            int y = Integer.parseInt(hasMap.get("y"));
            CGPoint cgPoint = CCNode.ccp(x, y);
            points.add(cgPoint);
        }
        return points;
    }

    /**
     * 播放序列帧动画
     *
     * @param format    图片格式化路径
     * @param num       图片数量
     * @param isForever 是否无限循环
     * @return
     */
    public static CCAction getAnimate(String format, int num, boolean isForever) {
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(format, i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        //创建action所需要的anim
        CCAnimation anim = CCAnimation.animation("", 0.2f, frames);
        if (isForever) {
            //创建动作，第二个参数为false表示不需要循环播放
            CCAnimate action = CCAnimate.action(anim);
            return CCRepeatForever.action(action);
        } else {
            return CCAnimate.action(anim, false);
        }
    }

}
