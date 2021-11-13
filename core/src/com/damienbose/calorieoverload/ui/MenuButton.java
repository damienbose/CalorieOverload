package com.damienbose.calorieoverload.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damienbose.calorieoverload.CalorieOverload;

public class MenuButton {

    Viewport viewport;
    Stage stage;
    boolean playPressed;
    OrthographicCamera cam;

    //scaling
    private static final int BUTTON_SCALING = 10;
    private static final int LETTER_SCALING = 6;

    //letter padding
    private static final int LETTER_PADDING = 5;

    public MenuButton(SpriteBatch batch){
        cam = new OrthographicCamera();
        viewport = new FitViewport(CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT, cam);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        //main table
        Table tableM = new Table();
        tableM.setBounds(0, 0, CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT);
        tableM.center();
        tableM.setDebug(false);

        //nested table with "calorie"
        Table table = new Table();
        table.setDebug(false);

        //nested table with "overload"
        Table table2 = new Table();
        table2.setDebug(false);

        //TITLE
        Image C = new Image(new Texture("C.png"));
        Image A = new Image(new Texture("A.png"));
        Image L = new Image(new Texture("L.png"));
        Image O = new Image(new Texture("O.png"));
        Image R = new Image(new Texture("R.png"));
        Image I = new Image(new Texture("I.png"));
        Image E = new Image(new Texture("E.png"));

        Image O2 = new Image(new Texture("O.png"));
        Image V = new Image(new Texture("V.png"));
        Image E2 = new Image(new Texture("E.png"));
        Image R2 = new Image(new Texture("R.png"));
        Image L2 = new Image(new Texture("L.png"));
        Image O3 = new Image(new Texture("O.png"));
        Image A2 = new Image(new Texture("A.png"));
        Image D = new Image(new Texture("D.png"));

        C.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        A.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        L.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        O.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        R.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        I.setSize(8 * LETTER_SCALING, 16 * LETTER_SCALING);
        E.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);

        O2.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        V.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        E2.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        R2.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        L2.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        O3.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        A2.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);
        D.setSize(14 * LETTER_SCALING, 16 * LETTER_SCALING);


        //PLAY BUTTON
        Image playImg = new Image(new Texture("playButton.png"));
        playImg.setSize(17 * BUTTON_SCALING, 17 * BUTTON_SCALING);
        playImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playPressed = false;
            }
        });

        table.add(C).size(C.getWidth(), C.getHeight()).padRight(LETTER_PADDING);
        table.add(A).size(A.getWidth(), A.getHeight()).padRight(LETTER_PADDING);
        table.add(L).size(L.getWidth(), L.getHeight()).padRight(LETTER_PADDING);
        table.add(O).size(O.getWidth(), O.getHeight()).padRight(LETTER_PADDING);
        table.add(R).size(R.getWidth(), R.getHeight()).padRight(LETTER_PADDING);
        table.add(I).size(I.getWidth(), I.getHeight()).padRight(LETTER_PADDING);
        table.add(E).size(E.getWidth(), E.getHeight());

        table2.add(O2).size(O2.getWidth(), O2.getHeight()).padRight(LETTER_PADDING);
        table2.add(V).size(V.getWidth(), V.getHeight()).padRight(LETTER_PADDING);
        table2.add(E2).size(E2.getWidth(), E2.getHeight()).padRight(LETTER_PADDING);
        table2.add(R2).size(R2.getWidth(), R2.getHeight()).padRight(LETTER_PADDING);
        table2.add(L2).size(L2.getWidth(), L2.getHeight()).padRight(LETTER_PADDING);
        table2.add(O3).size(O3.getWidth(), O3.getHeight()).padRight(LETTER_PADDING);
        table2.add(A2).size(A2.getWidth(), A2.getHeight()).padRight(LETTER_PADDING);
        table2.add(D).size(D.getWidth(), D.getHeight());

        tableM.add(table);
        tableM.row().padTop(5);
        tableM.add(table2);
        tableM.row().padTop(80);
        tableM.add(playImg).size(playImg.getWidth(), playImg.getHeight());

        stage.addActor(tableM);
    }

    public void draw(){
        stage.draw();
    }

    public boolean isPlayPressed() {
        return playPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public void dispose(){
        stage.dispose();
    }
}
