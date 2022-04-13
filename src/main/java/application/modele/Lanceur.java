package application.modele;

import application.Param;

public class Lanceur {
    public static void main(String[] args) {
        Grille grille = new Grille(Param.WIDTH, Param.HEIGHT);
        System.out.println("Base   Perso x = " + grille.getPerso().getX() + " y = " + grille.getPerso().getY());
        grille.getPerso().seDeplacerHaut();
        System.out.println("Haut   Perso x = " + grille.getPerso().getX() + " y = " + grille.getPerso().getY());
        grille.getPerso().seDeplacerBas();
        System.out.println("Bas    Perso x = " + grille.getPerso().getX() + " y = " + grille.getPerso().getY());
        grille.getPerso().seDeplacerGauche();
        System.out.println("Gauche Perso x = " + grille.getPerso().getX() + " y = " + grille.getPerso().getY());
        grille.getPerso().seDeplacerDroite();
        System.out.println("Droite Perso x = " + grille.getPerso().getX() + " y = " + grille.getPerso().getY());

    }
}
