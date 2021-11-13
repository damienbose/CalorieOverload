package com.damienbose.calorieoverload.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damienbose.calorieoverload.CalorieOverload;

public class PlayButton {
    //Constant
    public static final int SCALING = 10;

    public Stage stage;
    private Viewport viewport;

    private Texture stick;

    private Image move;
    private Image shoot;

    //record initial position
    private Vector2 mouseInPos;

    //record move stick
    private Vector2 dragMove;
    private Vector2 posStickMove; //use this to access direction
    private boolean moveIsClicked = false;


    //record shoot stick
    private Vector2 dragShoot;
    private Vector2 posStickShoot; //use this to access direction
    private boolean shootIsClicked = false;




    public PlayButton(SpriteBatch sb){
        viewport = new FitViewport(CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);

        stick = new Texture("stick.png");
        posStickMove = new Vector2();
        posStickShoot = new Vector2();

        Table tableM = new Table();
        tableM.setBounds(0, 0, CalorieOverload.V_WIDTH, CalorieOverload.V_HEIGHT);
        tableM.bottom();
        tableM.setDebug(false);

        //initial position
        mouseInPos = new Vector2(17 * SCALING / 2, 17 * SCALING / 2);
        dragMove = new Vector2();
        dragShoot = new Vector2();

        //move joystick
        move = new Image(new Texture("circle.png"));
        move.setSize(17 * SCALING, 17 * SCALING);
        move.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                moveIsClicked = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dragMove.setZero();
                moveIsClicked = false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                dragMove.x = x - mouseInPos.x;
                dragMove.y = y - mouseInPos.y;
                stickPosition();
            }

        });

        //shoot joystick
        shoot = new Image(new Texture("circle.png"));
        shoot.setSize(17 * SCALING, 17 * SCALING);
        shoot.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                shootIsClicked = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dragShoot.setZero();
                shootIsClicked = false;

            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                dragShoot.x = x - mouseInPos.x;
                dragShoot.y = y - mouseInPos.y;
                stickPosition();
            }

        });

        //DANGEROUS CODE!!!!
        tableM.add(move).size(move.getWidth(), move.getHeight()).padBottom(60)
                //padding between both buttons
                .padRight(800);
        tableM.add(shoot).size(shoot.getWidth(), shoot.getHeight()).padBottom(60);
        stage.addActor(tableM);
    }

    public void stickPosition(){

        //update move stick position
        if(dragMove.len() > 4 * SCALING) {
            posStickMove = dragMove.setLength(4 * SCALING);
        } else {
            posStickMove = dragMove;
        }


        //update shoot stick position
        if(dragShoot.len() > 4 * SCALING) {
            posStickShoot = dragShoot.setLength(4 * SCALING);
        } else {
            posStickShoot = dragShoot;
        }

    }

    public void update(float dt){
    }

    public void draw(float dt, SpriteBatch sb){
        update(dt);
        stage.draw();

        //DANGEROUS CODE
        sb.begin();

        //draw move stick
        sb.draw(stick, 70 + posStickMove.x,
                60 + posStickMove.y,
                17 * SCALING, 17 * SCALING);

        //draw shoot stick
        sb.draw(stick, 940 + 100 + posStickShoot.x,
                60 + posStickShoot.y,
                17 * SCALING, 17 * SCALING);
        sb.end();

    }

    public void dispose(){
        stage.dispose();
        stick.dispose();
    }

    public boolean isMoveIsClicked() {
        return moveIsClicked;
    }

    public boolean isShootIsClicked() {
        return shootIsClicked;
    }

    public Vector2 getposStickMove(){
        return posStickMove;
    }

    public Vector2 getPosStickShoot(){
        return posStickShoot;
    }

}
