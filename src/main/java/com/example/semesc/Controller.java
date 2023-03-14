package com.example.semesc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;

import static com.example.semesc.eTypKey.GPS;

public class Controller implements Initializable {

    private Iterator<Zamek> iterator;
    private Iterator<Zamek> iteratorHalda;
    private final Pamatky pamatky = new Pamatky();
    private final AbstrHeapI abstrHeapI = new AbstrHeapI();
    private final Generator generator = new Generator();
    private final ObservableList<eTypProhl> iteratorList = FXCollections.observableArrayList(eTypProhl.SIRKY, eTypProhl.HLOUBKY);
    private final ObservableList<eTypKey> poziceList = FXCollections.observableArrayList(GPS, eTypKey.NAZEV);
    ObservableList<Zamek> obList = FXCollections.observableArrayList();
    ObservableList<Zamek> obListHalda = FXCollections.observableArrayList();
    Zamek[] array;

    private eTypProhl prohl = eTypProhl.SIRKY;


    @FXML
    private ListView<Zamek> listViewHalda;

    @FXML
    private Button btnZrus1;

    @FXML
    private Button btnNajdiHalda;

    @FXML
    private Button btnOdeberMax;

    @FXML
    private Button btnIterator1;

    @FXML
    private Button btnVloz1;
    @FXML
    private Button btnExport;

    @FXML
    private Button btnGeneruj;

    @FXML
    private Button btnImport;

    @FXML
    private Button btnIterator;

    @FXML
    private Button btnNajdi;

    @FXML
    private Button btnNajdi1;

    @FXML
    private Button btnOdeber;

    @FXML
    private Button btnPrebuduj;

    @FXML
    private Button btnVloz;

    @FXML
    private Button btnZmenaKlice;

    @FXML
    private Button btnZrus;

    @FXML
    private ComboBox<eTypKey> cb;

    @FXML
    private ComboBox<eTypProhl> cb1;

    @FXML
    private ListView<Zamek> listView;

    @FXML
    private TextField tfE;

    @FXML
    private TextField tfN;

    @FXML
    private TextField tfNajdi;

    @FXML
    private TextField tfNazev;

    @FXML
    private TextField tfOdeber;


    ///Halda
    @FXML
    void btnIterator1OA(ActionEvent event) {
        this.iteratorHalda = abstrHeapI.vytvorIterator(this.cb1.getValue());
        listAktHalda();
    }
    @FXML
    void btnNajdiHaldaOA(ActionEvent event) {
        this.iterator = pamatky.vytvorIterator(this.cb1.getValue());
        int countHalda = 0;
        if (!obListHalda.isEmpty()) {
            this.iteratorHalda = abstrHeapI.vytvorIterator(eTypProhl.HLOUBKY);
            while (this.iteratorHalda.hasNext()){
                this.iteratorHalda.next();
                countHalda++;
            }
            System.out.println(countHalda);
            this.array = new Zamek[countHalda];
            this.iteratorHalda = abstrHeapI.vytvorIterator(eTypProhl.HLOUBKY);
            int i = 0;
            while (this.iteratorHalda.hasNext()){
                this.array[i] = this.iteratorHalda.next();
                i++;
            }
        }
        if (!iterator.hasNext()) {
            return;
        }
        int count = 0;
        while (this.iterator.hasNext()){
            this.iterator.next();
            count++;
        }
        int i;
        if (!obListHalda.isEmpty()) {
            this.array = Arrays.copyOf(this.array, this.array.length + count);
            i = countHalda;
        } else {
            this.array = new Zamek[count];
            i = 0;
        }
        this.iterator = pamatky.vytvorIterator(this.cb1.getValue());
        boolean test = true;
        int countSame = 0;
        while (this.iterator.hasNext()){
            Zamek temp = iterator.next();
            for (int j = 0; j < countHalda; j++) {
                if (this.array[j].equals(temp)) {
                    test = false;
                    countSame++;
                }
            }
            if (test) {
                this.array[i] = temp;
                i++;
            }
        }
        this.array = Arrays.copyOf(this.array, this.array.length - countSame);
        String klic = this.tfNajdi.getText();
        String[] data = klic.split(";");
        this.abstrHeapI.setN(Double.parseDouble(data[0]));
        this.abstrHeapI.setE(Double.parseDouble(data[1]));
        this.abstrHeapI.setSize(this.array.length);
        this.abstrHeapI.vybuduj(this.array);
        listAktHalda();
        this.listView.getSelectionModel().select(this.abstrHeapI.zprusupniMax());
    }

