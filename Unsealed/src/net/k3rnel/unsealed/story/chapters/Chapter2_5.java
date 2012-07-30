package net.k3rnel.unsealed.story.chapters;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.Bee;
import net.k3rnel.unsealed.battle.enemies.Clam;
import net.k3rnel.unsealed.battle.enemies.Snake;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.battle.skills.FireLion;
import net.k3rnel.unsealed.screens.BattleScreen;

public class Chapter2_5 extends BattleScreen {

    protected Button restartButton;

    public Chapter2_5(Unsealed game) {
        super(game,true, "RouteOne");

    }

    @Override
    public void show() {
        super.show();
        hero.setHp(180);
        hero.setSkill1(new EarthSpikes(getAtlas()));
        hud.xButton.addActor(hero.getSkill1());
        
        hero.setSkill3(new FireLion(getAtlas()));
        hud.aButton.addActor(hero.getSkill3());
        
        restartButton = new TextButton("Restart?", getSkin());
        restartButton.setY(250);
        restartButton.setX(320);
        restartButton.setWidth(100);
        restartButton.setHeight(50);
        restartButton.setVisible(false);
        restartButton.setDisabled(true);
        restartButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent arg0, float arg1, float arg2) {
               act = -1;
               hero.setHp(180);
               hero.setMana(0);
               hero.setGrid(1,1);
               hero.setSkill1(new EarthSpikes(getAtlas()));
               hud.xButton.addActor(hero.getSkill1());
               
               hero.setSkill3(new FireLion(getAtlas()));
               hud.aButton.addActor(hero.getSkill3());
               grid.assignEntity(hero);
               restartButton.setVisible(false);
            }
        });
        this.stage.addActor(restartButton);
        camera.position.set(1050, 1960, 0);
        camera.update();
    }
    
    @Override
    public void checkScene(float delta){
        this.stateTime+=delta;
        
        switch(act){
            case -1:
                dialog.setVisible(true);
                dialog.setText("I need to concentrate and use my Skills appropriately");
                if(stateTime>4){
                    grid.reset();
                    hero.setHp(180);
                    hero.setMana(0);
                    grid.assignEntity(hero);
                    act = 0;
                }
                break;
            case 0:
                dialog.setText("Remember you can use your Skills with J and L");
                dialog.setVisible(true);
                if(stateTime>4){
                    act = 1;
                    stateTime = 0;
                }
                break;
            case 1:
                disableInput = false;
                dialog.setVisible(false);
                grid.spawnEnemies(new Clam(getAtlas(),70,3,0),new Clam(getAtlas(),70,3,2),new Bee(getAtlas(),70,4,1));
                act = 2;
                break;
            case 2:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 3;
                    stateTime = 0;
                }else  if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 3:
                dialog.setText("Lidia: Bees? I must be getting near Hikaru...");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=4;
                }
                break;
            case 4:
                disableInput = false;
                dialog.setVisible(false);
                grid.spawnEnemies(new Clam(getAtlas(),70,5,0),new Clam(getAtlas(),70,5,2),new Bee(getAtlas(),60,3,1),new Bee(getAtlas(),80,4,1));
                act = 5;
                break;
            case 5:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 6;
                    stateTime = 0;
                }else  if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 6:
                dialog.setText("Lidia: One more round and I can relax...");
                dialog.setVisible(true);
                if(stateTime>3){
                    act=7;
                }
                break;
            case 7:
                disableInput = false;
                dialog.setVisible(false);
                grid.spawnEnemies(new Clam(getAtlas(),100,5,0),new Clam(getAtlas(),100,5,2),new Clam(getAtlas(),100,5,1),new Snake(getAtlas(),60,3,1),new Snake(getAtlas(),80,4,2));
                act = 8;
                break;
            case 8:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 9;
                    stateTime = 0;
                }else  if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 9:
                dialog.setText("Level Up! Your Maximum HP has been raised to 200!");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=10;
                }
                break;
            case 10:
                game.setScreen(new Chapter2_6(game));
                break;
        }
    }
}