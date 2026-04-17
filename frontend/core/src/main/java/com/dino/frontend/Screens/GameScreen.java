package com.dino.frontend.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dino.frontend.Constants;
import com.dino.frontend.Entities.*;
import com.dino.frontend.Enums.CactusType;
import com.dino.frontend.Factory.CactusFactory;
import com.dino.frontend.Factory.CloudFactory;
import com.dino.frontend.Factory.PterodactylFactory;
import com.dino.frontend.Main;
import com.dino.frontend.Enums.GameState;
import com.dino.frontend.UI.UiButton;

import java.util.Iterator;

public class GameScreen implements Screen {
    private final Main game;
    private GameState currentState;

    // Entities & Factories
    private Dino dino;
    private Ground ground;
    private CactusFactory cactusFactory;
    private Array<Cactus> activeCacti;
    private PterodactylFactory pterodactylFactory;
    private Array<Pterodactyl> activePterodactyls;
    private CloudFactory cloudFactory;
    private Array<Cloud> activeClouds;

    // UI Components
    private UiButton restartBtn;
    private UiButton resumeBtn;
    private BitmapFont font;
    private GlyphLayout scoreLayout;

    // Audio
    private Sound deathSound;
    private Sound scoreSound;

    // Timers & Logic
    private float obstacleSpawnTimer;
    private float nextObstacleSpawnTime;
    private float cloudSpawnTimer;
    private float gameSpeed;
    private int score;
    private int highScore;
    private int scoreMilestone;

    public GameScreen(Main game) {
        this.game = game;

        // Init Entities & Factories
        this.dino = Dino.getInstance();
        this.ground = new Ground();
        this.cactusFactory = new CactusFactory();
        this.activeCacti = new Array<>();
        this.pterodactylFactory = new PterodactylFactory();
        this.activePterodactyls = new Array<>();
        this.cloudFactory = new CloudFactory();
        this.activeClouds = new Array<>();

        // Init UI
        this.restartBtn = new UiButton("restart.png", Constants.WORLD_WIDTH / 2f - 36 / 2f, Constants.WORLD_HEIGHT / 2f - 50);
        this.resumeBtn = new UiButton("play.png", Constants.WORLD_WIDTH / 2f - 36 / 2f, Constants.WORLD_HEIGHT / 2f - 50);
        initFont();
        this.scoreLayout = new GlyphLayout();

        // Init Audio
        try {
            this.deathSound = Gdx.audio.newSound(Gdx.files.internal("death.wav"));
        } catch (Exception e) {
            System.out.println("Warning: death.wav not found. Sound will be disabled.");
            this.deathSound = null;
        }
        try {
            this.scoreSound = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
        } catch (Exception e) {
            System.out.println("Warning: score.wav not found. Sound will be disabled.");
            this.scoreSound = null;
        }


        // Init Logic Variables
        this.highScore = 0;
        resetGame();
    }

    private void initFont() {
        // Use the default LibGDX font to avoid native dependency issues.
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(1.5f);
    }

    @Override
    public void render(float delta) {
        if (currentState == GameState.RUNNING) {
            updateRunning(delta);
        }

        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();

        for (Cloud cloud : activeClouds) cloud.render(game.batch);
        ground.render(game.batch);
        for (Cactus cactus : activeCacti) cactus.render(game.batch);
        for (Pterodactyl ptero : activePterodactyls) ptero.render(game.batch);
        dino.render(game.batch);

        String scoreText = "HI " + String.format("%05d", highScore) + " " + String.format("%05d", score);
        scoreLayout.setText(font, scoreText);
        font.draw(game.batch, scoreLayout, Constants.WORLD_WIDTH - scoreLayout.width - 20, Constants.WORLD_HEIGHT - 20);

        if (currentState == GameState.PAUSED) {
            renderPausedUI();
        } else if (currentState == GameState.GAME_OVER) {
            renderGameOverUI();
        }

        game.batch.end();
        handleGlobalInput();
    }

    private void updateRunning(float delta) {
        dino.update(delta);
        ground.update(delta, gameSpeed);

        // Update score and difficulty
        score += (int)(gameSpeed * delta / 5);
        if (score > scoreMilestone) {
            scoreMilestone += 100;
            if (scoreSound != null) {
                scoreSound.play();
            }
        }
        if (gameSpeed < Constants.MAX_SPEED) {
            gameSpeed += Constants.ACCELERATION * delta;
        }

        updateClouds(delta);
        updateObstacles(delta);
    }

    private void updateObstacles(float delta) {
        obstacleSpawnTimer += delta;
        if (obstacleSpawnTimer > nextObstacleSpawnTime) {
            spawnObstacle();
            obstacleSpawnTimer = 0;
            nextObstacleSpawnTime = MathUtils.random(1.2f, 2.8f) * (Constants.INITIAL_SPEED / gameSpeed);
        }
        updateCacti(delta);
        updatePterodactyls(delta);
    }

