package application;

@FunctionalInterface
public interface EndGameStatsHandler {
    void onVictory(int enemiesKilled);
}
