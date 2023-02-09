/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.q.learning;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class MapGenerator extends JPanel {

    public int map[][];
    public int width;
    public int height;
    public int oyuncustart;
    public int oyuncufinish;
    public File dosya;
    public int satir;
    public int sutun;
    
    public MapGenerator(int satir, int sutun, int engel) throws IOException {
        
        this.satir = satir;
        this.sutun = sutun;
        Random rand = new Random();
        int gecicisatir, gecicisutun, temp;
        
        oyuncustart = rand.nextInt(satir);
        oyuncufinish = rand.nextInt(satir);

        ArrayList<Integer> Satir = new ArrayList<Integer>();
        ArrayList<Integer> Sutun = new ArrayList<Integer>();
        dosya = new File("engel.txt");
        if (!dosya.exists()) {
            dosya.createNewFile();
        } else if (dosya.exists()) {
            dosya.delete();
        }

        FileWriter fileWriter = null;

        for (int i = 0; i < engel; i++) {
            temp = 0;
            gecicisatir = rand.nextInt(satir);
            gecicisutun = rand.nextInt(sutun);

            if ((gecicisatir == 0 && gecicisutun == oyuncustart) || (gecicisatir == (satir-1) && gecicisutun == oyuncufinish)) {
                i--;
                continue;
            }
            if (Satir.size() == 0) {
                Satir.add(gecicisatir);
                Sutun.add(gecicisutun);
                fileWriter = new FileWriter(dosya, true);
                BufferedWriter bWriter = new BufferedWriter(fileWriter);
                gecicisatir += 1;
                gecicisutun += 1;
                bWriter.write("[" + gecicisatir + "," + gecicisutun + ",K" + "]\n");
                bWriter.close();
                continue;
            }
            for (int j = 0; j < Satir.size(); j++) {

                if (Satir.get(j) == gecicisatir && Sutun.get(j) == gecicisutun) {
                    i--;
                    temp = 1;
                    break;
                }
            }
            if (temp == 0) {
                Satir.add(gecicisatir);
                Sutun.add(gecicisutun);
                fileWriter = new FileWriter(dosya, true);
                BufferedWriter bWriter = new BufferedWriter(fileWriter);
                gecicisatir += 1;
                gecicisutun += 1;
                bWriter.write("[" + gecicisatir++ + "," + gecicisutun++ + ",K" + "]\n");
                bWriter.close();
            }

        }

        map = new int[satir][sutun];
        width = 1280 / satir;
        height = 790 / sutun;
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                map[i][j] = 1;
            }
        }

        for (int i = 0; i < engel; i++) {
            map[Satir.get(i)][Sutun.get(i)] = 2;
        }

        map[oyuncustart][0] = 3;
        map[oyuncufinish][satir-1] = 4;
        oyuncustart +=1;
        oyuncufinish +=1;
        fileWriter = new FileWriter(dosya, true);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        bWriter.write("[" + oyuncustart + "," + "1,S" + "]\n");
        bWriter.write("[" + oyuncufinish + "," + sutun +",F" + "]\n");
        bWriter.close();
        oyuncustart -=1;
        oyuncufinish -=1;
        
    }

    public void draw(Graphics2D g) {
        
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int karedeger = ((i*satir)+j+1);
                if (map[i][j] == 1) {
                    g.setColor(Color.lightGray);
                    g.fillRect(j * width, i * height, width, height);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.black);
                    g.drawRect(j * width, i * height, width, height);
                    g.drawString( String.valueOf(karedeger) , j * width+7, i * height + height-7);

                }
                if (map[i][j] == 2) {
                    g.setColor(Color.red);
                    g.fillRect(j * width, i * height, width, height);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.black);
                    g.drawRect(j * width, i * height, width, height);
                    g.drawString(String.valueOf(karedeger), j * width+7, i * height + height-7);

                }
                if (map[i][j] == 3) {
                    g.setColor(Color.blue);
                    g.fillRect(j * width, i * height, width, height);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.black);
                    g.drawRect(j * width, i * height, width, height);
                    g.drawString(String.valueOf(karedeger), j * width+7, i * height + height-7);
                }
                if (map[i][j] == 4) {
                    g.setColor(Color.green);
                    g.fillRect(j * width, i * height, width, height);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.black);
                    g.drawRect(j * width, i * height, width, height);
                    g.drawString(String.valueOf(karedeger), j * width+7, i * height + height-7);
                }
                if (map[i][j] == 5) {
                    g.setColor(Color.yellow);
                    g.fillRect(j * width, i * height, width, height);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.black);
                    g.drawRect(j * width, i * height, width, height);
                    g.drawString(String.valueOf(karedeger), j * width+7, i * height + height-7);
                }
            }
        }

    }

    public int getOyuncustart() {
        return oyuncustart;
    }

    public void setOyuncustart(int oyuncustart) {
        this.oyuncustart = oyuncustart;
    }

    public int getOyuncufinish() {
        return oyuncufinish;
    }

    public void setOyuncufinish(int oyuncufinish) {
        this.oyuncufinish = oyuncufinish;
    }
    
}
