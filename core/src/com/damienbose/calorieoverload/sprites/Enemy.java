package com.damienbose.calorieoverload.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.damienbose.calorieoverload.CalorieOverload;

import java.util.Random;

public class Enemy {
    //box 2d
    public World world;
    public Body b2body;

    //texture
    public TextureRegion textureRegion;

    //force of attraction to player
    private Vector2 force;

    //is colliding
    public boolean colliding = false;

    //type of enemy(0: burger, 1: nugget, 2: fries, 3: salad)
    public int type;
    public int health;
    public float speed;
    public float damage;

    //random number
    Random rand;

    public Enemy(World world, int type){
        this.world =  world;
        this.type = type;
        rand = new Random();

        if(type == 0) {
            textureRegion = new TextureRegion(new Texture("burger.png"), 0, 0, 16, 16);

            //burger
            health = 75;
            speed = 50;
            damage = 10;

        } else if (type == 1) {
            textureRegion = new TextureRegion(new Texture("nugget.png"), 0, 0, 16, 16);

            //nugget
            health = 75;
            speed= 75;
            damage = 5;

        } else if(type == 2) {
            textureRegion = new TextureRegion(new Texture("fries.png"), 0, 0, 16, 16);

            //fries
            health = 50;
            speed = 100;
            damage = 5;
        } else {
            System.out.println("enemy.type is invalid: " + type);
        }

        int i = rand.nextInt(4);

        if(i == 0) {
            defineEnemy(new Vector2(744, 1832)); //Top left
        } else if(i == 1){
            defineEnemy(new Vector2(744, 919)); //Bottom left
        } else if(i == 2) {
            defineEnemy(new Vector2(1928, 919)); //Bottom right
        } else if (i == 3) {
            defineEnemy(new Vector2(1928, 1832)); //Top right
        } else {
            System.out.println("invalid spawn location");
        }


        force = new Vector2();
    }

    public void defineEnemy(Vector2 pos){

        //body
        BodyDef bdef = new BodyDef();
        bdef.position.set(pos.x, pos.y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setUserData("alive");

        //fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Player.SIZE/2);

        fdef.shape = shape;

        //specify category
        fdef.filter.categoryBits = CalorieOverload.ENEMY_BIT;
        //category it can collide with
        fdef.filter.maskBits = CalorieOverload.WALL_BIT | CalorieOverload.ENEMY_BIT | CalorieOverload.PLAYER_BIT | CalorieOverload.PROJECTILE_BIT;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(Player player){

        if(!colliding) {
            player.b2body.getPosition();

            this.force.x = player.b2body.getPosition().x - this.b2body.getPosition().x;
            this.force.y = player.b2body.getPosition().y - this.b2body.getPosition().y;

            force.setLength(speed * 3);

            b2body.applyForce(force, b2body.getWorldCenter(), true);
        }
        //max speed
        if(b2body.getLinearVelocity().len() >= speed/1.3){
            b2body.setLinearVelocity(b2body.getLinearVelocity().setLength(speed/1.3f));
        }

        //check if dead
        if(health <= 0){
            b2body.setUserData("dead");
            Player.score += 5;
        }
    }

}
