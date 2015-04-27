package main;

import exception.NoMoreDataException;
import util.LabelledData;
import util.ListGlyphBuilder;
import util.Utils;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    private static final String ERROR_LOAD_MES = "Une erreur c'est produite lors du chargement : \n";
    private static final String ERROR_TITLE = "Erreur !";
    private static final String ERROR_NO_MORE_DATA_MES = "Il n'y a plus assez de données";

    public static void main(String[] Arg) {
        try {
            ArrayList<ArrayList<byte[]>> data = Utils.loadImages("src/ressources/classe");
            ListGlyphBuilder gb = new ListGlyphBuilder(data);
            List<LabelledData> glyphEnsSource = gb.getNewGroupGlyph(100);
            List<LabelledData> glyphEnsTest = gb.getNewGroupGlyph(100);

            int size = glyphEnsTest.size();
            Random r = new Random();

            for (int i = 0; i < 100; i++) {
                int index = r.nextInt(size);
                LabelledData glyphTest = glyphEnsTest.get(index);

                List<LabelledData> neighbor = Utils.KNearestNeighbor(glyphTest.getGlyph(), glyphEnsSource, 11);

                System.out.println("Real class : " + glyphTest.getCls() + " Class found : " + Utils.determineGlyphClass(neighbor));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, ERROR_LOAD_MES + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        } catch (NoMoreDataException e) {
            JOptionPane.showMessageDialog(null, ERROR_NO_MORE_DATA_MES + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
