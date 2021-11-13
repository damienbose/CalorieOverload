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

public class Projectile {
    //box 2d
    public World world;
    public Body b2body;

    //texture
    public TextureRegion textureRegion;

    public Projectile(World world, Vector2 pos){
        this.world =  world;
        defineProjectile(pos);
        textureRegion = new TextureRegion(new Texture("projectile.png"), 0, 0, 1, 1);
    }

    public void defineProjectile(Vector2 pos){

        //body
        BodyDef bdef = new BodyDef();
        bdef.position.set(pos.x, pos.y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setUserData("alive");

        //fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1);

        fdef.shape = shape;

        //specify category
        fdef.filter.categoryBits = CalorieOverload.PLAYER_BIT;
        //category it can collide with
        fdef.filter.maskBits = CalorieOverload.INVISIBLE_WALL_BIT | CalorieOverload.WALL_BIT | CalorieOverload.ENEMY_BIT;

        b2body.createFixture(fdef).setUserData(this);
    }
}
