package com.buttercat.fridgebook.model;

public enum RecipeRankings {

    MAX_USED("Maximize used ingredients", 1),
    MIN_MISSING("Minimize missing ingredients", 2);

    private final String description;
    private final int ranking;

    public int getRanking() {
        return ranking;
    }

    RecipeRankings(String description, int ranking) {
        this.description = description;
        this.ranking = ranking;
    }
}
