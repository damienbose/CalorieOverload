package com.damienbose.calorieoverload.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.damienbose.calorieoverload.CalorieOverload;
import com.damienbose.calorieoverload.helper.WorldContactListener;
import com.damienbose.calorieoverload.sprites.Enemy;
import com.damienbose.calorieoverload.sprites.Player;
import com.damienbose.calorieoverload.sprites.Projectile;
import com.damienbose.calorieoverload.ui.Hud;
import com.damienbose.calorieoverload.ui.PlayButton;

public class PlayScreen implements Screen{
    //camera and game
    private CalorieOverload game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //tiled
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D
    private World world;
    private Box2DDebugRenderer b2dr;

    //UI
    private Hud hud;
    private PlayButton playButton;

    //player
    Player player;

    //all Box2D bodies in game world
    Array<Body> bodies;

    //rate of fire of the shots
    private float time = 0;
    private static final float RPS = 8; //rate of fire - rounds per second

    //acceleration of spawn rate(every 20 seconds);
    private float timeAcceleration = 0;
    private float SPS_acceleration = 1.2f;

    //time between each spawn of burger
    private float timeSpawnBurger = 0;
    private float SPS_B = 0.15f; //spawn per second

    //time between each spawn of nugget
    private float timeSpawnNugget = 0;
    private float SPS_N = 0.2f; //spawn per second

    //time between each spawn of fries
    private float timeSpawnFries = 0;
    private float SPS_F = 0.15f; //spawn per second


    public PlayScreen(CalorieOverload game){

        //camera and game
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(CalorieOverload.V_WIDTH/5, CalorieOverload.V_HEIGHT/5, gamecam);

        //tiled
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("testMap2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        //UI
        hud = new Hud(game.batch);
        playButton = new PlayButton(game.batch);

        //Box2D
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //walls
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;

            //specify category
            fdef.filter.categoryBits = CalorieOverload.WALL_BIT;

            body.createFixture(fdef);
        }

        //invisible walls
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;

            //specify category
            fdef.filter.categoryBits = CalorieOverload.INVISIBLE_WALL_BIT;

            body.createFixture(fdef);
        }

        //create the player
        player = new Player(world);

        //listen for collisions between bodies
        world.setContactListener(new WorldContactListener());

        //all bodies in the world
        bodies  = new Array<Body>();

    }

    @Override
    public void show() {

    }


    public void moveCam(float dt) {
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;

    }



    public void handleInput(float dt){

    }

    public void shoot(float dt){
        if(playButton.isShootIsClicked()){
            time += dt;
            if(time > 1/RPS){
                time = 0;
                Projectile projectile = new Projectile(world, player.b2body.getPosition());
                projectile.b2body.applyLinearImpulse(new Vector2(1,1).setAngle(player.angle).setLength(200), projectile.b2body.getWorldCenter(), true);
            }
        }
    }

    public void spawnEnemies(float dt){

        //acceleration os spawn rate
        timeAcceleration += dt;
        if (timeAcceleration > 20) {
            timeAcceleration = 0;
            SPS_B *= SPS_acceleration;
            SPS_N *= SPS_acceleration;
            SPS_F *= SPS_acceleration;
        }

        //Burger
        timeSpawnBurger += dt;
        if(timeSpawnBurger > 1/SPS_B){
            timeSpawnBurger = 0;
            new Enemy(world, 0);
        }

        //nugget
        timeSpawnNugget += dt;
        if(timeSpawnNugget > 1/SPS_N){
            timeSpawnNugget = 0;
            new Enemy(world, 1);
        }

        //fries
        timeSpawnFries += dt;
        if(timeSpawnFries > 1/SPS_F){
            timeSpawnFries = 0;
            new Enemy(world, 2);
        }
    }

    public void update(float dt){

        handleInput(dt);

        //how many times to update world
        world.step(1/60f, 6, 2);

        gamecam.update();

        //tell it to render only what the cam can see
        renderer.setView(gamecam);

        //player
        player.update(playButton.getposStickMove(), playButton.getPosStickShoot());

        moveCam(dt);

        shoot(dt);

        spawnEnemies(dt);

        //check if player is dead
        if(player.gameOver){
            game.setScreen(new EndScreen(game, hud));
            dispose();
        }
    }


    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //tiled
        renderer.render();

        //box2d Debug lines
        //b2dr.render(world, gamecam.combined);


        //draw player
        game.batch.setProjectionMatrix(gamecam.combined);
        player.draw(game.batch);

        game.batch.begin();

        //get all bodies
        world.getBodies(bodies);

        //draw the projectiles
        for(Body b : bodies) {

            if (b.getUserData() == "dead") {
                world.destroyBody(b);
            } else {

                if(b.getFixtureList().peek().getUserData() instanceof Projectile) {

                    game.batch.draw(
                            //texture
                            ((Projectile) b.getFixtureList().peek().getUserData()).textureRegion,
                            //position
                            b.getPosition().x - 1, b.getPosition().y - 1,
                            //the point relative to which the rotation will occure
                            1, 1,
                            //the size of the texture
                            2, 2,
                            //scaling
                            1, 1,
                            //the rotation
                            b.getLinearVelocity().angle());
                }

                if(b.getFixtureList().peek().getUserData() instanceof Enemy){

                    Enemy enemy = ((Enemy) b.getFixtureList().peek().getUserData());
                    enemy.update(player);

                    game.batch.draw(
                            //texture
                            ((Enemy) b.getFixtureList().peek().getUserData()).textureRegion,
                            //position
                            b.getPosition().x - Player.SIZE/2, b.getPosition().y - Player.SIZE / 2,
                            //the point relative to which the rotation will occur
                            Player.SIZE / 2, Player.SIZE / 2,
                            //the size of the texture
                            Player.SIZE, Player.SIZE,
                            //scaling
                            1, 1,
                            //the rotation
                            b.getLinearVelocity().angle() - 90);
                }
            }
        }
        game.batch.end();


        //UI
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.draw(delta);
        game.batch.setProjectionMatrix(playButton.stage.getCamera().combined);
        playButton.draw(delta, game.batch);

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        world.dispose();
        playButton.dispose();
    }
}
