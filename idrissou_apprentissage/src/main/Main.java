package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import util.LabelledData;
import util.ListGlyphBuilder;
import util.Utils;
import exception.NoMoreDataException;

public class Main {
    private static final String ERROR_LOAD_MES = "Une erreur c'est produite lors du chargement : \n";
    private static final String ERROR_TITLE = "Erreur !";
    private static final String ERROR_NO_MORE_DATA_MES = "Il n'y a plus assez de données";
    private static ListGlyphBuilder glyphGenerator;

    public static void main(String[] Arg) {
        try {
            ArrayList<ArrayList<byte[]>> data = Utils.loadImages("src/ressources/classe");
            glyphGenerator = new ListGlyphBuilder(data);
            System.out.println("Question 2.2 : ");
            statisticWithKChange(100, 10, 50, 50);
            System.out.println("Question 2.3 : ");
            statisticWithModelChange(3, 10, 10, 100, 50);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, ERROR_LOAD_MES + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        } catch (NoMoreDataException e) {
            JOptionPane.showMessageDialog(null, ERROR_NO_MORE_DATA_MES + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void statisticWithKChange(int maxK, int nbModels, int modelSize, int testSize) throws NoMoreDataException {
    	List<List<LabelledData>> models = glyphGenerator.getNbGroupGlyph(nbModels, modelSize);
    	List<LabelledData> test = glyphGenerator.getNewGroupGlyph(testSize);
    	
    	double EMT = 0;
    	double EMS = 0;
    	maxK++;
    	
    	for(int k = 1; k < maxK; k++) {
    		EMT = 0;
    		EMS = 0;
    		
    		for (List<LabelledData> curModel : models) {
    			EMS += Utils.erreurMoyenne(curModel, curModel, k);
    			EMT += Utils.erreurMoyenne(curModel, test, k);
    		}
    		EMT /= nbModels;
    		EMS /= nbModels;
    		System.out.println(k + "\t" + EMT + "\t" + EMS);
    	}
    }
    
    public static void statisticWithModelChange(int k, int nbModels, int minModelSize, int maxModelSize, int testSize) throws NoMoreDataException {
    	if (minModelSize > maxModelSize) {
    		throw new IllegalArgumentException("Min > Max");
    	}
    	List<LabelledData> test = glyphGenerator.getNewGroupGlyph(testSize);
    	
    	double EMT = 0;
    	double EMS = 0;
    	
    	for(int i = minModelSize; i <= maxModelSize; i++) {
    		EMT = 0;
    		EMS = 0;
    		
    		List<List<LabelledData>> models = glyphGenerator.getNbGroupGlyphWithoutRetail(nbModels, i);
    		
    		for (List<LabelledData> curModel : models) {
    			EMS += Utils.erreurMoyenne(curModel, curModel, k);
    			EMT += Utils.erreurMoyenne(curModel, test, k);
    		}
    		EMT /= nbModels;
    		EMS /= nbModels;
    		System.out.println(i + "\t" + EMT + "\t" + EMS);
    	}
    }
}
