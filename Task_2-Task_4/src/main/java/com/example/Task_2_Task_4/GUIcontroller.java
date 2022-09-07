package com.example.Task_2_Task_4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GUIcontroller implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private TableView<passenger_class> table;
    @FXML
    private TextField searchBox;
    @FXML
    private TableColumn<passenger_class,String> PassengerFname;
    @FXML
    private TableColumn<passenger_class,String> PassengerLname;
    @FXML
    private TableColumn<passenger_class,String> PassengerVehicleNo;
    @FXML
    private TableColumn<passenger_class, String> PassengerFuelAmnt;
    @FXML
    private TableColumn<passenger_class,String>PassengerQueue;

    ObservableList<passenger_class> list= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Path file=Path.of("FuelQuesInfo.txt");

        try {
            Scanner sc=new Scanner(file);
            int lineNo=1;
            String[] queLenghts=Files.readAllLines(file).get(0).split(",");
            int noOfLinesRead;
            for (int i = 0; i <queLenghts.length ; i++) {
                noOfLinesRead=0;
                for (int j = lineNo; j < Integer.parseInt(queLenghts[i])+lineNo; j++) {
                    String[] loadedPassengerData=Files.readAllLines(file).get(j).split(",");
                    list.add(new passenger_class(loadedPassengerData[0],loadedPassengerData[1],loadedPassengerData[2],loadedPassengerData[3],String.valueOf(i+1)));
                    noOfLinesRead++;
                }
                lineNo+=noOfLinesRead;
            }
            int totalLenght=0;
            for (int i = 0; i <queLenghts.length ; i++) {
                totalLenght=totalLenght+Integer.parseInt(queLenghts[i]);
            }
            int waitingLineNo = totalLenght + 3;
            String[] waitingQueLenghts=Files.readAllLines(file).get(totalLenght+2).split(",");
            noOfLinesRead = waitingLineNo;
            for (int i = 0; i < Integer.parseInt(waitingQueLenghts[0]); i++) {
                String[] loadedWaitingPassengerData = Files.readAllLines(file).get(noOfLinesRead).split(",");
                list.add(new passenger_class(loadedWaitingPassengerData[0],loadedWaitingPassengerData[1],loadedWaitingPassengerData[2],loadedWaitingPassengerData[3],"Waiting" ));
                noOfLinesRead++;
            }
        } catch (Exception e) {
            System.out.println("!!!ERROR ERROR!!!");
        }


        PassengerFname.setCellValueFactory(new PropertyValueFactory<passenger_class, String>("PassengerFname"));
        PassengerLname.setCellValueFactory(new PropertyValueFactory<passenger_class, String>("PassengerLname"));
        PassengerVehicleNo.setCellValueFactory(new PropertyValueFactory<passenger_class, String>("PassengerVehicleNo"));
        PassengerFuelAmnt.setCellValueFactory(new PropertyValueFactory<passenger_class, String>("PassengerFuelAmnt"));
        PassengerQueue.setCellValueFactory(new PropertyValueFactory<passenger_class, String>("PassengerQueue"));

        table.setItems(list);

        FilteredList<passenger_class> filteredData = new FilteredList<>(list, b -> true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(passenger_class -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (passenger_class.getPassengerFname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (passenger_class.getPassengerLname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (passenger_class.getPassengerVehicleNo().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (passenger_class.getPassengerFuelAmnt().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (passenger_class.getPassengerQueue().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;

            });


        });
        SortedList<passenger_class> sortedData=new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }
}