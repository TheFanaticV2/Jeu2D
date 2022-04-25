package application.modele;

public class Arbre extends Objet {

    private static int cpt = 0;
    private static String id = "A0";

    public Arbre(int x, int y) {
        super(id, x, y);
        id = "A" + cpt++;
    }
}
