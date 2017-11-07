package com.tongtong.pvzhmpro.layer;

import android.view.MotionEvent;

import com.tongtong.pvzhmpro.base.BaseLayer;
import com.tongtong.pvzhmpro.bean.ShowPlant;
import com.tongtong.pvzhmpro.bean.ShowZombies;
import com.tongtong.pvzhmpro.engine.GameController;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
        CCSequence ccSequence = CCSequence.actions(CCDelayTime.action(3), ccMoveBy, CCDelayTime.action(2), CCCallFunc.action(this, "loadContainer"));
        map.runAction(ccSequence);
    }

    private CCSprite upSprite;  //玩家选择的植物
    private CCSprite downSprite;    //玩家可选的植物
    private CCSprite start;

    /**
     * 加载两个容器
     */
    public void loadContainer() {
        //创建两个精灵
        upSprite = CCSprite.sprite("image/fight/chose/fight_chose.png");
        //设置锚点
        upSprite.setAnchorPoint(0, 1);   //左上角
        //设置位置
        upSprite.setPosition(0, winSize.height);
        this.addChild(upSprite);
        downSprite = CCSprite.sprite("image/fight/chose/fight_choose.png");
        //设置锚点
        downSprite.setAnchorPoint(0, 0);
        this.addChild(downSprite);
        /*添加植物到可选区域*/
        loadShowPlant();
        /*加载一起来摇滚按钮*/
        start = CCSprite.sprite("image/fight/chose/fight_start.png");
        start.setPosition(downSprite.getContentSize().width / 2, 30);
        downSprite.addChild(start);
    }

    private List<ShowPlant> showPlants = new ArrayList<>(); //展示植物用的集合

    /**
     * 添加植物到可选区域
     */
    private void loadShowPlant() {
        for (int i = 1; i <= 9; i++) {
            ShowPlant showPlant = new ShowPlant(i);
            //添加背景
            CCSprite bgSprite = showPlant.getBgSprite();
            //设置位置
            bgSprite.setPosition(20 + ((i - 1) % 4) * 54, 175 - ((i - 1) / 4) * 59);
            downSprite.addChild(bgSprite);
            //添加植物图片
            CCSprite showSprite = showPlant.getShowSprite();
            showSprite.setPosition(20 + ((i - 1) % 4) * 54, 175 - ((i - 1) / 4) * 59);
            downSprite.addChild(showSprite);
            //添加至集合
            showPlants.add(showPlant);
        }
        //开启点击事件
        setIsTouchEnabled(true);
    }

    private List<ShowPlant> selectPlants = new CopyOnWriteArrayList<>();   //选中植物的集合
    boolean isLock; //点击锁
    boolean isDelete;   //是否删除了植物
    boolean isDelLock;

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        //坐标转换
        CGPoint cgPoint = this.convertTouchToNodeSpace(event);
        CGRect boundingBox = downSprite.getBoundingBox();
        CGRect upSpriteBoundingBox = upSprite.getBoundingBox();
        //反选植物
        if (CGRect.containsPoint(upSpriteBoundingBox, cgPoint)) {
            isDelete = false;
            for (ShowPlant plant : selectPlants) {
                CCSprite showSprite = plant.getShowSprite();
                CGRect box = showSprite.getBoundingBox();
                if (CGRect.containsPoint(box, cgPoint) && !isDelLock) {
                    isDelLock = true;
                    //反选植物
                    CCMoveTo action = CCMoveTo.action(0.5f, plant.getBgSprite().getPosition());
                    CCSequence ccSequence = CCSequence.actions(action, CCCallFunc.action(this, "unlock"));
                    showSprite.runAction(ccSequence);
                    selectPlants.remove(plant);
                    isDelete = true;
                    continue;
                }
                if (isDelete) {
                    CCMoveBy ccMoveBy = CCMoveBy.action(0.5f, ccp(-53, 0));
                    plant.getShowSprite().runAction(ccMoveBy);
                }
            }
        } else {
            if (CGRect.containsPoint(boundingBox, cgPoint)) {  //点击的点落在可选植物容器中
                if (CGRect.containsPoint(start.getBoundingBox(), cgPoint)) {
                    //点击了一起来摇滚
                    ready();
                } else {
                    if (selectPlants.size() < 5 && !isLock) {
                        //可能选择植物
                        for (ShowPlant plant : showPlants) {
                            CCSprite showSprite = plant.getShowSprite();
                            CGRect box = showSprite.getBoundingBox();
                            if (CGRect.containsPoint(box, cgPoint)) {  //表示选中了对应的植物
                                isLock = true;
                                CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, ccp(75 + 53 * selectPlants.size(), 255));
                                CCSequence sequence = CCSequence.actions(ccMoveTo, CCCallFunc.action(this, "unlock"));
                                plant.getShowSprite().runAction(sequence);
                                selectPlants.add(plant);
                            }
                        }
                    }
                }
            }
        }
        return super.ccTouchesBegan(event);
    }

    /**
     * 处理点击一起来摇滚按钮的逻辑
     */
    private void ready() {
        //把选中的植物从新添加到已选择容器上
        //缩小玩家已选择的容器大小
        upSprite.setScale(0.65f);
        for (ShowPlant plant : selectPlants) {
            //缩小植物，因为父容器缩小了，孩子也应该跟着缩小
            plant.getShowSprite().setScale(0.65f);
            //设置孩子位置
            plant.getShowSprite().setPosition(plant.getShowSprite().getPosition().x * 0.65f,
                    plant.getShowSprite().getPosition().y
                            + (CCDirector.sharedDirector().getWinSize().height - plant.getShowSprite().getPosition().y) * 0.35f);
            this.addChild(plant.getShowSprite());
        }
        //关闭下面的容器
        downSprite.removeSelf();
        //移动地图
        int x = (int) (map.getContentSize().width - winSize.width);
        CCMoveBy ccMoveBy = CCMoveBy.action(1, ccp(x, 0));
        CCSequence ccSequence = CCSequence.actions(ccMoveBy, CCCallFunc.action(this, "preGame"));
        map.runAction(ccSequence);
    }

    private CCSprite ready;

    /**
     * 准备开始游戏
     */
    public void preGame() {
        ready = CCSprite.sprite("image/fight/startready_01.png");
        ready.setPosition(winSize.width / 2, winSize.height / 2);
        this.addChild(ready);
        String format = "image/fight/startready_%02d.png";
        CCAction animate = CommonUtil.getAnimate(format, 3, false);
        CCSequence ccSequence = CCSequence.actions((CCAnimate) animate, CCCallFunc.action(this, "startGame"));
        ready.runAction(ccSequence);
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        ready.removeSelf();
        GameController controller = GameController.getInstance();
        controller.startGame(map,selectPlants);
    }

    /**
     * 点击解锁
     */
    public void unlock() {
        isLock = false;
        isDelLock = false;
    }

}
