package com.yuvaraj.jigsawpuzzle.cutter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class CurveDrawer {

    private static final Point[] horizontalTabCoords = new Point[] {
            new Point(0, 0), new Point(35, 15), new Point(37, 5),
            new Point(40, 0), new Point(38, -5), new Point(20, -20),
            new Point(50, -20), new Point(80, -20), new Point(62, -5),
            new Point(60, 0), new Point(63, 5), new Point(65, 15), new Point(100, 0)};
    private static final Point[] verticalTabCoords = new Point[] {
            new Point(0, 0), new Point(15, 35), new Point(5, 37),
            new Point(0, 40), new Point(-5, 38), new Point(-20, 20),
            new Point(-20, 50), new Point(-20, 80), new Point(-5, 62),
            new Point(0, 60), new Point(5, 63), new Point(15, 65), new Point(0, 100)};

    public static void drawPuzzlePiece(Canvas canvas, PieceCurveSet pieceCurveSet, Point startPoint,
                                         int horizontalStep, int verticalStep, Paint paint) {
        float horizontalScale = horizontalStep / 100f;
        float verticalScale = verticalStep / 100f;
        drawHorizontalCurve(pieceCurveSet.top, canvas, startPoint, horizontalScale, verticalScale, paint);
        drawVerticalCurve(pieceCurveSet.left, canvas, startPoint, horizontalScale, verticalScale, paint);
        drawHorizontalCurve(pieceCurveSet.bottom, canvas, new Point(startPoint.x, startPoint.y + verticalStep), horizontalScale, verticalScale, paint);
        drawVerticalCurve(pieceCurveSet.right, canvas, new Point(startPoint.x + horizontalStep, startPoint.y), horizontalScale, verticalScale, paint);
    }

    public static void drawHorizontalCurve(HorizontalCurve curve, Canvas canvas, Point startPoint,
                                           float horizontalScale, float verticalScale, Paint paint) {
        if (curve == HorizontalCurve.NONE) {
            return;
        }
        int sign = curve == HorizontalCurve.TAB ? 1 : -1;
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        for (int i = 0; i + 2 < horizontalTabCoords.length; i+=2) {
            path.cubicTo(startPoint.x + horizontalTabCoords[i].x * horizontalScale, startPoint.y + sign * horizontalTabCoords[i].y * verticalScale,
                    startPoint.x + horizontalTabCoords[i+1].x * horizontalScale, startPoint.y + sign * horizontalTabCoords[i+1].y * verticalScale,
                    startPoint.x + horizontalTabCoords[i+2].x * horizontalScale, startPoint.y + sign * horizontalTabCoords[i+2].y * verticalScale);
        }
        canvas.drawPath(path, paint);
    }

    public static void drawVerticalCurve(VerticalCurve curve, Canvas canvas, Point startPoint,
                                   float horizontalScale, float verticalScale, Paint paint) {
        if (curve == VerticalCurve.NONE) {
            return;
        }
        int sign = curve == VerticalCurve.TAB ? 1 : -1;
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        for (int i = 0; i + 2 < verticalTabCoords.length; i+=2) {
            path.cubicTo(startPoint.x + sign * verticalTabCoords[i].x * horizontalScale, startPoint.y + verticalTabCoords[i].y * verticalScale,
                    startPoint.x + sign * verticalTabCoords[i+1].x * horizontalScale, startPoint.y + verticalTabCoords[i+1].y * verticalScale,
                    startPoint.x + sign * verticalTabCoords[i+2].x * horizontalScale, startPoint.y + verticalTabCoords[i+2].y * verticalScale);
        }
        canvas.drawPath(path, paint);
    }
}
