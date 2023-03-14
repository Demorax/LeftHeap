package com.example.semesc;
import com.example.semesc.Zamek;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class HelloApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("SemesB");
        stage.setScene(scene);

        stage.show();

    }



    public static void main(String[] args) throws IOException {
        launch();

/*
        int arr[] = {25, 12, 6, 41, 18, 31, 53};
        Zamek[] pole = {new Zamek(1, "Pepa1", new Gps(20, 20)), new Zamek(1, "Pepa2", new Gps(15, 20)),
                new Zamek(1, "Pepa3", new Gps(35, 20)), new Zamek(1, "Pepa4", new Gps(40, 20))};
        AbstrHeapI test = new AbstrHeapI();
        test.setSize(pole.length);
        test.setN(50);
        test.setE(20);
        test.vybuduj(pole);

        test.vloz(new Zamek(2, "PepanaVloz", new Gps(50.7, 11.256)));
        Iterator iterator = test.vytvorIterator(eTypProhl.HLOUBKY);
        while (iterator.hasNext())
            System.out.print(iterator.next());



 */
    }
}