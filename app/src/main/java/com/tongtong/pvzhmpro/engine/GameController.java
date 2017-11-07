package com.tongtong.pvzhmpro.engine;

import com.tongtong.pvzhmpro.bean.ShowPlant;
import com.tongtong.pvzhmpro.sprite.PrimaryZombies;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 处理游戏开始后的操作
 * 添加僵尸
 * 安放植物
 * 僵尸攻击植物
 * 植物攻击僵尸
 * Created by allen on 2017/11/7.
 */

public class GameController {

    private boolean isStart; //游戏是否开始
    private CCTMXTiledMap map;
    private List<ShowPlant> selectPlants;

    private List<CGPoint> roadPoints;

    private GameController() {
    }

    private static GameController controller;

    public static GameController getInstance() {
        if (controller == null) {
            controller = new GameController();
        }
        return controller;
    }

    private static List<FightLine> lines;   //管理了五行

    static {
        lines = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FightLine line = new FightLine(i);
            lines.add(line);
        }
    }

    /**
     * 开始游戏
     *
     * @param map          游戏地图
     * @param selectPlants 玩家已选植物的集合
     */
    public void startGame(CCTMXTiledMap map, List<ShowPlant> selectPlants) {
        isStart = true;
        this.map = map;
        this.selectPlants = selectPlants;
        //解析地图
        loadMap();
        //添加定时器,每隔一段时间添加一个僵尸
        CCScheduler.sharedScheduler().schedule("addZombie", this, 1, false);
        //安放植物
        //僵尸攻击植物
        //植物攻击僵尸
    }

    private void loadMap() {
        roadPoints = CommonUtil.getMapPoints(map, "road");
    }

    /**
     * 添加僵尸
     */
    public void addZombie(float t) {
        //随机一行添加僵尸
        Random random = new Random();
        int lineNum = random.nextInt(5);
        PrimaryZombies zombies = new PrimaryZombies(roadPoints.get(lineNum * 2),
                roadPoints.get(lineNum * 2 + 1));
        map.addChild(zombies);
        lines.get(lineNum).addZombies(zombies);
    }

    public void stopGame() {
        isStart = false;
    }

}
