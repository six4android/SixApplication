package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by charb on 10/27/2016.
 */

public class TimeTileScreen implements Screen {
    Game game;
    SpriteBatch batch;
    Stage stage;
    private OrthographicCamera gamecam;
    Viewport gamePort;
    TextureAtlas buttonAtlas;
    TextButton.TextButtonStyle textButtonStyle;
    TextButton button;
    TextButton button1;
    Skin skin;
    BitmapFont font;
    Label.LabelStyle style;
    Label label;
    float width,height;
    public static final float PPM = 100; //Pixels per Meter
    World world;
    Box2DDebugRenderer renderer;
    Body BoxBody,rightsideBody,leftsideBody,rooftopBody;

    public TimeTileScreen(Game game){
        this.game = game;
        width = Gdx.graphics.getWidth()/PPM;
        height = Gdx.graphics.getHeight()/PPM;
        gamecam = new OrthographicCamera(width,height);
        gamecam.position.set(width*0.5f,height*0.5f,0); //made to put the green line horizontally, either upper or lower, i.e. 0.5 would put in the bottom. 0.05 would put it in the middle
        gamecam.update();

        world = new World(new Vector2(0,-10.8f),false); //this detects the gravity either in the x-axis or y-axis  the higher negative number the faster the object goes down.... Efficiently change to pixels per meter(better)


        renderer = new Box2DDebugRenderer();

        int y =0;
        int x =0;
        for(int i =1;i<=13;i++){
            y=103*i;
            CreateBox(x,y);

        }
        CreategroundBody();
        CreateHexagon();
    }

    public void CreateBox(int x,int y){
        for(int i = 0;i<=4;i++){
            BodyDef BoxBodyDef = new BodyDef();
            BoxBodyDef.type = BodyDef.BodyType.DynamicBody;
            BoxBodyDef.position.set(width / 9f + width*2/9f + x/ PPM, height / 2f - height / 2f + y/PPM);
            BoxBody = world.createBody(BoxBodyDef);
            PolygonShape Box = new PolygonShape();
            Box.setAsBox(50f / PPM, 50f / PPM);
            FixtureDef BoxFixture = new FixtureDef();
            BoxFixture.shape = Box;
            BoxFixture.density = 80f;
            BoxFixture.friction = 1f;
            BoxFixture.restitution = 0f;
            BoxBody.createFixture(BoxFixture);
            x+=103;
        }
    }
    public void CreateHexagon(){
        BodyDef BoxBodyDef = new BodyDef(); //Not sure how to make a Hexagon
        BoxBodyDef.type = BodyDef.BodyType.DynamicBody;
        BoxBodyDef.position.set(width /2f + 25/PPM, height /2f + 530/PPM);
        BoxBody = world.createBody(BoxBodyDef);
        PolygonShape Box = new PolygonShape();
        Box.setAsBox(100f / PPM, 100f / PPM);
        FixtureDef BoxFixture = new FixtureDef();
        BoxFixture.shape = Box;
        BoxFixture.density = 80f;
        BoxFixture.friction = 1f;
        BoxFixture.restitution = 0f;
        BoxBody.createFixture(BoxFixture);
    }

    public void CreateBigBox(){
        BodyDef BoxBodyDef = new BodyDef();
        BoxBodyDef.type = BodyDef.BodyType.DynamicBody;
        BoxBodyDef.position.set(width / 9f + 50/PPM, height / 2f - height / 2f);
        BoxBody = world.createBody(BoxBodyDef);
        PolygonShape Box = new PolygonShape();
        Box.setAsBox(100f / PPM, 400f / PPM);
        FixtureDef BoxFixture = new FixtureDef();
        BoxFixture.shape = Box;
        BoxFixture.density = 10f;
        BoxFixture.friction = 0.0f;
        BoxFixture.restitution = 0f;
        BoxBody.createFixture(BoxFixture);
    }
    public void CreateRooftopBody(){

        BodyDef rooftopBodyDef = new BodyDef(); // rooftop to keep the structure intact
        rooftopBodyDef.position.set(0,11);

        rooftopBody = world.createBody(rooftopBodyDef);

        PolygonShape rooftopBox = new PolygonShape();

        rooftopBox.setAsBox((gamecam.viewportWidth)*2,3.0f);
        rooftopBody.createFixture(rooftopBox,0.0f); //rooftop to keep the structure intact
    }

    public void CreateLeftSideBody(){
        BodyDef rightsideBodyDef = new BodyDef(); // big rightside line to keep the structure intact without falling
        rightsideBodyDef.position.set(width / 9f - 360/PPM, height / 2f - height / 2f);

        rightsideBody = world.createBody(rightsideBodyDef);

        PolygonShape rightsideBox = new PolygonShape();

        rightsideBox.setAsBox(5f,900);
        rightsideBody.createFixture(rightsideBox,0.0f);// big rightside line to keep the structure intact without falling
    }

    public void CreateRightSideBody(){
        BodyDef leftsideBodyDef = new BodyDef(); // big leftside line to keep the structure intact without falling
        leftsideBodyDef.position.set(width / 9f + 1170/PPM, height / 2f - height / 2f);

        leftsideBody = world.createBody(leftsideBodyDef);

        PolygonShape leftsideBox = new PolygonShape();

        leftsideBox.setAsBox(5f,900f);
        leftsideBody.createFixture(leftsideBox,0.0f);// big leftside line to keep the structure intact without falling

    }

    public void CreategroundBody(){
        BodyDef groundBodyDef = new BodyDef(); // groundbody for the structure to sit
        groundBodyDef.position.set(0,-3.099f);

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox((gamecam.viewportWidth)*2,3.0f);
        groundBody.createFixture(groundBox, 0.0f); // groundbody for the structure to sit
    }





    @Override
    public void show() {
        stage = new Stage();

        font = new BitmapFont(Gdx.files.internal("font.fnt"),false);
        style = new Label.LabelStyle(font, Color.RED);

        label = new Label("Game Screen",style);
        label.setPosition(50,Gdx.graphics.getHeight()-50);
        label.setFontScale(2);

        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("button");

        textButtonStyle.down = skin.getDrawable("buttonpressed");
        textButtonStyle.font = font;

        button1 = new TextButton("Options", textButtonStyle);

        button1.setPosition(800,1800,1);
        stage.addActor(button1);
        stage.addActor(label);

        Gdx.input.setInputProcessor(stage);

        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new GameScreenOptions(game));
                return true;
            }
        });

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(world, gamecam.combined);

        world.step(1/60f,6,2);
        world.clearForces();

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        world.dispose();
    }
}
