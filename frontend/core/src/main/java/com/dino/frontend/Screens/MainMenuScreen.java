package com.dino.frontend.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dino.frontend.Main;
import com.dino.frontend.UI.UiButton;

import static com.dino.frontend.Constants.GROUND_LEVEL;

public class MainMenuScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture groundTx;
    private Texture dinoTx;
    private UiButton play_resumeBtn;
    private UiButton leaderboardBtn;
    private UiButton accountBtn;
    private UiButton settingsBtn;

    public MainMenuScreen(Main game){
        this.game = game;
        this.batch = game.batch;
        groundTx = new Texture("ground.png");
        dinoTx = new Texture("dinoIdle.png");
        play_resumeBtn = new UiButton(new Texture("play_resume.png"), 72, 64);
        leaderboardBtn = new UiButton(new Texture("leaderboard.png"), 72, 64);
        accountBtn = new UiButton(new Texture("account.png"), 72, 64);
        settingsBtn = new UiButton(new Texture("settings.png"), 72, 64);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        batch.draw(groundTx, 0, GROUND_LEVEL);
        batch.draw(dinoTx, 50, GROUND_LEVEL);
        play_resumeBtn.render(batch);
        leaderboardBtn.render(batch);
        accountBtn.render(batch);
        settingsBtn.render(batch);
        batch.end();

        if(play_resumeBtn.isClicked() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
        }
        if(leaderboardBtn.isClicked()){
            System.out.println("Opening Leaderboard...");
        }
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
        groundTx.dispose();
        dinoTx.dispose();
        play_resumeBtn.dispose();
        leaderboardBtn.dispose();
        settingsBtn.dispose();
        accountBtn.dispose();
    }
}
