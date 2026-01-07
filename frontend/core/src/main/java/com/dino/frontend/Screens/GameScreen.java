package com.dino.frontend.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.dino.frontend.Entities.Cactus;
import com.dino.frontend.Entities.Dino;
import com.dino.frontend.Enums.GameState;
import com.dino.frontend.Factory.CactusFactory;
import com.dino.frontend.Main;
import com.dino.frontend.UI.UiButton;

public class GameScreen implements Screen {
    private final Main game;
    private GameState currentState;
    private Dino dino;
    private CactusFactory cactusFactory;
    private Array<Cactus> activeCactus;
    private Texture groundTx;
    private UiButton restartBtn;
    private UiButton play_resumeBtn;
    private UiButton pauseBtn;
    private float spawnTimer;


    public GameScreen(Main game){
        this.game = game;
        this.dino = Dino.getInstance();
        this.dino.reset();
        this.cactusFactory = new CactusFactory();
        this.activeCactus = new Array<>();
        this.groundTx = new Texture("ground.png");
        this.restartBtn = new UiButton(new Texture("restart.png"), 72, 64);
        this.play_resumeBtn = new UiButton(new Texture("play_resume.png"), 72, 64);
        this.pauseBtn = new UiButton(new Texture("pause.png"), 72, 64);
        this.currentState = GameState.RUNNING;
        this.spawnTimer = 0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(currentState == GameState.RUNNING){
            updateRunning(delta);
        }
    }

    private void updateRunning(float delta){
        dino.update(delta);
        spawnTimer += delta;

    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
