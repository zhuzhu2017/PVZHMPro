package com.tongtong.pvzhmpro.layer;

import com.tongtong.pvzhmpro.base.BaseLayer;
import com.tongtong.pvzhmpro.bean.ShowZombies;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.List;

/**
 * 对战页面
 * Created by allen on 2017/10/20.
 */

public class FightLayer extends BaseLayer {

    private CCTMXTiledMap map;
    private List<CGPoint> zombiesPoints;

    public FightLayer() {
        init();
    }

    private void init() {
        loadMap();
        parseMap();
        showZombies();
        moveMap();
    }

    /**
     * 解析地图
     */
    private void parseMap() {
        zombiesPoints = CommonUtil.getMapPoints(map, "zombies");
    }

    /**
     * 显示僵尸
     */
    private void showZombies() {
        for (int i = 0; i < zombiesPoints.size(); i++) {
            CGPoint cgPoint = zombiesPoints.get(i);
            ShowZombies showZombies = new ShowZombies();
            showZombies.setPosition(cgPoint);
            map.addChild(showZombies);
        }
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        //创建地图对象
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        //设置锚点
        map.setAnchorPoint(0.5f, 0.5f);
        CGSize contentSize = map.getContentSize();
        //设置位置
        map.setPosition(contentSize.width / 2, contentSize.height / 2);
        this.addChild(map);
    }

    /**
     * 移动地图
     */
    private void moveMap() {
        int x = (int) (winSize.width - map.getContentSize().width);
        CCMoveBy ccMoveBy = CCMoveBy.action(2, ccp(x, 0));
        CCSequence ccSequence = CCSequence.actions(CCDelayTime.action(4), ccMoveBy);
        map.runAction(ccSequence);
    }

}
