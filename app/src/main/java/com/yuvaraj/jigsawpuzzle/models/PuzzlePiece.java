package com.yuvaraj.jigsawpuzzle.models;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.yuvaraj.jigsawpuzzle.cutter.PieceCurveSet;

public class PuzzlePiece  {

    private Bitmap image;
    private Point anchorPoint;
    private Point centerPoint;
    private PieceCurveSet pieceCurveSet;
    private int width;
    private int height;
    private String path;

    public PuzzlePiece(Bitmap image, String path, Point anchorPoint, Point centerPoint, PieceCurveSet pieceCurveSet, int width, int height) {
        this.image = image;
        this.anchorPoint = anchorPoint;
        this.centerPoint = centerPoint;
        this.pieceCurveSet = pieceCurveSet;
        this.width = width;
        this.height = height;
        this.path=path;
    }

    public PuzzlePiece(String path, Point anchorPoint, Point centerPoint, PieceCurveSet pieceCurveSet, int width, int height) {
        this.anchorPoint = anchorPoint;
        this.centerPoint = centerPoint;
        this.pieceCurveSet = pieceCurveSet;
        this.width = width;
        this.height = height;
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getImage() {
        return image;
    }

    public Point getAnchorPoint() {
        return anchorPoint;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public PieceCurveSet getPieceCurveSet() {
        return pieceCurveSet;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
