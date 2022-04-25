package application.modele;

public class Sommet {
    private int x;
    private int y;
    private int groundType;

    public Sommet(int x, int y, int groundType) {
        this.x = x;
        this.y = y;
        this.groundType = groundType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGroundType() {
        return groundType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sommet other = (Sommet) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
