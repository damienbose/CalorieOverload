package com.damienbose.calorieoverload.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damienbose.calorieoverload.CalorieOverload;
import com.damienbose.calorieoverload.ui.MenuButton;

public class MenuScreen implements Screen{


    //game state manager
    private CalorieOverload game;

    //camera
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    //button and title
    private MenuButton menuButton;

    //sound effect
    private Sound click;



    public MenuScreen(CalorieOverload game){
        this.game = game;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT, gameCam);

        menuButton = new MenuButton(this.game.batch);

        //sound effect
        click = Gdx.audio.newSound(Gdx.files.internal("coin.mp3"));
    }





    //update method
    public void update(float delta){
        gameCam.update();
        handleInput(delta);
    }

    public void handleInput(float delta){
        if(menuButton.isPlayPressed()){
            System.out.println("play");
            click.play();
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }


    //screen interface methods
    @Override
    public void show(){}

    @Override
    public void render(float delta) {

        //updating the screen
        this.update(delta);

        //clear the screen
        Gdx.gl.glClearColor( 255, 206, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuButton.draw();
    }

    @Override
    public void resize(int width, int height) {
        //resizing desktop window
        gamePort.update(width, height);
        menuButton.resize(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        menuButton.dispose();
        click.dispose();
    }
}
