package application.modele;

public class Bois extends Objet {

    private static int cpt = 0;
    private static String id = "B0";

    public Bois(int x, int y) {
        super(id, x, y);
        id = "B" + cpt++;
    }
}
