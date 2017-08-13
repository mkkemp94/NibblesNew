package com.mkemp.nibbles.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mkemp.nibbles.sprites.Fruit;
import com.mkemp.nibbles.sprites.SnakePart;

import static com.mkemp.nibbles.Nibbles.FRUIT_BIT;
import static com.mkemp.nibbles.Nibbles.SNAKE_BIT;
import static com.mkemp.nibbles.Nibbles.WALL_BIT;

/**
 * Created by mkemp on 7/26/17.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

        Gdx.app.log("Begin", "Contact");

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cdef) {
            case SNAKE_BIT | WALL_BIT:
                Gdx.app.log("Collision", "Wall");
                contact.setEnabled(true);
                if (fixA.getFilterData().categoryBits == SNAKE_BIT)
                    ((SnakePart) fixA.getUserData()).gameOver();
                else
                    ((SnakePart) fixB.getUserData()).gameOver();
                break;

            case SNAKE_BIT | FRUIT_BIT:
                contact.setEnabled(false);
                if (fixA.getFilterData().categoryBits == FRUIT_BIT)
                    ((Fruit) fixA.getUserData()).eatFruit();
                else
                    ((Fruit) fixB.getUserData()).eatFruit();
                break;

            case SNAKE_BIT | SNAKE_BIT:
                Gdx.app.log("Collision", "Snake");
                ((SnakePart) fixA.getUserData()).gameOver();
                break;


        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End", "Contact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
