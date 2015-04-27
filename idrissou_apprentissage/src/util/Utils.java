package util;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Utils {
	public static final int imWidth = 28;
	public static final int imHeight = 28;
	public static final int imSize = imWidth * imHeight;

	public static ArrayList<ArrayList<byte[]>> loadImages(String prefixPath) throws IOException {
		ArrayList<ArrayList<byte[]>> classes= new ArrayList<ArrayList<byte[]>>();
		for (int i=0; i<10; ++i) classes.add(new ArrayList<byte[]>());
		
		for (int c=0; c<10; ++c) {
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(prefixPath+c));
			int read=0;
			
			ArrayList<byte[]> cls=classes.get(c);
			do {
				byte[] img=new byte[imSize];
				read=is.read(img);
				if (read == imSize) cls.add(img);
			} while (read == imSize);
			is.close();
		}
		return classes;  
	};

	@SuppressWarnings("serial")
	public static class GlyphViewer extends JComponent{
		private byte[] glyph;
		
		public void setGlyph(byte[]g ) { glyph=g;}
		
		@Override
		public void paint(Graphics g){
			int height = 280;
			int width = 280;
			g.setColor(Color.black);
			g.fillRect(0,0,height,width);

			for (int j=0; j<Utils.imHeight; ++ j)
				for (int i=0; i<Utils.imWidth; ++ i) {
					int grey=255-2*glyph[j*28+i];
					g.setColor(new Color(grey, grey, grey));
					g.fillRect(i*10,j*10,9, 9); 
				}			
		}
	}

	public static void viewGlyph(byte[] glyph) {
		JFrame frmMain = new JFrame();
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMain.setSize(450, 450);
        //frmMain.getContentPane().setLayout(new GridLayout(2, 2));


        GlyphViewer gv=new GlyphViewer();
        gv.setGlyph(glyph);
        frmMain.getContentPane().add(new JPanel().add(gv));

/*        GlyphViewer gv2=new GlyphViewer();
        gv2.setGlyph(dataset.get(6).get(1026));
        frmMain.getContentPane().add(new JPanel().add(gv2));
        //frmMain.getContentPane().add(gv2);*/
        frmMain.setVisible(true);
	}

    public static double computeEuclideDistance(byte[] g1, byte[] g2) {
        int maxSize = Math.min(g1.length, g2.length);

        double res = 0;
        int d = 0;
        for (int i = 0; i < maxSize; i++) {
        	d = (g1[i] - g2[i]) * (g1[i] - g2[i]);
            res += d;
        }

        return Math.sqrt(res);
    }

    public static List<LabelledData> KNearestNeighbor(byte[] glyph, List<LabelledData> all, int k) {
        SortedMap<Double, LabelledData> distGroup = new TreeMap<Double, LabelledData>();

        for (LabelledData data : all) {
            double dst = Utils.computeEuclideDistance(glyph, data.getGlyph());
            distGroup.put(dst, data);
        }

        Collection<LabelledData> sorted = distGroup.values();
        ArrayList<LabelledData> out = new ArrayList<LabelledData>();
        int i = 0;

        for (LabelledData ld : sorted) {
            if (i >= k) {
                break;
            }
            out.add(ld);
            i++;
        }

        return out;
    }

    public static int determineGlyphClass(List<LabelledData> neighbors) {
        Map<Integer, Integer> classNb = new HashMap<Integer, Integer>();

        for (LabelledData ld : neighbors) {
            Integer nb = classNb.get(ld.getCls());
            if (nb == null) {
                nb = 0;
            }
            nb++;

            classNb.put(ld.getCls(), nb);
        }

        Map.Entry<Integer, Integer> maxEntry = null;

        for (Map.Entry<Integer, Integer> entry : classNb.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        
        return maxEntry.getKey();
    }
    
    public static double erreurMoyenne(List<LabelledData> ensGen, List<LabelledData> ensTest, int k) {
    	double EM = 0;
    	
    	for (LabelledData ld : ensTest) {
    		List<LabelledData> neighbor = KNearestNeighbor(ld.getGlyph(), ensGen, k);
    		if (ld.getCls() != determineGlyphClass(neighbor)) {
    			EM++;
    		}
    	}
    	
    	return EM / ensTest.size();
    }

}