    private void updateClouds(float delta) {
        cloudSpawnTimer += delta;
        if (cloudSpawnTimer > MathUtils.random(Constants.CLOUD_SPAWN_TIMER_MIN, Constants.CLOUD_SPAWN_TIMER_MAX)) {
            spawnCloud();
            cloudSpawnTimer = 0;
        }
        Iterator<Cloud> cloudIter = activeClouds.iterator();
        while (cloudIter.hasNext()) {
            Cloud c = cloudIter.next();
            c.update(delta);
            if (c.getRemove()) {
                cloudFactory.freeCloud(c);
                cloudIter.remove();
            }
        }
    }

    private void updateCacti(float delta) {
        Iterator<Cactus> cactusIter = activeCacti.iterator();
        while (cactusIter.hasNext()) {
            Cactus cactus = cactusIter.next();
            cactus.update(delta, gameSpeed);
            if (dino.getHitbox().overlaps(cactus.getHitbox())) {
                gameOver();
                return;
            }
            if (cactus.getRemove()) {
                cactusFactory.freeCactus(cactus);
                cactusIter.remove();
            }
        }
    }

    private void updatePterodactyls(float delta) {
        Iterator<Pterodactyl> pteroIter = activePterodactyls.iterator();
        while (pteroIter.hasNext()) {
            Pterodactyl ptero = pteroIter.next();
            ptero.update(delta, gameSpeed);
            if (dino.getHitbox().overlaps(ptero.getHitbox())) {
                gameOver();
                return;
            }
            if (ptero.getRemove()) {
                pterodactylFactory.freePterodactyl(ptero);
                pteroIter.remove();
            }
        }
    }

    private void spawnObstacle() {
        if (score > 300 && MathUtils.random(0, 5) == 0) {
            spawnPterodactyl();
        } else {
            spawnCactus();
        }
    }

    private void spawnCactus() {
        float x = Constants.WORLD_WIDTH + 50;
        CactusType type = MathUtils.randomBoolean() ? CactusType.SINGLE : CactusType.TRIPLE;
        Cactus cactus = cactusFactory.obtainCactus(x, type);
        activeCacti.add(cactus);
    }

    private void spawnPterodactyl() {
        float x = Constants.WORLD_WIDTH + 50;
        Pterodactyl ptero = pterodactylFactory.obtainPterodactyl(x);
        activePterodactyls.add(ptero);
    }

    private void spawnCloud() {
        float x = Constants.WORLD_WIDTH + 50;
        Cloud cloud = cloudFactory.createCloud(x);
        activeClouds.add(cloud);
    }

    private void gameOver() {
        if (currentState == GameState.GAME_OVER) return;
        currentState = GameState.GAME_OVER;
        dino.setDead(true);
        if (deathSound != null) {
            deathSound.play();
        }
        if (score > highScore) {
            highScore = score;
        }
    }

    private void resetGame() {
        dino.reset();
        gameSpeed = Constants.INITIAL_SPEED;
        score = 0;
        scoreMilestone = 100;

        for (Cactus cactus : activeCacti) cactusFactory.freeCactus( cactus);
        activeCacti.clear();
        for (Pterodactyl ptero : activePterodactyls) pterodactylFactory.freePterodactyl(ptero);
        activePterodactyls.clear();
        for (Cloud cloud : activeClouds) cloudFactory.freeCloud(cloud);
        activeClouds.clear();
        spawnCloud();

        obstacleSpawnTimer = 0;
        cloudSpawnTimer = 0;
        nextObstacleSpawnTime = MathUtils.random(1.5f, 3.0f);

        currentState = GameState.RUNNING;
    }

    private void renderPausedUI() {
        resumeBtn.render(game.batch);
        if (resumeBtn.isClicked()) {
            currentState = GameState.RUNNING;
        }
    }

    private void renderGameOverUI() {
        GlyphLayout layout = new GlyphLayout(font, "GAME OVER");
        font.draw(game.batch, layout, (Constants.WORLD_WIDTH - layout.width) / 2f, Constants.WORLD_HEIGHT / 2f + 50);
        restartBtn.render(game.batch);
        if (restartBtn.isClicked() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            resetGame();
        }
    }

    private void handleGlobalInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (currentState == GameState.RUNNING) {
                currentState = GameState.PAUSED;
            } else if (currentState == GameState.PAUSED) {
                currentState = GameState.RUNNING;
            }
        }
    }

    @Override
    public void dispose() {
        cactusFactory.dispose();
        pterodactylFactory.dispose();
        cloudFactory.dispose();
        restartBtn.dispose();
        resumeBtn.dispose();
        font.dispose();
        if (deathSound != null) {
            deathSound.dispose();
        }
        if (scoreSound != null) {
            scoreSound.dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() { if (currentState == GameState.RUNNING) currentState = GameState.PAUSED; }
    @Override public void resume() {}
    @Override public void hide() {}
}
