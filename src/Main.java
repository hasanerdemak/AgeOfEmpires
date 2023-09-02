import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws AgeOfEmpiresException {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = null;
            try {
                mainFrame = new MainFrame();
            } catch (AgeOfEmpiresException e) {
                throw new RuntimeException(e);
            }
            mainFrame.setVisible(true);
            GameManager.getInstance().setMainFrame(mainFrame);
        });

        //GameManager.getInstance().startGame();

        /*

        Game g = new Game(2); // iki kişilik oyun başlatır
        g.getPlayer(0).getWorker(0).move(1, 2); // İlk oyuncu işçisini hareket ettirir
        g.getPlayer(1).purchase(new Cavalry()); // İkinci oyuncu bir atlı alır
        // İlk oyuncu 1,2 konumuna bir üniversite kurar
        g.getPlayer(0).getWorker(0).build(new University());
        // İkinci oyuncu ilk ürettiği askeri 5,5 konumuna taşır
        g.getPlayer(1).getSoldier(0).move(95, 46);
        // ekranın o anki basılır, Ayrıntılar için MapInterface’e bakın
        g.getMap().print();
        g.save_binary("binary_file"); // oyunun o hali binary olarak kaydedilmiştir.
        g.save_text("text_file"); // oyunun o hali text olarak kaydedilmiştir.

        // oyunun o hali g1 nesnesindedir. Dosyanın binrary olduğunu Boolean değer ile gösterilir
        Game g1 = new Game("binary_file", true);
        // oyunun o hali g2 nesnesindedir. Dosyanın binrary olmadığı Boolean değer ile gösterilir
        Game g2 = new Game("text_file", false);
        // ilk oyuncu universiteden piyade eğitimi yaptırır
        g2.getPlayer(0).getUniversity().trainInfantry();
        // ilk oyuncu iki kez üst üste oynadığı için AgeOfEmpiresException atılmalı
        //g2.getPlayer(0).getUniversity().trainInfantry();


         */
    }

}