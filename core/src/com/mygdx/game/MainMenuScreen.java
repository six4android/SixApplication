package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by charb on 10/25/2016.
 */

public class MainMenuScreen implements Screen {
    //private MyGdxGame game;
    Game game;
    Stage stage;
    private OrthographicCamera gamecam;
    Viewport gamePort;
    TextureAtlas buttonAtlas;
    TextButtonStyle textButtonStyle;
    TextButton button;
    TextButton button1;
    Skin skin;
    BitmapFont font;
    LabelStyle style;
    Label label;
    SpriteBatch batch;


    public MainMenuScreen (Game game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, gamecam);


    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        batch.end();


    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void resume(){}

    @Override
    public void pause(){}

    @Override
    public void show() {
        stage = new Stage();

        font = new BitmapFont(Gdx.files.internal("font.fnt"),false);
        style = new LabelStyle(font, Color.RED);

        label = new Label("Welcome to game Six!",style);
        label.setPosition(50,Gdx.graphics.getHeight()-50 );
        label.setFontScale(2);

        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("button");

        textButtonStyle.down = skin.getDrawable("buttonpressed");
        textButtonStyle.font = font;

        button = new TextButton("Start Game", textButtonStyle);
        button1 = new TextButton("Challenge", textButtonStyle);

        button.setPosition(500,1000,1);
        button1.setPosition(500,800,1);
        stage.addActor(button);
        stage.addActor(button1);
        stage.addActor(label);

        Gdx.input.setInputProcessor(stage);

        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new GameScreen(game));
                return true;
            }
        });
        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new ChallengeScreen(game));
                return true;
            }
        });
        batch = new SpriteBatch();
    }

    @Override
    public void hide(){}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
