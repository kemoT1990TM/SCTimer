package com.tkartas.speedcubingtimer;

import net.gnehzr.tnoodle.scrambles.Puzzle;
import puzzle.CubePuzzle;

public class ScrambleGenerator {
    private Puzzle cubeType;

    public ScrambleGenerator(int puzzleType) {
        cubeType=new CubePuzzle(puzzleType);
    }

    public String generateWcaScramble() {
        return cubeType.generateScramble();
    }
}
