package com.yuvaraj.jigsawpuzzle.cutter;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Pair;

public class PuzzlePieceBorderFinder {

    public static Rect getBorders(PieceCurveSet pieceCurveSet, Point startPoint, int horizontalStep, int verticalStep) {
        return new Rect(leftmostPointForCurve(pieceCurveSet.left, startPoint, horizontalStep),
                topmostPointForCurve(pieceCurveSet.top, startPoint, verticalStep),
                rightmostPointForCurve(pieceCurveSet.right, startPoint, horizontalStep),
                bottommostPointForCurve(pieceCurveSet.bottom, startPoint, verticalStep));
    }

    public static Pair<Integer, Integer> getOffsets(PieceCurveSet curveSet, int horizontalStep, int verticalStep) {
        return new Pair<>(getLeftOffset(curveSet.left, horizontalStep), getTopOffset(curveSet.top, verticalStep));
    }


    private static int topmostPointForCurve(HorizontalCurve curve, Point startPoint, int verticalStep) {
        if (curve == HorizontalCurve.NONE) {
            return startPoint.y;
        }
        int scale = verticalStep / 100;
        if (curve == HorizontalCurve.TAB) {
            return startPoint.y - 30 * scale;
        } else {
            return startPoint.y - 15 * scale;
        }
    }

    private static int bottommostPointForCurve(HorizontalCurve curve, Point startPoint, int verticalStep) {
        if (curve == HorizontalCurve.NONE) {
            return startPoint.y + verticalStep;
        }
        int scale = verticalStep / 100;
        if (curve == HorizontalCurve.TAB) {
            return startPoint.y + verticalStep + 15 * scale;
        } else {
            return startPoint.y + verticalStep + 30 * scale;
        }
    }

    private static int leftmostPointForCurve(VerticalCurve curve, Point startPoint, int horizontalStep) {
        if (curve == VerticalCurve.NONE) {
            return startPoint.x;
        }
        int scale = horizontalStep / 100;
        if (curve == VerticalCurve.TAB) {
            return startPoint.x - 30 * scale;
        } else {
            return startPoint.x - 15 * scale;
        }
    }

    private static int rightmostPointForCurve(VerticalCurve curve, Point startPoint, int horizontalStep) {
        if (curve == VerticalCurve.NONE) {
            return startPoint.x + horizontalStep;
        }
        int scale = horizontalStep / 100;
        if (curve == VerticalCurve.TAB) {
            return startPoint.x + horizontalStep + 15 * scale;
        } else {
            return startPoint.x + horizontalStep + 30 * scale;
        }
    }

    private static int getLeftOffset(VerticalCurve curve, int horizontalStep) {
        if (curve == VerticalCurve.NONE) {
            return 0;
        }
        int scale = horizontalStep / 100;
        if (curve == VerticalCurve.TAB) {
            return 30 * scale;
        } else {
            return 15 * scale;
        }
    }

    private static int getTopOffset(HorizontalCurve curve, int verticalStep) {
        if (curve == HorizontalCurve.NONE) {
            return 0;
        }
        int scale = verticalStep / 100;
        if (curve == HorizontalCurve.TAB) {
            return 30 * scale;
        } else {
            return 15 * scale;
        }
    }
}
