package com.mycompany.q.learning;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Player extends JPanel implements KeyListener, ActionListener {

    public int sayac = 0;
    public MapGenerator map;
    public int oyuncusatir;
    public int oyuncusutun;
    ArrayList<Integer> adimsay = new ArrayList<Integer>();
    public int adim = 0;
    public int delay = 1;
    public int satir = 25;
    public int sutun = 25;
    public int engel = 200;
    public int temp = 0;
    public boolean buldu = false;
    public Timer timer;
    public int R[][];
    public float Q[][];
    public int width = 1280 / satir;
    public int height = 790 / sutun;
    public int rota;

    public Player() throws IOException {
        map = new MapGenerator(satir, sutun, engel);
        timer = new Timer(delay, this);
        timer.start();
        oyuncusatir = map.getOyuncustart();
        oyuncusutun = 0;
        R = R_olustur();
        Q = new float[satir * sutun][satir * sutun];
        for (int i = 0; i < satir * sutun; i++) {
            for (int j = 0; j < satir * sutun; j++) {
                Q[i][j] = 0;
            }
        }
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paint(Graphics g) {
        map.draw((Graphics2D) g);
        g.setColor(Color.WHITE);
        g.fillOval(oyuncusutun * width, oyuncusatir * height, 10, 10);

        if (sayac != 1000) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.drawString("Learning", 50, 800);

        } else {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.drawString("Learning Completed", 50, 800);
        }

        String yuzde = "  %" + String.valueOf(sayac / 10);
        for (int i = 0; i < sayac; i += 1) {
            g.setColor(Color.BLACK);
            g.fillRect(50 + i, 810, 100, 40);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.drawString(yuzde, 50, 835);
        }

        if (buldu == false && sayac == 1000) {
            g.setFont(new Font("Arial Black", Font.ITALIC, 50));
            g.setColor(Color.YELLOW);
            g.drawString("Bitişe giden hiçbir yol yoktur!!!", 225, 400);
        }
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (temp == 0) {
            RastgeleGez();
        } else if (temp == 1 && buldu == true) {
            timer.setDelay(100);
            BilerekGez();
        }
        repaint();
    }

    public void RastgeleGez() {
        Random rand = new Random();
        if (sayac != 1000) {
            sayac += 1;
            while (map.map[oyuncusatir][oyuncusutun] != 4) {
                if(adim == 50000 ){
                    break;
                }
                rota = rand.nextInt(8); // 0 yukarı, rota saat yönündedir.
                if (rota == 0 && oyuncusatir != 0) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir - 1][oyuncusutun] != 2) {
                        //System.out.println("İlerle");
                        oyuncusatir -= 1;
                        adim += 1;
                    }

                } else if (rota == 1 && oyuncusatir != 0 && oyuncusutun != sutun - 1) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir - 1][oyuncusutun + 1] != 2) {
                        //System.out.println("İlerle");
                        oyuncusatir -= 1;
                        oyuncusutun += 1;
                        adim += 1;
                    }

                } else if (rota == 2 && oyuncusutun != sutun - 1) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir][oyuncusutun + 1] != 2) {
                        //System.out.println("İlerle");
                        oyuncusutun += 1;
                        adim += 1;
                    }

                } else if (rota == 3 && oyuncusutun != sutun - 1 && oyuncusatir != satir - 1) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir + 1][oyuncusutun + 1] != 2) {
                        //System.out.println("İlerle");
                        oyuncusatir += 1;
                        oyuncusutun += 1;
                        adim += 1;
                    }
                } else if (rota == 4 && oyuncusatir != satir - 1) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir + 1][oyuncusutun] != 2) {
                        //System.out.println("İlerle");
                        oyuncusatir += 1;
                        adim += 1;
                    }

                } else if (rota == 5 && oyuncusatir != satir - 1 && oyuncusutun != 0) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir + 1][oyuncusutun - 1] != 2) {
                        //System.out.println("İlerle");
                        oyuncusatir += 1;
                        oyuncusutun -= 1;
                        adim += 1;
                    }

                } else if (rota == 6 && oyuncusutun != 0) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir][oyuncusutun - 1] != 2) {
                        //System.out.println("İlerle");
                        oyuncusutun -= 1;
                        adim += 1;
                    }

                } else if (rota == 7 && oyuncusatir != 0 && oyuncusutun != 0) {
                    Q = Q_hesapla(Q, R, oyuncusatir, oyuncusutun, rota);
                    if (map.map[oyuncusatir - 1][oyuncusutun - 1] != 2) {
                        //System.out.println("İlerle");
                        oyuncusatir -= 1;
                        oyuncusutun -= 1;
                        adim += 1;
                    }
                }

                if (map.map[oyuncusatir][oyuncusutun] == 4) {
                    buldu = true;
                    adimsay.add(adim);
                    adim = 0;
                    oyuncusatir = map.getOyuncustart();
                    oyuncusutun = 0;
                    break;
                }

            }
        } else {
            Q = Q_duzenle(Q);
            temp += 1;
        }

    }

    public void BilerekGez() {
        float enbuyuk = 0;
        int gecici = 0;
        int karedeger = oyuncusatir * satir + oyuncusutun;
        for (int i = 0; i < satir * sutun; i++) {
            if (Q[karedeger][i] > enbuyuk) {
                enbuyuk = Q[karedeger][i];
                gecici = i;
            }
        }
        int rota = karedeger - gecici;
        timer.start();
        if (rota == satir) {
            oyuncusatir -= 1;
        } else if (rota == satir - 1) {
            oyuncusatir -= 1;
            oyuncusutun += 1;
        } else if (rota == -1) {
            oyuncusutun += 1;
        } else if (rota == -(sutun + 1)) {
            oyuncusatir += 1;
            oyuncusutun += 1;
        } else if (rota == -satir) {
            oyuncusatir += 1;
        } else if (rota == -(satir - 1)) {
            oyuncusatir += 1;
            oyuncusutun -= 1;
        } else if (rota == 1) {
            oyuncusutun -= 1;
        } else if (rota == (satir + 1)) {
            oyuncusatir -= 1;
            oyuncusutun -= 1;
        }
        if (oyuncusatir == map.getOyuncufinish() && oyuncusutun == sutun - 1) {
            temp += 1;
        } else {
            map.map[oyuncusatir][oyuncusutun] = 5;
        }

        gecici += 1;
        System.out.println("Q değeri:"+enbuyuk + " Kare:" + gecici);
    }

    public int[][] R_olustur() {
        R = new int[satir * sutun][satir * sutun];
        for (int i = 0; i < satir * sutun; i++) {
            for (int j = 0; j < satir * sutun; j++) {
                R[i][j] = -1;
            }
        }
        for (int i = 1; i < satir - 1; i++) {
            for (int j = 1; j < sutun - 1; j++) {
                int karedeger = i * satir + j;
                R[karedeger][karedeger - 1] = 0;
                R[karedeger][karedeger + 1] = 0;
                R[karedeger][karedeger - (satir - 1)] = 0;
                R[karedeger][karedeger + (satir - 1)] = 0;
                R[karedeger][karedeger - satir] = 0;
                R[karedeger][karedeger + satir] = 0;
                R[karedeger][karedeger - (satir + 1)] = 0;
                R[karedeger][karedeger + (satir + 1)] = 0;
            }
        }
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                int karedeger = i * satir + j;
                if (karedeger == 0) {
                    R[karedeger][karedeger + 1] = 0;
                    R[karedeger][karedeger + satir] = 0;
                    R[karedeger][karedeger + (satir + 1)] = 0;
                }
                if (karedeger == satir - 1) {
                    R[karedeger][karedeger - 1] = 0;
                    R[karedeger][karedeger + satir] = 0;
                    R[karedeger][karedeger + (satir - 1)] = 0;
                }
                if (karedeger == (satir * sutun) - satir) {
                    R[karedeger][karedeger + 1] = 0;
                    R[karedeger][karedeger - satir] = 0;
                    R[karedeger][karedeger - (satir - 1)] = 0;
                }
                if (karedeger == (satir * sutun) - 1) {
                    R[karedeger][karedeger - 1] = 0;
                    R[karedeger][karedeger - satir] = 0;
                    R[karedeger][karedeger - (satir + 1)] = 0;
                }
            }
        }

        for (int i = 1; i < satir - 1; i++) {
            int karedeger = i * satir;
            R[karedeger][karedeger + 1] = 0;
            R[karedeger][karedeger - satir] = 0;
            R[karedeger][karedeger - (satir - 1)] = 0;
            R[karedeger][karedeger + satir] = 0;
            R[karedeger][karedeger + (satir + 1)] = 0;
        }

        for (int i = 1; i < satir - 1; i++) {
            int j = satir - 1;
            int karedeger = i * satir + j;
            R[karedeger][karedeger - 1] = 0;
            R[karedeger][karedeger - satir] = 0;
            R[karedeger][karedeger + (satir - 1)] = 0;
            R[karedeger][karedeger + satir] = 0;
            R[karedeger][karedeger - (satir + 1)] = 0;
        }
        for (int j = 1; j < satir - 1; j++) {
            int karedeger = j;
            R[karedeger][karedeger - 1] = 0;
            R[karedeger][karedeger + 1] = 0;
            R[karedeger][karedeger + (satir - 1)] = 0;
            R[karedeger][karedeger + satir] = 0;
            R[karedeger][karedeger + (satir + 1)] = 0;
        }
        for (int j = 1; j < satir - 1; j++) {
            int i = satir - 1;
            int karedeger = i * satir + j;
            R[karedeger][karedeger - 1] = 0;
            R[karedeger][karedeger - satir] = 0;
            R[karedeger][karedeger - (satir - 1)] = 0;
            R[karedeger][karedeger + 1] = 0;
            R[karedeger][karedeger - (satir + 1)] = 0;
        }

        if (map.getOyuncufinish() == 0) {
            int karedeger = map.getOyuncufinish() * satir + (satir - 1);
            R[karedeger - 1][karedeger] = 100;
            R[karedeger + (satir - 1)][karedeger] = 100;
            R[karedeger + satir][karedeger] = 100;
        }
        if (map.getOyuncufinish() == (satir - 1)) {
            int karedeger = map.getOyuncufinish() * satir + (satir - 1);
            R[karedeger - 1][karedeger] = 100;
            R[karedeger - (satir + 1)][karedeger] = 100;
            R[karedeger - satir][karedeger] = 100;
        }
        if (map.getOyuncufinish() != (satir - 1) && map.getOyuncufinish() != 0) {
            int karedeger = map.getOyuncufinish() * satir + (satir - 1);

            R[karedeger - 1][karedeger] = 100;
            R[karedeger + (satir - 1)][karedeger] = 100;
            R[karedeger + satir][karedeger] = 100;
            R[karedeger - (satir + 1)][karedeger] = 100;
            R[karedeger - satir][karedeger] = 100;
        }

        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                if (map.map[i][j] == 2) {
                    int karedeger = i * satir + j;
                    for (int k = 0; k < satir * sutun; k++) {
                        R[k][karedeger] = -1;

                    }
                }
            }
        }

        return R;
    }

    public float[][] Q_hesapla(float[][] Q, int[][] R, int oyuncusatir, int oyuncusutun, int rota) {
        float y = (float) 0.9;
        int karedeger = oyuncusatir * satir + oyuncusutun;
        int karedeger2 = 0;
        if (rota == 0) {
            karedeger2 = karedeger - satir;
        } else if (rota == 1) {
            karedeger2 = karedeger - (satir - 1);
        } else if (rota == 2) {
            karedeger2 = karedeger + 1;
        } else if (rota == 3) {
            karedeger2 = karedeger + (satir + 1);
        } else if (rota == 4) {
            karedeger2 = karedeger + satir;
        } else if (rota == 5) {
            karedeger2 = karedeger + (satir - 1);
        } else if (rota == 6) {
            karedeger2 = karedeger - 1;
        } else if (rota == 7) {
            karedeger2 = karedeger - (satir + 1);
        }

        Q[karedeger][karedeger2] = (float) R[karedeger][karedeger2] + (y * (float) Maxhesapla(Q, karedeger2));

        return Q;
    }

    public float[][] Q_duzenle(float[][] Q) {
        for (int j = 0; j < satir * sutun; j++) {
            Q[j][j] = -1;
        }
        return Q;
    }

    public float Maxhesapla(float[][] Q, int karedeger) {
        float enbuyuk = 0;

        for (int i = 0; i < satir * sutun; i++) {
            if (Q[karedeger][i] > enbuyuk) {
                enbuyuk = Q[karedeger][i];
            }
        }

        return enbuyuk;
    }

}
