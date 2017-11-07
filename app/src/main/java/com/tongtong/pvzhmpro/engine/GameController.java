package com.tongtong.pvzhmpro.engine;

import com.tongtong.pvzhmpro.bean.ShowPlant;

import org.cocos2d.layers.CCTMXTiledMap;

import java.util.ArrayList;
import java.util.List;

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
        //添加僵尸
        //安放植物
        //僵尸攻击植物
        //植物攻击僵尸
    }

    public void stopGame() {
        isStart = false;
    }

}
