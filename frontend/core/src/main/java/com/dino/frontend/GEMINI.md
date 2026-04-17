# GEMINI.md - Google Dinosaur Game Clone

## Project Overview

This project is a 2D endless runner game, a clone of the classic Google Dinosaur game, built using the **Java** programming language and the **LibGDX** game development framework.

The game's architecture follows common LibGDX patterns:
*   **Screen Management**: The application uses a `Game` class (`Main.java`) to manage different screens (`MainMenuScreen`, `GameScreen`).
*   **Entity-Component (like) Structure**: The game world is composed of entities like `Dino`, `Cactus`, `Pterodactyl`, and `Cloud`. Each entity class encapsulates its own logic for state, movement, and rendering.
*   **Object Pooling**: To ensure smooth performance and avoid garbage collection pauses, the game uses an object pooling pattern for frequently created-and-destroyed objects like `Cactus` and `Pterodactyl`. This is managed by `Factory` and `Pool` classes (e.g., `CactusFactory`, `CactusPool`).
*   **Centralized Constants**: A `Constants.java` file is used to manage global configuration values like world size, gravity, and game speed, making it easy to tweak game balance.

## Building and Running

**TODO:** The build configuration files (`build.gradle`) are not present in this directory and are likely in a parent directory.

This is a LibGDX project, which typically uses Gradle for dependency management and running the application. The standard command to run the desktop version of a LibGDX project is usually:

```bash
# From the root directory of the project (which contains the build.gradle file)
./gradlew desktop:run
```

Consult the `build.gradle` file in the project's root for the exact tasks and configurations.

## Development Conventions

*   **Asset Management**:
    *   Game assets (images, sounds) are loaded from an `assets` folder in the project's root directory (this is a LibGDX convention).
    *   Asset loading has been made robust using `try-catch` blocks to prevent the game from crashing if an asset (like a `.wav` or `.png` file) is missing. A warning is printed to the console instead.

*   **Design Patterns**:
    *   **Object Pooling**: For any new, reusable game objects that are spawned frequently, follow the existing pattern of creating a `Pool` class (e.g., `MyObjectPool extends Pool<MyObject>`) and a `Factory` class (`MyObjectFactory`) to manage the object lifecycle (`obtain`, `free`).
    *   **Singleton**: The player character `Dino` is implemented as a singleton to ensure a single, globally accessible instance.

*   **Code Style**:
    *   All gameplay-related magic numbers (speed, size, position) should be defined as `public static final` fields in `Constants.java`.
    *   Game logic is primarily handled within the `updateRunning(float delta)` method in `GameScreen.java`.
    *   Separate methods are used for updating different groups of entities (e.g., `updateCacti`, `updatePterodactyls`).
    *   Entity-specific logic (like movement physics) is kept within the entity's own class (e.g., `Dino.update()`).

*   **Font Rendering**:
    *   The game currently uses LibGDX's default `BitmapFont` for rendering text (like the score) to avoid issues with native library dependencies (`gdx-freetype`).
