package com.yuvaraj.jigsawpuzzle.puzzle;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.yuvaraj.jigsawpuzzle.R;
import com.yuvaraj.jigsawpuzzle.adapter.PuzzleAdapter;

import com.yuvaraj.jigsawpuzzle.models.Pieces;
import com.yuvaraj.jigsawpuzzle.models.PuzzlePiece;

/**
 * Created by Yuvaraj on 9/5/2016.
 */
public class PuzzleActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    FrameLayout scrollView;
    ImageView imageView;
    Context context;
    List<Pieces> piecesModelListMain = new ArrayList<Pieces>();
    HashMap<String, Pieces> piecesModelHashMap = new HashMap<String, Pieces>();
    int countGrid = 0;
    ArrayList<PuzzlePiece> puzzlePiecesList = new ArrayList<PuzzlePiece>();
    private RecyclerView rvPuzzle;
    private RecyclerView.LayoutManager linearLayoutManager;
    private PuzzleAdapter puzzleListAdapter;
    Puzzle puzzle;

    boolean widthCheck = true;
    int widthFinal;
    int heightFinal;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_activity);

        context = this;
        imageView = (ImageView) findViewById(R.id.frameImage);
        scrollView = (FrameLayout) findViewById(R.id.scrollView);
        scrollView.setOnDragListener(new MyDragListener(null));
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnDragListener(new MyDragListener(null));
        rvPuzzle = (RecyclerView) findViewById(R.id.listView2);
        rvPuzzle.setOnDragListener(new MyDragListener(null));
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPuzzle.setLayoutManager(linearLayoutManager);
        puzzle = new Puzzle();
        puzzlePiecesList.clear();

        final ViewTreeObserver obs = scrollView.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {//width=lGrid.getWidth();
                if (widthCheck) {
                    widthFinal = scrollView.getWidth();
                    heightFinal = scrollView.getHeight();
                    puzzlePiecesList = puzzle.createPuzzlePieces(PuzzleActivity.this, null, widthFinal, heightFinal, imageView, "/puzzles/", 3, 3);
                    getAdapter();
                    widthCheck = false;
                }
            }
        });

        setPuzzleListAdapter();
    }

    public void getAdapter() {
        RelativeLayout.LayoutParams params;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                PuzzlePiece piece = puzzlePiecesList.get(countGrid);
                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                int dimX = piece.getAnchorPoint().x - piece.getCenterPoint().x;
                int dimY = piece.getAnchorPoint().y - piece.getCenterPoint().y;

                params.setMargins(dimX, dimY, 0, 0);
                final ImageView button2 = new ImageView(this);
                button2.setId(generateViewId());
                button2.setTag(i + "," + j);

                button2.setImageResource(R.drawable.ic_1);

                button2.setOnDragListener(new MyDragListener(button2));
                button2.setLayoutParams(params);
                relativeLayout.addView(button2);

                Pieces piecesModel = new Pieces();
                piecesModel.setpX(i);
                piecesModel.setpY(j);
                piecesModel.setPosition(countGrid);
                piecesModel.setOriginalResource(puzzlePiecesList.get(countGrid).getImage());
                piecesModelListMain.add(piecesModel);
                Collections.shuffle(piecesModelListMain);
                piecesModelHashMap.put(i + "," + j, piecesModel);
                piecesModel = null;

                countGrid++;

            }
        }
    }


    public int generateViewId() {
        final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public void setPuzzleListAdapter() {
        if (puzzleListAdapter != null)
            puzzleListAdapter = null;

        puzzleListAdapter = new PuzzleAdapter(this, piecesModelListMain);
        rvPuzzle.setHasFixedSize(true);
        rvPuzzle.setAdapter(puzzleListAdapter);

        puzzleListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    static public class MyClickListener implements View.OnLongClickListener {

        // called when the item is long-clicked
        @Override
        public boolean onLongClick(View view) {
            // TODO Auto-generated method stub

            // create it from the object's tag
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(data, //data to be dragged
                    shadowBuilder, //drag shadow
                    view, //local data about the drag and drop operation
                    0   //no needed flags
            );

            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    public class MyDragListener implements View.OnDragListener {

        final ImageView imageView;

        public MyDragListener(final ImageView imageView) {
            this.imageView = imageView;
        }


        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;

                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackgroundResource(R.drawable.target_shape);    //change the shape of the view
                    break;

                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundResource(R.drawable.normal_shape);    //change the shape of the view back to normal
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    //v is the dynamic grid imageView, we accept the drag item
                    //view is listView imageView the dragged item
                    if (v == imageView) {
                        View view = (View) event.getLocalState();

                        ViewGroup owner = (ViewGroup) v.getParent();
                        if (owner == relativeLayout) {
                            String selectedViewTag = view.getTag().toString();

                            Pieces piecesModel = piecesModelHashMap.get(v.getTag().toString());
                            String xy = piecesModel.getpX() + "," + piecesModel.getpY();

                            if (xy.equals(selectedViewTag)) {
                                ImageView imageView = (ImageView) v;
                                imageView.setImageBitmap(piecesModel.getOriginalResource());
                                piecesModelListMain.remove(piecesModel);
                                setPuzzleListAdapter();
                                piecesModel = null;
                                if (piecesModelListMain.size() == 0) {
                                    Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "The correct Puzzle", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                piecesModel = null;
                                view.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Not the correct Puzzle", Toast.LENGTH_LONG).show();
                                break;
                            }
                        } else {
                            View view1 = (View) event.getLocalState();
                            view1.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else if (v == scrollView) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else if (v == rvPuzzle) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;
                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundResource(R.drawable.normal_shape);	//go back to normal shape
                default:
                    break;
            }
            return true;
        }
    }
}
