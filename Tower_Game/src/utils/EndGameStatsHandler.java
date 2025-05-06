package utils;

@FunctionalInterface
public interface EndGameStatsHandler {
    void onVictory(int enemiesKilled);
}
