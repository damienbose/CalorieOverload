package com.damienbose.calorieoverload.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damienbose.calorieoverload.CalorieOverload;
import com.damienbose.calorieoverload.ui.Hud;

public class EndScreen implements Screen{

    private CalorieOverload game;
    private Hud hud;

    private OrthographicCamera cam;
    private Viewport viewport;
    private Stage stage;

    private static final int LETTER_SCALING = 10;
    private static final int LETTER_PADDING = 5;



    public EndScreen(CalorieOverload game, Hud hud){
        this.game = game;
        this.hud = hud;

        hud.gameOver = true;

        cam = new OrthographicCamera();
        viewport = new FitViewport(CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT, cam);

        stage = new Stage(viewport, game.batch);

        //main table
        Table tableM = new Table();
        tableM.setBounds(0, 0, CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT);
        tableM.center();
        tableM.setDebug(false);

        //nested table
        Table table1 = new Table();
        table1.setDebug(false);

        //top (table 1)
        Image G = new Image(new Texture("G.png"));
        Image A = new Image(new Texture("A.png"));
        Image M = new Image(new Texture("M.png"));
        Image E = new Image(new Texture("E.png"));

        G.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        A.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        M.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        E.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);

        table1.add(G).size(G.getWidth(), G.getHeight()).padRight(LETTER_PADDING);
        table1.add(A).size(A.getWidth(), A.getHeight()).padRight(LETTER_PADDING);
        table1.add(M).size(M.getWidth(), M.getHeight()).padRight(LETTER_PADDING);
        table1.add(E).size(E.getWidth(), E.getHeight());

        //nested table
        Table table2 = new Table();
        table2.setDebug(false);

        //bottom (table 2)
        Image O = new Image(new Texture("O.png"));
        Image V = new Image(new Texture("V.png"));
        Image E2 = new Image(new Texture("E.png"));
        Image R = new Image(new Texture("R.png"));

        O.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        V.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        E2.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        R.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);

        table2.add(O).size(O.getWidth(), O.getHeight()).padRight(LETTER_PADDING).padLeft(50);
        table2.add(V).size(V.getWidth(), V.getHeight()).padRight(LETTER_PADDING);
        table2.add(E2).size(E2.getWidth(), E2.getHeight()).padRight(LETTER_PADDING);
        table2.add(R).size(R.getWidth(), R.getHeight());

        tableM.add(table1);
        tableM.row().padTop(5);
        tableM.add(table2);

        stage.addActor(tableM);
    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    public void update(){
        cam.update();
        handleInput();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //updating the screen
        this.update();

        //clear the screen
        Gdx.gl.glClearColor( 1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hud.draw(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        hud.dispose();
    }
}
