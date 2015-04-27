package util;

import exception.NoMoreDataException;

import java.util.*;

public class ListGlyphBuilder {
    private List<List<LabelledData>> data;

    public ListGlyphBuilder(ArrayList<ArrayList<byte[]>> data) {
        this.data = new ArrayList<List<LabelledData>>();
        int i = 0;

        for (ArrayList<byte[]> tab : data) {
            ArrayList<LabelledData> labelledDatas = new ArrayList<LabelledData>();
            this.data.add(i, labelledDatas);
            for (byte[] glyph : tab) {
                labelledDatas.add(new LabelledData(i, glyph));
            }
            i++;
        }
    }

    public List<LabelledData> getNewGroupGlyph(int size) throws NoMoreDataException {
        Random r = new Random();
        List<LabelledData> resultat = new ArrayList<LabelledData>();

        for (List<LabelledData> l : data) {
            int lsize = l.size();

            if (lsize < size) {
                throw new NoMoreDataException("Not more Data");
            }

            for (int i = 0; i < size; i++) {
                int index = r.nextInt(lsize);

                resultat.add(l.get(index));
                l.remove(index);
                lsize--;
            }
        }
        return resultat;
    }
    
    public List<LabelledData> getNewGroupGlyphWithoutRetail(int size) throws NoMoreDataException {
        Random r = new Random();
        List<LabelledData> resultat = new ArrayList<LabelledData>();

        for (List<LabelledData> l : data) {
            int lsize = l.size();

            if (lsize < size) {
                throw new NoMoreDataException("Not more Data");
            }

            for (int i = 0; i < size; i++) {
                int index = r.nextInt(lsize);

                resultat.add(l.get(index));
            }
        }
        return resultat;
    }
    
    public List<List<LabelledData>> getNbGroupGlyphWithoutRetail(int nb, int size) throws NoMoreDataException {
    	ArrayList<List<LabelledData>> result = new ArrayList<List<LabelledData>>();
    	
    	for (int i = 0; i < nb; i++) {
    		result.add(getNewGroupGlyphWithoutRetail(size));
    	}
    	
    	return result;
    }
    
    public List<List<LabelledData>> getNbGroupGlyph(int nb, int size) throws NoMoreDataException {
    	ArrayList<List<LabelledData>> result = new ArrayList<List<LabelledData>>();
    	
    	for (int i = 0; i < nb; i++) {
    		result.add(getNewGroupGlyph(size));
    	}
    	
    	return result;
    }
}
