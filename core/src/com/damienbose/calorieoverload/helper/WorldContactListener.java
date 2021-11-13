package com.damienbose.calorieoverload.helper;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.damienbose.calorieoverload.sprites.Enemy;
import com.damienbose.calorieoverload.sprites.Player;
import com.damienbose.calorieoverload.sprites.Projectile;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        //check if the projectile has collided with anything
        if (contact.getFixtureA().getUserData() instanceof Projectile) {
            contact.getFixtureA().getBody().setUserData("dead");
        }
        if (contact.getFixtureB().getUserData() instanceof Projectile) {
            contact.getFixtureB().getBody().setUserData("dead");
        }

        //check if enemy has collided with projectile
        if (contact.getFixtureA().getUserData() instanceof Enemy && contact.getFixtureB().getUserData() instanceof Projectile) {
            ((Enemy) contact.getFixtureA().getUserData()).health -= 25;
            Player.score += 1;
        }
        if (contact.getFixtureA().getUserData() instanceof Projectile && contact.getFixtureB().getUserData() instanceof Enemy) {
            ((Enemy) contact.getFixtureB().getUserData()).health -= 25;
            Player.score += 1;
        }

        //check if enemy and player collided
        if (contact.getFixtureA().getUserData() instanceof Enemy && contact.getFixtureB().getUserData() instanceof Player) {
            ((Enemy) contact.getFixtureA().getUserData()).colliding = true;
            ((Player) contact.getFixtureB().getUserData()).health -= ((Enemy) contact.getFixtureA().getUserData()).damage;
        }
        if (contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof Enemy) {
            ((Player) contact.getFixtureA().getUserData()).health -= ((Enemy) contact.getFixtureB().getUserData()).damage;
            ((Enemy) contact.getFixtureB().getUserData()).colliding = true;
        }

    }

    @Override
    public void endContact(Contact contact) {
        //check if enemy and player stopped colliding
        if (contact.getFixtureA().getUserData() instanceof Enemy && contact.getFixtureB().getUserData() instanceof Player) {
            ((Enemy) contact.getFixtureA().getUserData()).colliding = false;
        }

        if (contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof Enemy) {
            ((Enemy) contact.getFixtureB().getUserData()).colliding = false;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}




