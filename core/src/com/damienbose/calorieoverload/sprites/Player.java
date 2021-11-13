package com.damienbose.calorieoverload.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.damienbose.calorieoverload.CalorieOverload;

public class Player {
    //constants(width)
    public static final float SIZE = 16;

    //orientation of player
    public float angle;

    //box2D body
    public World world;
    public Body b2body;

    //texture
    public TextureRegion textureRegion;

    //score
    public static int score = 0;
    //health
    public static int health = 100;
    public boolean gameOver = false;

    //direction of movement
    Vector2 move;

    public Player(World world){
        this.world =  world;
        definePlayer();
        textureRegion = new TextureRegion(new Texture("player.png"), 0, 0, 16, 16);

        //rest the stuff
        health = 100;
        score = 0;

    }


    public void definePlayer(){

        //body
        BodyDef bdef = new BodyDef();
        bdef.position.set(1381, 1200); //initial position
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(SIZE / 2);

        fdef.shape = shape;

        //specify category
        fdef.filter.categoryBits = CalorieOverload.PLAYER_BIT;
        //category it can collide with
        fdef.filter.maskBits = CalorieOverload.INVISIBLE_WALL_BIT | CalorieOverload.WALL_BIT | CalorieOverload.ENEMY_BIT;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(Vector2 vector, Vector2 angle){
        //max speed
        if(b2body.getLinearVelocity().len() >= 1){
            b2body.setLinearVelocity(b2body.getLinearVelocity().setLength(1f));
        }

        //friction
        b2body.setLinearVelocity(b2body.getLinearVelocity().x * 0.95f, b2body.getLinearVelocity().y * 0.95f);

        //orientation of player
        if(angle.len() != 0) {
            this.angle = angle.angle();
        }

        //check for dead
        if(health <= 0){
            gameOver = true;
            System.out.println("Dead");
        }

        move = new Vector2(vector);

        //move in ditection of drag
        b2body.applyForce(move.setLength(move.len() * 100), b2body.getWorldCenter(), true);
    }

    public void draw(SpriteBatch sb){

        sb.begin();
        //draw the player
        sb.draw(
                //the texture
                textureRegion,
                //its position
                b2body.getPosition().x - (Player.SIZE/ 2), b2body.getPosition().y - (Player.SIZE / 2),
                //the point relative to which the rotation will occure
                Player.SIZE / 2, Player.SIZE / 2,
                //the size of the texture
                Player.SIZE, Player.SIZE,
                //the scaling of the texture
                1, 1,
                //the rotation
                angle + 90f);
        sb.end();
    }

}
