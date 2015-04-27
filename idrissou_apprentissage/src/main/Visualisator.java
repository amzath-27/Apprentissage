package main;

import java.io.IOException;
import java.util.ArrayList;

import util.Utils;

import javax.swing.*;

public class Visualisator {
    private static final String ERROR_LOAD_MES = "Une erreur c'est produite lors du chargement : \n";
    private static final String ERROR_LOAD_TITLE = "Erreur !";


    public static void main(String[] Arg) {
        try {
            ArrayList<ArrayList<byte[]>> data = Utils.loadImages("src/ressources/classe");
            Utils.viewGlyph(data.get(0).get(0));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, ERROR_LOAD_MES + e.getMessage(), ERROR_LOAD_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
