package tools;

import java.awt.*;
import java.io.Serializable;

public class Point implements Serializable {
    private int x, y;
    private Color color;

    public Point(int i, int j, Color c){
        x = i;
        y = j;
        color = c;
    }
    public Point(Point p2){
        this.x = p2.x;
        this.y = p2.y;
        this.color = p2.color;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public Color getColor(){ return color; }

    public void setX(int i){ x = i; }
    public void setY(int j){ y = j; }
    public void setColor(Color c){ color = c; }

    public boolean equals(Point p2){
        if (this.x == p2.x && this.y == p2.y){
            return true;
        }
        return false;
    }

    public void move(int i, int j){
        x += i;
        y += j;
    }
}