    @FXML
    void btnOdeberMaxOA(ActionEvent event) {
        this.abstrHeapI.odeberMax();
        listAktHalda();
    }
    @FXML
    void btnVloz1OA(ActionEvent event) {
        this.abstrHeapI.vloz(new Zamek(generator.generujIdZamku(), this.tfNazev.getText(), new Gps(Double.parseDouble(this.tfN.getText()), Double.parseDouble(this.tfE.getText()))));
        listAktHalda();
    }
    @FXML
    void btnZrus1OA(ActionEvent event) {
        this.abstrHeapI.zrus();
        listAktHalda();
    }
    private void listAktHalda(){
        this.listViewHalda.getItems().clear();
        this.iteratorHalda = abstrHeapI.vytvorIterator(this.cb1.getValue());
        if (!iteratorHalda.hasNext()) {
            return;
        }

        while (this.iteratorHalda.hasNext()){
            this.obListHalda.add(iteratorHalda.next());
        }

        this.iteratorHalda = abstrHeapI.vytvorIterator(this.cb1.getValue());
        while (this.iteratorHalda.hasNext()){
            System.out.println(this.iteratorHalda.next());
        }
        this.listViewHalda.setItems(this.obListHalda);

    }
    //Pamatky
    @FXML
    void btnExportOA(ActionEvent event) {
        try {
            pamatky.zapisDoSouboru();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnGenerujOA(ActionEvent event) {
        pamatky.generuj();
        listAkt();
    }

    @FXML
    void btnImportOA(ActionEvent event) {
        pamatky.importDatZTEXT();
        listAkt();

    }

    @FXML
    void btnIteratorOA(ActionEvent event) {
        this.iterator = pamatky.vytvorIterator(this.cb1.getValue());
        listAkt();
    }

    @FXML
    void btnNajdiOA(ActionEvent event) {
        this.listView.getSelectionModel().select(this.pamatky.najdiZamek(this.tfNajdi.getText()));
    }

    @FXML
    void btnOdeberOA(ActionEvent event) {
        this.pamatky.odeberZamek(this.tfOdeber.getText());
        listAkt();
    }

    @FXML
    void btnNajdiNejOA(ActionEvent event){
        this.listView.getSelectionModel().select(this.pamatky.najdiNejbliz(this.tfNajdi.getText()));
    }

    @FXML
    void btnPrebudujOA(ActionEvent event) {
        this.pamatky.prebuduj();
        this.listView.getItems().clear();
        this.obList.clear();
        Iterator<Zamek> itera = this.pamatky.vytvorIterator(this.cb1.getValue());
        if (!itera.hasNext()) {
            return;
        }

        while (itera.hasNext()){
            this.obList.add(itera.next());
        }
        this.listView.setItems(this.obList);

    }

    @FXML
    void btnVlozOA(ActionEvent event) {
        this.pamatky.vlozZamek(new Zamek(generator.generujIdZamku(), this.tfNazev.getText(), new Gps(Double.parseDouble(this.tfN.getText()), Double.parseDouble(this.tfE.getText()))));
        listAkt();
    }

    @FXML
    void btnZmenaKliceOA(ActionEvent event) {
        this.pamatky.nastavKlic(this.cb.getValue());
    }

    @FXML
    void btnZrusOA(ActionEvent event) {
        pamatky.zrus();
        listAkt();
    }

    @FXML
    void cb1OA(ActionEvent event) {

    }

    @FXML
    void cbOA(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cb1.setItems(this.iteratorList);
        this.cb1.getSelectionModel().selectFirst();
        this.cb.setItems(this.poziceList);
        this.cb.getSelectionModel().selectFirst();

        this.pamatky.nastavKlic(this.cb.getValue());
        this.prohl = this.cb1.getValue();
    }

    private void listAkt(){
        this.listView.getItems().clear();
        this.iterator = pamatky.vytvorIterator(this.cb1.getValue());
        if (!iterator.hasNext()) {
            return;
        }

        while (this.iterator.hasNext()){
            this.obList.add(iterator.next());
        }

        Iterator<Zamek> itera = pamatky.vytvorIterator(this.cb1.getValue());
        while (itera.hasNext()){
            System.out.println(itera.next());
        }
        this.listView.setItems(this.obList);

    }

}
