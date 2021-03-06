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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by charb on 10/26/2016.
 */

public class GameScreenOptions implements Screen {

    Game game;
    private OrthographicCamera gamecam;
    Viewport gamePort;
    SpriteBatch batch;
    Stage stage;
    TextureAtlas buttonAtlas;
    TextButton.TextButtonStyle textButtonStyle;
    TextButton button;
    TextButton button1;
    TextButton button2;
    TextButton button3;
    Skin skin;
    BitmapFont font;
    Label.LabelStyle style;
    Label label;

    GameScreenOptions(Game game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, gamecam);
    }

    @Override
    public void show() {
        stage = new Stage();

        font = new BitmapFont(Gdx.files.internal("font.fnt"),false);
        style = new Label.LabelStyle(font, Color.RED);

        label = new Label("Options Screen",style);
        label.setPosition(50,Gdx.graphics.getHeight()-50);
        label.setFontScale(2);

        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("button");

        textButtonStyle.down = skin.getDrawable("buttonpressed");
        textButtonStyle.font = font;

        button = new TextButton("Resume", textButtonStyle);
        button1 = new TextButton("Restart", textButtonStyle);
        button2 = new TextButton("Home", textButtonStyle);
        button3 = new TextButton("Volume", textButtonStyle);

        button.setPosition(500,1300,1);
        button1.setPosition(500,1100,1);
        button2.setPosition(500,900,1);
        button3.setPosition(500,700,1);
        stage.addActor(button);
        stage.addActor(button1);
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(label);

        Gdx.input.setInputProcessor(stage);

        button2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });
        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new GameScreen(game));
                return true;
            }
        });

        batch = new SpriteBatch();
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
    }
}
