package com.tongtong.pvzhmpro.engine;

import com.tongtong.pvzhmpro.base.AttackPlant;
import com.tongtong.pvzhmpro.base.BaseElement;
import com.tongtong.pvzhmpro.base.Plant;
import com.tongtong.pvzhmpro.base.Zombies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 把一行的操作抽取出来
 * 每一行可以添加僵尸，安放植物，植物能攻击僵尸，僵尸能攻击植物
 * Created by allen on 2017/11/7.
 */

public class FightLine {

    private int lineNum;    //行号

    public FightLine(int lineNum) {
        this.lineNum = lineNum;
    }

    //记录当前行上所有的僵尸
    private List<Zombies> zombiesList = new ArrayList<>();
    private Map<Integer, Plant> plants = new HashMap<>();// 管理添加的植物
    // key当前植物所对应的列号
    private List<AttackPlant> attackPlants = new ArrayList<>();
    /**
     * 添加一个僵尸
     *
     * @param zombies
     */
    public void addZombies(final Zombies zombies) {
        zombiesList.add(zombies);
        zombies.setDieListener(new BaseElement.DieListener() {

            @Override
            public void die() {
                zombiesList.remove(zombies);
            }
        });
    }

    public void addPlant(final Plant plant) {
        plants.put(plant.getRow(), plant);
        if (plant instanceof AttackPlant) { // 如果发现植物是一个攻击类型的植物,添加到攻击类型植物的集合中
            attackPlants.add((AttackPlant) plant);
        }

        plant.setDieListener(new BaseElement.DieListener() {

            @Override
            public void die() {
                plants.remove(plant.getRow());
                if (plant instanceof AttackPlant) {
                    attackPlants.remove((AttackPlant) plant);
                }
            }
        });

    }

    /**
     * 判断该列上 是否有植物
     *
     * @param row
     */
    public boolean containsRow(int row) {
        return plants.containsKey(row);
    }

}
