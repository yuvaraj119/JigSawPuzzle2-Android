package com.yuvaraj.jigsawpuzzle.cutter;

import java.util.Random;

public class CutMap {

    private int horizontalResolution;
    private int verticalResolution;
    private final HorizontalCurve[][] horizontalCurveArray;
    private final VerticalCurve[][] verticalCurveArray;

    public CutMap(int horizontalResolution, int verticalResolution) {
        this.horizontalResolution = horizontalResolution;
        this.verticalResolution = verticalResolution;
        horizontalCurveArray = new HorizontalCurve[horizontalResolution][verticalResolution - 1];
        verticalCurveArray = new VerticalCurve[horizontalResolution - 1][verticalResolution];
        Random random = new Random();

        for (int i = 0; i < horizontalResolution; i++) {
            for (int j = 0; j < verticalResolution - 1; j++) {
                horizontalCurveArray[i][j] = random.nextBoolean() ? HorizontalCurve.SLOT : HorizontalCurve.TAB;
            }
        }
        for (int i = 0; i < horizontalResolution - 1; i++) {
            for (int j = 0; j < verticalResolution; j++) {
                verticalCurveArray[i][j] = random.nextBoolean() ? VerticalCurve.SLOT : VerticalCurve.TAB;
            }
        }
    }

    public PieceCurveSet getCurveSetForPiece(int xIndex, int yIndex) {
        return new PieceCurveSet(yIndex == 0 ? HorizontalCurve.NONE : horizontalCurveArray[xIndex][yIndex - 1],
                xIndex == 0 ? VerticalCurve.NONE : verticalCurveArray[xIndex - 1][yIndex],
                xIndex == verticalResolution - 1 ? VerticalCurve.NONE : verticalCurveArray[xIndex][yIndex],
                yIndex == horizontalResolution - 1 ? HorizontalCurve.NONE : horizontalCurveArray[xIndex][yIndex]);
    }

    public HorizontalCurve[][] getHorizontalCurveArray() {
        return horizontalCurveArray;
    }

    public VerticalCurve[][] getVerticalCurveArray() {
        return verticalCurveArray;
    }

    public int getHorizontalResolution() {
        return horizontalResolution;
    }

    public int getVerticalResolution() {
        return verticalResolution;
    }
}
