package opiskelu.laivanupotus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

class Coordinate {
    public int x, y;

    public Coordinate(int _x, int _y) {
        x = _x;
        y = _y;
    }
}

class Cell {

    enum Shot { notFired, hit, miss }
    private Content content;
    public Shot status;

    private Coordinate position;

    public Cell(Coordinate pos) {
        status = Shot.notFired;
        content = new Content("water");

        position = pos;
    }
    public void PlaceShip(Ship ship) {
        content = ship;
    }

    public Content getContent() {
        return content;
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
                Coordinate coord = new Coordinate(i, j);


                cells[i][j] = new Cell(coord);
            }
        }
    }

    public boolean Place(Ship ship, Coordinate coord, String orientation) {
        if(ship.Placed()) {
            return false;
        }

        for(int attempt = 1; attempt <= 2; attempt++) {
            Coordinate temp = new Coordinate(coord.x, coord.y);
            for(int i = 0; i < ship.Length(); i++) {
                temp = TestCellAndPlace(ship, temp, orientation, attempt);
                if(temp==null) return false;
            }
        }
        return true;

    }
    // TODO Ship Placement tests and placement
    private Coordinate TestCellAndPlace(Ship ship, Coordinate coord, String orientation, int attempt) {
        if(coord.x >= 10 ||coord.y >= 10) return null;
        if(attempt == 2) {
            cells[coord.x][coord.y].PlaceShip(ship);
        }
        if(attempt == 1) {
            if(!cells[coord.x][coord.y].getContent().equals("water")) {
                return null;
            }
        }

        if (orientation.equals("horizontal")) {
            return new Coordinate(coord.x + 1, coord.y);
        } else {
            return new Coordinate(coord.x, coord.y + 1);
        }

    }

    public void hit(Coordinate coord) {
        if(cells[coord.x][coord.y].status == Cell.Shot.notFired) {
            cells[coord.x][coord.y].HitIt();
        }
    }
}

class Content {
    private String type;

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
    public boolean Sunk() {
        if(hit_count==length) {
            return true;
        }
        return false;
    }
}

/*
TODO Player



*/

class Player {
    private Grid grid;
    private String name;
    private Ship ships[];

    Player(String name) {
        this.name = name;

        ships = new Ship[5];
        ships[0] = new Ship(5, "Lentotukialus");
        ships[1] = new Ship(4, "Taistelulaiva");
        ships[2] = new Ship(3, "Hävittäjä");
        ships[3] = new Ship(3, "Sukellusvene");
        ships[4] = new Ship(2, "Tiedustelualus");

        grid = new Grid();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Grid getGrid() {
        return grid;
    }

}

class Enemy {
    private Grid grid;
    private String name;

    Enemy(String name) {
        this.name = name;

        grid = new Grid();
    }
    public Grid getGrid() {
        return grid;
    }
}

final class GameBoard extends View {
    private int view_width;
    private int view_height;
    private Player player;
    private Enemy enemy;
    public GameBoard( Context context, String playerName, String enemyName) {
        super(context);
        player = new Player(playerName);
        enemy = new Enemy(enemyName);
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
