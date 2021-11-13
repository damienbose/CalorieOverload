package com.damienbose.calorieoverload.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damienbose.calorieoverload.CalorieOverload;
import com.damienbose.calorieoverload.sprites.Player;

public class Hud {

    public boolean gameOver = false;

    public Stage stage;
    private Viewport viewport;

    private float timeCount = 0;
    private Integer minutes;
    private Integer seconds;
    public Integer score;
    public Integer health;

    Label timeLable;
    Label scoreLabel;
    Label healthLabel;

    public Hud(SpriteBatch sb){
        minutes = 0;
        seconds = 0;
        score = 0;
        health = 100;

        viewport = new FitViewport(CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //font generator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Coolville.ttf")); //change font
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 65; // font size
        parameter.color = Color.WHITE;
        //parameter.borderWidth = 3;
        //parameter.borderStraight = true;
        //parameter.borderColor = Color.BLACK;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        //BitmapFont font = new BitmapFont();

        Table table = new Table();
        table.setBounds(0, 0, CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT);
        table.top();
        table.setDebug(false);

        timeLable = new Label("TIME: " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label("SCORE: " + String.format("%02d", score), new Label.LabelStyle(font, Color.WHITE));
        healthLabel = new Label("HEALTH: " + String.format("%03d", health), new Label.LabelStyle(font, Color.WHITE));

        table.add(timeLable).expandX().padTop(30);
        table.add(scoreLabel).expandX().padTop(30);
        table.add(healthLabel).expandX().padTop(30);

//        timeLable.setFontScale(3);
//        scoreLabel.setFontScale(3);
//        healthLabel.setFontScale(3);


        stage.addActor(table);
    }


    public void update(float dt){
        timerUpdate(dt);

        //update health
        if(Player.health >= 0) {
            health = Player.health;
            healthLabel.setText("HEALTH: " + String.format("%02d", health));
        }

        //update score
        score = Player.score;
        scoreLabel.setText("SCORE: " + String.format("%02d", score));
    }

    public void timerUpdate(float dt){
        if(!gameOver) {
            timeCount += dt;
        }
        if(timeCount >= 1) {
            seconds++;
            timeCount = 0;

            if (seconds >= 60) {
                minutes++;
                seconds = 0;
            }

            timeLable.setText("TIME: " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        }

    }

    public void draw(float dt){
        update(dt);
        stage.draw();
    }


    public void dispose(){
        stage.dispose();
    }

}
