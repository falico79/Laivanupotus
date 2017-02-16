package opiskelu.laivanupotus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



class Cell {
    private enum Shot { notFired, hit, miss }
    private Content content;
    private Shot status;

    private int x, y;

    public Cell(int _x, int _y) {
        status = Shot.notFired;
        content = new Content("water");

        x = _x;
        y = _y;
    }
    public void PlaceShip(Ship ship) {
        content = ship;
    }



    public boolean HitIt() {
        if(content.getType().equals("water")) {
            status = Shot.miss;
        } else {
            status = Shot.hit;
            ((Ship)content).hit();

        }
        return false;
    }
}

class Grid {
    private Cell cells[][];

    public Grid() {
        cells = new Cell[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public boolean Place(Ship ship, int posx, int posy, int orientation) {
        if(ship.Placed()) {
            return false;
        }
        return TestCellAndPlace(ship, posx, posy, orientation);

    }
    // TODO Ship Placement tests and placement
    public boolean TestCellAndPlace(Ship ship, int posx, int posy, int orientation) {
        return true;
    }
}

class Content {
    protected String type;

    public Content(String t) {
        type = t;
    }

    String getType() {
        return type;
    }
}

class Ship extends Content {
    private int length;
    private int hit_count;
    private boolean placed;

    public Ship(int shipLength, String shipname) {
        super(shipname);
        length = shipLength;
        hit_count = 0;
        placed = false;
    }

    public void Place() {
        placed = true;
    }

    public boolean Placed() {
        return placed;
    }

    public void hit() {
        hit_count++;
    }

    public int Length() {
        return length;
    }
    public boolean sunk() {
        if(hit_count==length) {
            return true;
        }
        return false;
    }
}

final class GameBoard extends View {
    int view_width, view_height;
    public GameBoard( Context context) {
        super(context);
    }

    public void onSizeChanged(int current_width_of_this_view, int current_height_of_this_view, int old_width_of_this_view, int old_height_of_this_view) {
        view_width = current_width_of_this_view;
        view_height = current_height_of_this_view;

    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
