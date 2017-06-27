package assignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/** 
 * dashboard Class reads and turns data into information and displays it in an educational way
 * @author Calvin Williams
 */
public class dashboard extends Application {/**/
	/*-------------------------------------------------------------------------------------------------------------------------
	 *---------------------------------------------------------STAGES----------------------------------------------------------
	 *------------------------------------------------------------------------------------------------------------------------- */

	
	/**
	 * Method that starts the GUI of the dashboard
	 * @param main The main stage
	 */
	@Override
	public void start(Stage main) throws Exception {
		readCSV(); /* Calls method to read csv and set multiple variables and objects*/
		calc();/* Performs multiple calculations on the csv values retrieved*/
		/* Setting DecimalFormats*/
		df.setMaximumFractionDigits(2);
		year.setMaximumFractionDigits(0);
		/** The root layout */
		GridPane root = new GridPane();
		/* Setting styling for root layout*/
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setStyle("-fx-background-color: #FFFFFF;");
		
		Label avgAgeL = new Label();/* Making label and styling label for Average Age*/
		avgAgeL.setText("Average Age");
		avgAgeL.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 65");
		Text ageVal = new Text(); /* Making Text and styling text for Average Age Value*/
		ageVal.setText(df.format(avgAge));
		ageVal.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 65");
		Text sdAge = new Text(); /* Making Text and styling text for the Standard Deviation of the Average Age Values*/
		sdAge.setText("Standard Deviation:  " + df.format(standDevAge));
		sdAge.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 40");
		Text note1 = new Text("Total Average Building Age"); /*Making and styling a note for more information on Average Age */
		note1.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");

		Label avgValL = new Label();/* Making label and styling label for Average Value*/
		avgValL.setText("Average Value");
		avgValL.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 65");
		Text avgValue = new Text();  /* Making Text and styling text for Average Value value*/
		avgValue.setText(currency.format(avgVal));
		avgValue.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 65");
		Text sdVal = new Text();/* Making Text and styling text for the Standard Deviation of the Average Value values*/
		sdVal.setText("Standard Deviation:  " + currency.format(standDevVal));
		sdVal.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 40");
		Text note2 = new Text("Total Average Property Value"); /*Making and styling a note for more information on Average Value */
		note2.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");

		Label valChange = new Label();/* Making label and styling label for Total Changed*/
		valChange.setText("Value change" + "*");
		valChange.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 65");
		Text changedVal = new Text();/* Making Text and styling text for Total Changed Value*/
		changedVal.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 65");
		changedVal.setText(currency.format(valChangedTotal));
		Text sdChange = new Text();/* Making Text and styling text for the Standard Deviation of the Total Changed Values*/
		sdChange.setText("Standard Deviation:  " + currency.format(standDevChange));
		sdChange.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 40");
		Text note = new Text("Total Difference Increase or Decrease of Property Value \n*Not adjusted for inflation");/*Making and styling a note for more information on Average Value */
		note.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");

		ageVbox.getChildren().addAll(avgAgeL, ageVal, sdAge, note1); /*Setting and Styling Age VBox */
		ageVbox.setAlignment(Pos.CENTER);
		ageVbox.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");

		valVbox.getChildren().addAll(avgValL, avgValue, sdVal, note2); /*Setting and Styling Value VBox */
		valVbox.setAlignment(Pos.CENTER);
		valVbox.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");

		valChangedVbox.getChildren().addAll(valChange, changedVal, sdChange, note); /*Setting and Styling Total Change VBox */
		valChangedVbox.setAlignment(Pos.CENTER);
		valChangedVbox.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");

		dataHbox.getChildren().addAll(ageVbox, valVbox, valChangedVbox); /*Adding all VBox's to HBox */

		pie = housePieChart(); /* Creating and Styling pie chart*/
		pie.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		pie.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		bar = costBarChart();/* Creating and Styling bar chart*/
		bar.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		bar.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		bar.setPrefHeight(800);

		MyClickHandler hand = new MyClickHandler();/* Instantiating Click handler*/

		ageVbox.setOnMouseClicked(hand);/* Setting nodes to the click handler*/
		valVbox.setOnMouseClicked(hand);
		valChangedVbox.setOnMouseClicked(hand);
		pie.setOnMouseClicked(hand);
		bar.setOnMouseClicked(hand);

		VBox buttons = new VBox(10);/* Information VBox */
		Button infoButton = new Button("Information");/* Information button with lambda handler to go to pickInfoStage*/
		infoButton.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");
		Text advert = new Text();
		advert.setText("Place Ad Here");
		advert.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16;");
		infoButton.setOnMouseClicked(e -> {
			pickInfoStage();
		});
		buttons.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		buttons.setAlignment(Pos.CENTER);
		buttons.setStyle("-fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		buttons.getChildren().addAll(infoButton,advert);

		/* Adding all sub-layouts to the root layout*/
		root.add(buttons, 1, 1);
		root.add(dataHbox, 0, 0);
		root.add(pie, 1, 0);
		root.add(bar, 0, 1);
		/* configuring scene and style sheets*/
		Scene scene = new Scene(root);
		scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Baloo+Da");
		/* setting stage and showing*/
		main.setScene(scene);
		main.setTitle("City of Vancouver Dashboard");
		main.show();
	}
	/** 
	 * Stage to pick what you would like to know more about after clicking the information button
	 */
	public void pickInfoStage() {/*selection of information user wish's to see(Unimportant to assignment:NOT DOCUMENTED)*/
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(10, 20, 10, 20));

		yearInfo = new Text("Year Average Info");
		yearInfo.setStyle(
				"-fx-font-family: Baloo+Da; -fx-font-size: 16; -fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		valInfo = new Text("Property Average Info");
		valInfo.setStyle(
				"-fx-font-family: Baloo+Da; -fx-font-size: 16; -fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		totalInfo = new Text("Total Value Changed Info");
		totalInfo.setStyle(
				"-fx-font-family: Baloo+Da; -fx-font-size: 16; -fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		pieInfo = new Text("PieChart Info");
		pieInfo.setStyle(
				"-fx-font-family: Baloo+Da; -fx-font-size: 16; -fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		barInfo = new Text("BarChart Info");
		barInfo.setStyle(
				"-fx-font-family: Baloo+Da; -fx-font-size: 16; -fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px;");
		Text info = new Text("Double click item for more info");
		info.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 16));

		vb.getChildren().addAll(yearInfo, valInfo, totalInfo, pieInfo, barInfo, info);

		MyInfoClickHandler hand = new MyInfoClickHandler();

		yearInfo.setOnMouseClicked(hand);
		valInfo.setOnMouseClicked(hand);
		totalInfo.setOnMouseClicked(hand);
		pieInfo.setOnMouseClicked(hand);
		barInfo.setOnMouseClicked(hand);

		Stage pickInfoStage = new Stage();
		Scene pickInfoScene = new Scene(vb);
		pickInfoStage.setTitle("Pick Information");
		pickInfoStage.setScene(pickInfoScene);
		pickInfoStage.show();
	}
	/** 
	 *Displays information based off the users pick inside the pickInfoStage() method 
	 * @param s Information
	 * @param s1 Information
	 * @param s2 Information
	 * @param s3 Information
	 * @param id Identifier to know which selection the user made
	 */
	public void infoStage(String s, String s1, String s2, String s3, int id) {/*information user wish's to see(Unimportant to assignment:NOT DOCUMENTED)*/
		VBox vb = new VBox(20);

		Text t = new Text(s);
		t.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");
		Text t1 = new Text(s1);
		t1.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");
		Text t2 = new Text(s2);
		t2.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");
		Text t3 = new Text(s3);
		t3.setStyle("-fx-font-family: Baloo+Da; -fx-font-size: 16");
		Text info = new Text("Created by Calvin Williams");
		info.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 16));

		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(10, 20, 10, 20));
		vb.getChildren().addAll(t, t1, t2, t3, info);
		Stage secondStage = new Stage();
		Scene secondScene = new Scene(vb);
		secondStage.setTitle("Information");
		secondStage.setScene(secondScene);
		secondStage.show();
	}

	/**
	 * Stage for when the user double clicks an element to show the calculations of the node they clicked applied to each street 
	 * @param id Identifier to tell which node the user clicked
	 */
	public void streetStage(int id) {
		HBox hb = new HBox();/* root layout and Stage and Scene created*/
		Stage stStage = new Stage();
		Scene streetScene = new Scene(hb);
		if (id == 0) {
			stStage.setTitle("Average Street Building Age");
			ObservableList<String> streetObservList = FXCollections.observableArrayList();
			for (String key : ageAvgMap.keySet()) {/*Gets all street names from hashmap puts the names into observable list */
				streetObservList.add(key);
			}
			ListView<String> streetList = new ListView<String>();/* List view of street names*/
			streetList.setItems(streetObservList);
			ObservableList<String> streetObservListData = FXCollections.observableArrayList();
			double average = 0;
			double count = 0;
			for (Map.Entry<String, ArrayList<Double>> entry : ageAvgMap.entrySet()) {/* Iterates through hashmap to get ages, adds them together then divides for average*/
				average = 0;
				count = 0;
				ArrayList<Double> value = entry.getValue();
				for (Double d : value) {
					average += d;
					++count;
				}
				streetObservListData.add(year.format(average / count));
			}
			ListView<String> streetListData = new ListView<String>();
			streetListData.setItems(streetObservListData);
			hb.getChildren().addAll(streetList, streetListData);
			hb.setHgrow(streetListData, Priority.ALWAYS);
			hb.applyCss();
			Node n1 = streetList.lookup(".scroll-bar");/* Binds scroll bars of ListViews each other*/
			if (n1 instanceof ScrollBar) {
				final ScrollBar bar = (ScrollBar) n1;
				Node n2 = streetListData.lookup(".scroll-bar");
				if (n2 instanceof ScrollBar) {
					final ScrollBar bar1 = (ScrollBar) n2;
					bar.valueProperty().bindBidirectional(bar1.valueProperty());
				}
			}
			streetList.setOnMouseClicked(e -> {/* Handler to open postal code stage based off street that is double clicked*/
				if (e.getClickCount() > 1) {
					String street = streetList.getSelectionModel().getSelectedItem();
					postalStage(id, street);
				}
			});
		} else if (id == 1) {
			stStage.setTitle("Average Street Property Value");
			ObservableList<String> streetObservList = FXCollections.observableArrayList();
			for (String key : valAvgMap.keySet()) {/*Gets all street names from hashmap puts the names into observable list */
				streetObservList.add(key);
			}
			ListView<String> streetList = new ListView<String>();
			streetList.setItems(streetObservList);

			ObservableList<String> streetObservListData = FXCollections.observableArrayList();
			double average = 0;
			double count = 0;
			for (Map.Entry<String, ArrayList<Double>> entry : valAvgMap.entrySet()) {/*Iterates through hashmap and retrieves all values and gets the average value of property's based off street*/
				average = 0;
				count = 0;
				ArrayList<Double> value = entry.getValue();
				for (Double d : value) {
					average += d;
					++count;
				}
				streetObservListData.add(currency.format(average / count));
			}
			ListView<String> streetListData = new ListView<String>();
			streetListData.setItems(streetObservListData);
			hb.getChildren().addAll(streetList, streetListData);
			hb.setHgrow(streetListData, Priority.ALWAYS);
			hb.applyCss();
			Node n1 = streetList.lookup(".scroll-bar");
			if (n1 instanceof ScrollBar) {/* Binds scroll bars of ListViews each other*/
				final ScrollBar bar = (ScrollBar) n1;
				Node n2 = streetListData.lookup(".scroll-bar");
				if (n2 instanceof ScrollBar) {
					final ScrollBar bar1 = (ScrollBar) n2;
					bar.valueProperty().bindBidirectional(bar1.valueProperty());
				}
			}
			streetList.setOnMouseClicked(e -> {/* Handler to open postal code stage based off street that is double clicked*/
				if (e.getClickCount() > 1) {
					String street = streetList.getSelectionModel().getSelectedItem();
					postalStage(id, street);
				}
			});
		} else if (id == 2) {
			stStage.setTitle("Total Street Price Change");
			ObservableList<String> streetObservList = FXCollections.observableArrayList();
			for (String key : totalMap.keySet()) {/*Gets all street names from hashmap puts the names into observable list */
				streetObservList.add(key);
			}
			ListView<String> streetList = new ListView<String>();
			streetList.setItems(streetObservList);
			streetList.styleProperty();

			ObservableList<String> streetObservListData = FXCollections.observableArrayList();
			double num = 0;
			for (Map.Entry<String, ArrayList<Double>> entry : totalMap.entrySet()) {/*Iterates through hashmap and gets total change value based on street.*/
				num = 0;
				ArrayList<Double> value = entry.getValue();
				for (Double d : value) {
					num += d;
				}
				streetObservListData.add(currency.format(num));
			}
			ListView<String> streetListData = new ListView<String>();
			streetListData.setItems(streetObservListData);
			hb.getChildren().addAll(streetList, streetListData);
			hb.setHgrow(streetListData, Priority.ALWAYS);
			hb.applyCss();
			Node n1 = streetList.lookup(".scroll-bar");
			if (n1 instanceof ScrollBar) {/* Binds scroll bars of ListViews each other*/
				final ScrollBar bar = (ScrollBar) n1;
				Node n2 = streetListData.lookup(".scroll-bar");
				if (n2 instanceof ScrollBar) {
					final ScrollBar bar1 = (ScrollBar) n2;
					bar.valueProperty().bindBidirectional(bar1.valueProperty());
				}
			}
			streetList.setOnMouseClicked(e -> {/* Handler to open postal code stage based off street that is double clicked*/
				if (e.getClickCount() > 1) {
					String street = streetList.getSelectionModel().getSelectedItem();
					postalStage(id, street);
				}
			});
		} else if (id == 3) {

			streetPie = housePieChart();
			stStage.setTitle("Pie's");
			ObservableList<String> streetObservList = FXCollections.observableArrayList();
			for (String key : houseMap.keySet()) {/*Gets all street names from hashmap puts the names into observable list */
				streetObservList.add(key);
			}
			ListView<String> streetList = new ListView<String>();
			streetList.setItems(streetObservList);
			hb.getChildren().addAll(streetList, streetPie);
			streetList.setOnMouseClicked(e -> { /* Click handler for selecting which chart the user wants to see*/
				String street = streetList.getSelectionModel().getSelectedItem();
				houseType(houseMap.get(street));
				if (streetPie != null) {
					hb.getChildren().removeAll(streetPie);
				}
				streetPie = housePieChart();
				hb.getChildren().add(streetPie);

				if (e.getClickCount() > 1) {/* Handler to open postal code stage based off street that is double clicked*/
					postalStage(id, street);
				}
			});
		} else if (id == 4) {

			streetBar = costBarChart();
			stStage.setTitle("Bar's");
			ObservableList<String> streetObservList = FXCollections.observableArrayList();
			for (String key : houseMap.keySet()) {/*Gets all street names from hashmap puts the names into observable list */
				streetObservList.add(key);
			}
			ListView<String> streetList = new ListView<String>();
			streetList.setItems(streetObservList);
			hb.getChildren().addAll(streetList, streetBar);
			streetList.setOnMouseClicked(e -> {/* Click handler for selecting which chart the user wants to see*/
				String street = streetList.getSelectionModel().getSelectedItem();
				houseCostInterval(valAvgMap.get(street));
				if (streetBar != null) {
					hb.getChildren().removeAll(streetBar);
				}
				streetBar = costBarChart();
				hb.getChildren().add(streetBar);

				if (e.getClickCount() > 1) {/* Handler to open postal code stage based off street that is double clicked*/
					postalStage(id, street);
				}
			});
		}
		stStage.setScene(streetScene);
		stStage.show();
	}
	/**
	 * Stage to show the user calculations applied to postal codes on each street based on what original node they clicked in the main stage
	 * @param id Identifier
	 * @param street The street the user double clicked in the ListView
	 */
	public void postalStage(int id, String street) {
		HBox hb = new HBox();
		Stage stStage = new Stage();
		Scene streetScene = new Scene(hb);
		if (id == 0) {
			ObservableList<String> pCodes = FXCollections.observableArrayList();
			ListView<String> postalCodes = new ListView<String>();
			if (streetPostalMap.containsKey(street)) {/*Gets all Postal codes from hashmap puts the names into observable list */
				ArrayList<String> value = streetPostalMap.get(street);
				for (String d : value) {
					pCodes.add(d);
				}
				postalCodes.setItems(pCodes);
			}
			ObservableList<String> oValues = FXCollections.observableArrayList();
			ListView<String> values = new ListView<String>();
			double average = 0;
			String postal;
			for (Iterator<String> e = pCodes.iterator(); e.hasNext();) {/*Iterates through property ages and returns average based on postal code */
				average = 0;
				postal = e.next();
				ArrayList<Double> list = postalAgeMap.get(postal);
				for (Double d : list) {
					average += d;
				}
				oValues.add(year.format(average / list.size()));
			}
			values.setItems(oValues);
			hb.getChildren().addAll(postalCodes, values);
			hb.applyCss();
			Node n1 = postalCodes.lookup(".scroll-bar");/* Binds scroll bars of ListViews each other*/
			if (n1 instanceof ScrollBar) {
				final ScrollBar bar = (ScrollBar) n1;
				Node n2 = values.lookup(".scroll-bar");
				if (n2 instanceof ScrollBar) {
					final ScrollBar bar1 = (ScrollBar) n2;
					bar.valueProperty().bindBidirectional(bar1.valueProperty());
				}
			}
		} else if (id == 1) {
			ObservableList<String> pCodes = FXCollections.observableArrayList();
			ListView<String> postalCodes = new ListView<String>();
			if (streetPostalMap.containsKey(street)) {/*Gets all Postal codes from hashmap puts the names into observable list */
				ArrayList<String> value = streetPostalMap.get(street);
				for (String d : value) {
					pCodes.add(d);
				}
				postalCodes.setItems(pCodes);
			}
			ObservableList<String> oValues = FXCollections.observableArrayList();
			ListView<String> values = new ListView<String>();
			double average = 0;
			String postal;
			for (Iterator<String> e = pCodes.iterator(); e.hasNext();) {/*Iterates through property values and returns average based on postal code */
				postal = e.next();
				;
				ArrayList<Double> list = postalValMap.get(postal);
				for (Double d : list) {
					average += d;
				}
				oValues.add(currency.format(average / list.size()));
			}
			values.setItems(oValues);
			hb.getChildren().addAll(postalCodes, values);
			hb.applyCss();
			Node n1 = postalCodes.lookup(".scroll-bar");/* Binds scroll bars of ListViews each other*/
			if (n1 instanceof ScrollBar) {
				final ScrollBar bar = (ScrollBar) n1;
				Node n2 = values.lookup(".scroll-bar");
				if (n2 instanceof ScrollBar) {
					final ScrollBar bar1 = (ScrollBar) n2;
					bar.valueProperty().bindBidirectional(bar1.valueProperty());
				}
			}
		} else if (id == 2) {
			ObservableList<String> pCodes = FXCollections.observableArrayList();
			ListView<String> postalCodes = new ListView<String>();
			if (streetPostalMap.containsKey(street)) {/*Gets all Postal codes from hashmap puts the names into observable list */
				ArrayList<String> value = streetPostalMap.get(street);
				for (String d : value) {
					pCodes.add(d);
				}
				postalCodes.setItems(pCodes);
			}
			ObservableList<String> oValues = FXCollections.observableArrayList();
			ListView<String> values = new ListView<String>();
			double num = 0;
			String postal;
			for (Iterator<String> e = pCodes.iterator(); e.hasNext();) {/*Iterates through property value change and returns total based on postal code */
				postal = e.next();
				
				ArrayList<Double> list = postalChangeMap.get(postal);
				for (Double d : list) {
					num += d;
				}
				oValues.add(currency.format(num));
			}
			values.setItems(oValues);
			hb.getChildren().addAll(postalCodes, values);
			hb.applyCss();
			Node n1 = postalCodes.lookup(".scroll-bar");/* Binds scroll bars of ListViews each other*/
			if (n1 instanceof ScrollBar) {
				final ScrollBar bar = (ScrollBar) n1;
				Node n2 = values.lookup(".scroll-bar");
				if (n2 instanceof ScrollBar) {
					final ScrollBar bar1 = (ScrollBar) n2;
					bar.valueProperty().bindBidirectional(bar1.valueProperty());
				}
			}
		} else if (id == 3) {
			codePie = housePieChart();
			stStage.setTitle("Pie's");
			ListView<String> pcodeList = new ListView<String>();
			ObservableList<String> pcodeObservList = FXCollections.observableArrayList();
			if (streetPostalMap.containsKey(street)) {/*Gets all Postal codes from hashmap puts the names into observable list */
				ArrayList<String> value = streetPostalMap.get(street);
				for (String d : value) {
					pcodeObservList.add(d);
				}
				pcodeList.setItems(pcodeObservList);
			}
			pcodeList.setItems(pcodeObservList);
			hb.getChildren().addAll(pcodeList, codePie);
			pcodeList.setOnMouseClicked(e -> {/*Click handler to create pie chart based on postal code selected*/
				String pcode = pcodeList.getSelectionModel().getSelectedItem();
				houseType(housePostalMap.get(pcode));
				if (codePie != null) {
					hb.getChildren().removeAll(codePie);
				}
				codePie = housePieChart();
				hb.getChildren().add(codePie);
			});
		} else if (id == 4) {
			codeBar = costBarChart();
			stStage.setTitle("Bar's");
			ListView<String> pcodeList = new ListView<String>();
			ObservableList<String> pcodeObservList = FXCollections.observableArrayList();
			if (streetPostalMap.containsKey(street)) {/*Gets all Postal codes from hashmap puts the names into observable list */
				ArrayList<String> value = streetPostalMap.get(street);
				for (String d : value) {
					pcodeObservList.add(d);
				}
				pcodeList.setItems(pcodeObservList);
			}
			pcodeList.setItems(pcodeObservList);
			hb.getChildren().addAll(pcodeList, codeBar);
			pcodeList.setOnMouseClicked(e -> {/*Click handler to create bar chart based on postal code selected*/
				String pcode = pcodeList.getSelectionModel().getSelectedItem();
				houseCostInterval(postalValMap.get(pcode));
				if (codeBar != null) {
					hb.getChildren().removeAll(codeBar);
				}
				codeBar = costBarChart();
				hb.getChildren().add(codeBar);
			});
		}
		stStage.setTitle("Postal Code Values");
		stStage.setScene(streetScene);
		stStage.show();
	}
	/*-------------------------------------------------------------------------------------------------------------------------
	 *-------------------------------------------------------MAIN--------------------------------------------------------------
	 *------------------------------------------------------------------------------------------------------------------------- */
	/**
	 *java main required. Used to launch Javafx Start method
	 *@param args its a pirate
	 */
	public static void main(String[] args) {
		launch(args);
	}
	/*-------------------------------------------------------------------------------------------------------------------------
	 *-------------------------------------------------------CLICKHANDLERS------------------------------------------------------
	 *------------------------------------------------------------------------------------------------------------------------- */
	/**
	 * Click handler to handle mouse clicks inside the select information stage 
	 * 
	 * 
	 */
	private class MyInfoClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			if (e.getClickCount() > 1) {
				if (e.getSource() == yearInfo) {
					String s = "Average from " + list.size() + " elements";
					String s1 = "Column Name(s): YEAR_BUILT";
					String s2 = "Column(s): Y";
					String s3 = "Data from City of Vancouver Open Data Catalogue \nProperty tax report(2015) property_tax_report.csv";
					int id = 0;
					infoStage(s, s1, s2, s3, id);
				} else if (e.getSource() == valInfo) {
					String s = "Average from " + list.size() + " elements";
					String s1 = "Column Name(s): CURRENT_LAND_VALUE";
					String s2 = "Column(s): T";
					String s3 = "Data from City of Vancouver Open Data Catalogue \nProperty tax report(2015) property_tax_report.csv";
					int id = 1;
					infoStage(s, s1, s2, s3, id);
				} else if (e.getSource() == totalInfo) {
					String s = "Total from " + list.size() + " elements";
					String s1 = "Column Name(s): CURRENT_LAND_VALUE, PREVIOUS_LAND_VALUE";
					String s2 = "Column(s): T, W";
					String s3 = "Data from City of Vancouver Open Data Catalogue \nProperty tax report(2015) property_tax_report.csv";
					int id = 2;
					infoStage(s, s1, s2, s3, id);
				} else if (e.getSource() == pieInfo) {
					String s = "Data from" + (houseCommerce + houseOneFam + houseTwoFam + houseMultFam + houseCompreh
							+ houseHistorical + houseIndustrial + houseLightIndustrial + other) + " elements";
					String s1 = "Column Name(s): ZONE_CATEGORY";
					String s2 = "Column(s): F";
					String s3 = "Data from City of Vancouver Open Data Catalogue \nProperty tax report(2015) property_tax_report.csv";
					int id = 3;
					infoStage(s, s1, s2, s3, id);
				} else if (e.getSource() == barInfo) {
					String s = "Data from Propertys under 2.5 million and over 0";
					String s1 = "Column Name(s): CURRENT_LAND_VALUE";
					String s2 = "Column(s): T";
					String s3 = "Data from City of Vancouver Open Data Catalogue \nProperty tax report(2015) property_tax_report.csv";
					int id = 4;
					infoStage(s, s1, s2, s3, id);
				}
			}
		}
	}

	
	/**
	 *Click handler to handle when the user double clicks a node on the main stage. Calls the street Stage with an id based on which node the user selected
	 * 
	 */
	private class MyClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			if (e.getClickCount() > 1) {
				if (e.getSource() == ageVbox) {
					int id = 0;
					streetStage(id);
				} else if (e.getSource() == valVbox) {
					int id = 1;
					streetStage(id);
				} else if (e.getSource() == valChangedVbox) {
					int id = 2;
					streetStage(id);
				} else if (e.getSource() == pie) {
					int id = 3;
					streetStage(id);
				} else if (e.getSource() == bar) {
					int id = 4;
					streetStage(id);
				}
			}
		}

	}

	/*-------------------------------------------------------------------------------------------------------------------------
	 *----------------------------------------------------OTHER METHODS--------------------------------------------------------
	 *------------------------------------------------------------------------------------------------------------------------- */
	/** Executes original calculations using helper methods as well as the retrieved data from the csv file*/
	public void calc() {
		avgAge = averageAge(list).doubleValue();/*Calculates total age*/
		avgVal = averageLandValue(list).doubleValue();/*Calculates total land value*/
		ArrayList<Double> tempArrayList = new ArrayList<Double>();/*Temp Double array*/
		for (int i = 0; i < list
				.size(); i++) {/* Adds the elements to the temp array list */
			Row temp = list.get(i);
			tempArrayList.add(temp.getCurrentLandValue());
		}
		standDevVal = standardDeviation(tempArrayList,
				avgVal);/* Standard Deviation of House value */
		houseCostInterval(tempArrayList); /* Sorts houses into ranges */
		tempArrayList.clear();
		for (int i = 0; i < list
				.size(); i++) {/* Adds the elements to the temp array list */
			Row temp = list.get(i);
			tempArrayList.add(temp.getYearBuilt());
		}
		standDevAge = standardDeviation(tempArrayList,
				avgAge);/* Standard Deviation of House age/build time */
		tempArrayList.clear();
		ArrayList<Double> tempArrayList2 = new ArrayList<Double>();/*Secondary temp array list of doubles*/
		for (int i = 0; i < list.size(); i++) {/*Adds values to both array lists*/
			Row temp = list.get(i);
			tempArrayList.add(temp.getCurrentLandValue());
			tempArrayList2.add(temp.getPreviousLandValue());
		}
		valChangedTotal = houseValueChange(tempArrayList, tempArrayList2);/*Calculates total change value*/
		if (!standChange.isEmpty()) {/*Calculates standard deviation of total change value*/
			standDevChange = standardDeviation(standChange, (valChangedTotal / tempArrayList.size()));
		}
		tempArrayList.clear();
		tempArrayList2.clear();
		ArrayList<String> tempString = new ArrayList<String>();/*Temporary array list of strings*/
		for (int i = 0; i < list.size(); i++) {/*puts house type values in temp array*/
			Row temp = list.get(i);
			tempString.add(temp.getHouseType());
		}
		houseType(tempString);/*counts house types*/
		tempString.clear();
	}

	/**
	 *Helper Method to calculate the average age of buildings
	 *@return average average age of the buildings as a BigInteger 
	 *@param a ArrayList of Rows
	 */
	public BigInteger averageAge(
			ArrayList<Row> a) {/* Calculates the Average Age of houses */
		BigInteger average = new BigInteger("0");
		for (int i = 0; i < a.size(); i++) {/*Goes through each element and adds them to average*/
			Row tempRow = a.get(i);
			long temp = (long) tempRow.getYearBuilt();
			average = average.add(BigInteger.valueOf(temp));
		}
		BigInteger divide = new BigInteger(a.size() + "");
		average = average.divide(
				divide);/*Divides all the added values by the number of values*/
		return average;
	}

	/**
	 *Helper Method to calculate the average property value of the propertys 
	 * @return average the average property value
	 * @param a ArrayList of Rows
	 */
	public BigInteger averageLandValue(
			ArrayList<Row> a) {/* Calculates the Average value of houses */
		BigInteger average = new BigInteger("0");
		for (int i = 0; i < a.size(); i++) {/*Goes through each element and adds them to average*/
			Row tempRow = a.get(i);
			long temp = (long) tempRow.getCurrentLandValue();
			average = average.add(BigInteger.valueOf(temp));
		}
		BigInteger divide = new BigInteger(a.size() + "");
		average = average.divide(
				divide);/* Divides the added values by the number of values */
		return average;
	}

	/**
	 *Helper Method to calculate the total difference of two ArrayList's of doubles
	 * @param current The current value of the property's.
	 * @param old The old values of the property's.
	 * @return total The total value change between old and new values
	 */
	public double houseValueChange(ArrayList<Double> current,
			ArrayList<Double> old) {/*Calculates total amount house value has changed*/
		double total = 0.0;
		for (int i = 0; i < current.size(); i++) {
			total += current.get(i) - old.get(i);
			standChange.add(current.get(i) - old.get(i));
		}
		return total;
	}
	/**
	 *Helper Method to calculate the amounts of each type of property 
	 * @param al ArrayList of house type strings
	 */
	public void houseType(ArrayList<String> al) {
		houseCommerce = 0;
		houseOneFam = 0;
		houseTwoFam = 0;
		houseMultFam = 0;
		houseCompreh = 0;
		houseHistorical = 0;
		houseIndustrial = 0;
		houseLightIndustrial = 0;
		other = 0;
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).equals("One Family Dwelling")) {
				houseOneFam++;
			} else if (al.get(i).equals("Two Family Dwelling")) {
				houseTwoFam++;
			} else if (al.get(i).equals("Multiple Family Dwelling")) {
				houseMultFam++;
			} else if (al.get(i).equals("Industrial")) {
				houseIndustrial++;
			} else if (al.get(i).equals("Comprehensive Development")) {
				houseCompreh++;
			} else if (al.get(i).equals("Light Industrial")) {
				houseLightIndustrial++;
			} else if (al.get(i).equals("Historic Area")) {
				houseHistorical++;
			} else if (al.get(i).equals("Commercial")) {
				houseCommerce++;
			} else {
				other++;
			}
		}
	}
	/** 
	 * Method that makes a pie chart based off the house type values 
	 * @return chart pie chart of the house type values
	 */
	public PieChart housePieChart() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(/*Pie chart created based off previously calculated data*/
				new PieChart.Data("One Family Dwelling", houseOneFam),
				new PieChart.Data("Two Family Dwelling", houseTwoFam),
				new PieChart.Data("Multiple Family Dwelling", houseMultFam),
				new PieChart.Data("Industrial", houseIndustrial),
				new PieChart.Data("Comprehensive Development", houseCompreh),
				new PieChart.Data("Light Industrial", houseLightIndustrial),
				new PieChart.Data("Historic Area", houseHistorical), new PieChart.Data("Commercial", houseCommerce),
				new PieChart.Data("Other", other));
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Zone Category");
		return chart;
	}
	/**
	 * Method to calculate how many houses are within dynamic ranges of 25000. Capped at 2.5 million(Hard coded) due to small amounts of property in each 25k range at that point 
	 * @param al ArrayList of house prices
	 */
	public void houseCostInterval(ArrayList<Double> al) {
		Collections.sort(al);
		Integer keyCount = 1;
		Integer count = 0;
		for (Iterator<Double> e = al.iterator(); e.hasNext();) {/*Iterates through sorted list*/
			double value = e.next();
			boolean found = false;
			while (!found) {
				if (value <= keyCount * 25000 && value > (keyCount - 1) * 25000) {/*if value is within 25000 range*/
					++count;
					if (valueSortMap.containsKey(keyCount)) {/*If the HashMap key exists*/
						valueSortMap.replace(keyCount, count);/*replace it*/
					} else {
						valueSortMap.put(keyCount, count);/*Else create it*/
					}
					found = true;/*If it is found go to next iteration*/
				} else {/*If value is over 2.5 million break*/
					if (value > 2500000) {
						break;
					}
					count = 0;/*If not found restart count add, go to next $25000 range, and make new HashMap KV*/
					++keyCount;
					valueSortMap.put(keyCount, count);
				}
			}
		}
	}
	/** 
	 *Method to create a bar chart based off the 25000 dollar ranges
	 *@return bc Bar chart of charted ranges 
	 */
	public BarChart<String, Number> costBarChart() {
		final CategoryAxis x = new CategoryAxis();
		final NumberAxis y = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String, Number>(x, y);
		bc.setTitle("Value Partitions(Up to 2.5 million CAD)");
		x.setLabel("Price(Canadian Dollars)");
		y.setLabel("Amount of Propertys");
		XYChart.Series series = new XYChart.Series<>();
		for (int i = 1; i <= valueSortMap.size(); i++) {
			series.getData().add(new XYChart.Data<>(largeCurrency.format(i * 25000) + "", valueSortMap.get(i)));
		}
		bc.getData().addAll(series);
		bc.setLegendVisible(false);
		return bc;
	}
	/**
	 * Method that reads a csv file using a regex that ignores commas inside quotes. It then makes Row objects based of the values read as long as none of the values are empty.
	 * This method also makes an assortment of HashMaps that are used to calculate the data.
	 */
	public void readCSV() {/* Reads the CSV file */
		try {
			String file = "property_tax_report.csv";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			reader.readLine();
			while ((line = reader
					.readLine()) != null) {/* While the line isnt empty */
				tempList = Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",
						-1));/*
								 * splits line using a Regex that ignores commas
								 * in quotes
								 */

				if (!tempList.get(5).isEmpty() && !tempList.get(12).isEmpty() && !tempList.get(13).isEmpty()
						&& !tempList.get(19).isEmpty() && Integer.parseInt(tempList.get(19)) != 0
						&& !tempList.get(22).isEmpty() && !tempList.get(24)
								.isEmpty()) {/*
												 * If the values I need are not
												 * empty
												 */
					if ((Integer.parseInt(tempList.get(
							19)) < 205451000)) {/*
												 * Removes top valued property
												 */

						houseType = tempList.get(5);/*Sets all values from csv*/
						street = tempList.get(12);
						postalCode = tempList.get(13);
						ageForMap = Double.parseDouble(tempList.get(24));
						valForMap = Double.parseDouble(tempList.get(19));
						prevForMap = Double.parseDouble(tempList.get(22));

						list.add(new Row(houseType, street, postalCode, valForMap, prevForMap, ageForMap));

						ArrayList<Double> ageValues = ageAvgMap.get(street);/*Setting hash map for Property Age based on Street*/
						if (ageValues == null) {
							ageValues = new ArrayList<Double>();
							ageAvgMap.put(street, ageValues);
						}
						ageValues.add(ageForMap);

						ArrayList<Double> priceValues = valAvgMap.get(street);/*Setting hash map for Property Value based on Street*/
						if (priceValues == null) {
							priceValues = new ArrayList<Double>();
							valAvgMap.put(street, priceValues);
						}
						priceValues.add(valForMap);

						ArrayList<Double> totalValues = totalMap.get(street);/*Setting hash map for Property Total Changed based on Street*/
						if (totalValues == null) {
							totalValues = new ArrayList<Double>();
							totalMap.put(street, totalValues);
						}
						totalValues.add((valForMap - prevForMap));

						ArrayList<String> houseValues = houseMap.get(street);/*Setting hash map for House Type based on Street*/
						if (houseValues == null) {
							houseValues = new ArrayList<String>();
							houseMap.put(street, houseValues);
						}
						houseValues.add(houseType);

						ArrayList<String> postalValues = streetPostalMap.get(street);/*Setting hash map for Postal Code based on Street*/
						if (postalValues == null) {
							postalValues = new ArrayList<String>();
							streetPostalMap.put(street, postalValues);
						}
						if (!streetPostalMap.get(street).contains(postalCode)) {
							postalValues.add(postalCode);
						}

						ArrayList<Double> postalVal = postalValMap.get(postalCode);/*Setting hash map for Property Value based on Postal Code*/
						if (postalVal == null) {
							postalVal = new ArrayList<Double>();
							postalValMap.put(postalCode, postalVal);
						}
						postalVal.add(valForMap);

						ArrayList<Double> postalAge = postalAgeMap.get(postalCode);/*Setting hash map for Property Age based on Postal code*/
						if (postalAge == null) {
							postalAge = new ArrayList<Double>();
							postalAgeMap.put(postalCode, postalAge);
						}
						postalAge.add(ageForMap);

						ArrayList<Double> totalPostalValues = postalChangeMap.get(postalCode);/*Setting hash map for Total Value Changed based on Postal Code*/
						if (totalPostalValues == null) {
							totalPostalValues = new ArrayList<Double>();
							postalChangeMap.put(postalCode, totalPostalValues);
						}
						totalPostalValues.add((valForMap - prevForMap));

						ArrayList<String> housePostalValues = housePostalMap.get(postalCode);/*Setting hash map for House Type based on Postal Code*/
						if (housePostalValues == null) {
							housePostalValues = new ArrayList<String>();
							housePostalMap.put(postalCode, housePostalValues);
						}
						housePostalValues.add(houseType);
					}
				}
			}
			System.out.println("Done");
			reader.close();
		} catch (Exception e) {
			e.getMessage();
			e.getCause();
		}
	}

	/** Calculates the standard deviation of the List passed
	 * @param list List of doubles to get the SD of 
	 * @return standard deviation of the passed list
	 * @param list List of doubles to get the SD of
	 * @param mean average of number set
	 */
	public double standardDeviation(List<Double> list,
			double mean) { /*Calculates Standard Deviation usingdifferenceOfSquares*/
		return Math.sqrt(differenceOfSquares(list, mean) / list.size());
	}

	/**
	 * Helper method used to calculate the standard deviation 
	 * @return sum 
	 * @param list ArrayList of Doubles
	 * @param mean Average of ArrayList
	 */
	public double differenceOfSquares(List<Double> list,
			double mean) {/* Used to calculate standard deviation */
		sum = 0.0;
		list.forEach(item -> sum += (item - mean) * (item - mean));
		return sum;
	}

	/*-------------------------------------------------------------------------------------------------------------------------
	 *--------------------------------------------------------VARIABLES--------------------------------------------------------
	 *------------------------------------------------------------------------------------------------------------------------- */

	/* Text Boxes that hold information for info stages*/
	Text yearInfo;
	Text valInfo;
	Text totalInfo;
	Text pieInfo;
	Text barInfo;

	/* Doubles to hold counts of each house type */
	/** Amount of Commercial property types*/
	int houseCommerce;
	/** Amount of One Family property types*/
	int houseOneFam;
	/** Amount of Two Family property types*/
	int houseTwoFam;
	/** Amount of Multiple Family property types*/
	int houseMultFam;
	/** Amount of Comprehensive Development property types*/
	int houseCompreh;
	/** Amount of Historical property types*/
	int houseHistorical;
	/** Amount of Industrial property types*/
	int houseIndustrial;
	/** Amount of Light Industrial property types*/
	int houseLightIndustrial;
	/** Amount of other property types*/
	int other;

	/*Layouts*/
	/** VBox used in the main stage for the average age of property's.*/
	VBox ageVbox = new VBox();
	/** VBox used in the main stage for the average value of property's.*/
	VBox valVbox = new VBox();
	/** VBox used in the main stage for the total value changed of property's.*/
	VBox valChangedVbox = new VBox();
	/** HBox that hold above VBoxs*/
	HBox dataHbox = new HBox(10);
	
	/*Charts*/
	/** Main pie chart used on the main stage*/
	PieChart pie;
	/** Secondary pie chart used on the second stage based on street data*/
	PieChart streetPie;
	/** Tertiary pie chart used on the third stage based on postal code data*/
	PieChart codePie;
	/** Main bar chart used on the main stage*/
	BarChart<String, Number> bar;
	/** Secondary bar chart used on the second stage based on street data*/
	BarChart<String, Number> streetBar;
	/** Tertiary bar chart used on the third stage based on postal code data*/
	BarChart<String, Number> codeBar;

	/*Values for reading csv*/
	/** House type read from .csv*/
	String houseType = "";
	/** Street read from csv*/
	String street = "";
	/** Postal Code read from csv*/
	String postalCode = "";
	/** Previous land value read from csv*/
	double prevForMap = 0.0;
	/**Property Age read from csv*/
	double ageForMap = 0.0;
	/**Current Value read from csv*/
	double valForMap = 0.0;
	
	/*HashMaps for sorting by street and postal code*/
	/** HashMap used for the sorted counts of houses in 25000 dollar ranges.*/
	HashMap<Integer, Integer> valueSortMap = new HashMap<Integer, Integer>();
	/** HashMap used for the street and property ages on the street.*/
	HashMap<String, ArrayList<Double>> ageAvgMap = new HashMap<String, ArrayList<Double>>();
	/** HashMap used for the street and property values on the street.*/
	HashMap<String, ArrayList<Double>> valAvgMap = new HashMap<String, ArrayList<Double>>();
	/** HashMap used for the street and total difference of propertys on the street*/
	HashMap<String, ArrayList<Double>> totalMap = new HashMap<String, ArrayList<Double>>();
	/** HashMap used for street and house types on that street*/
	HashMap<String, ArrayList<String>> houseMap = new HashMap<String, ArrayList<String>>();
	/** HashMap used for street and postal codes on that street*/
	HashMap<String, ArrayList<String>> streetPostalMap = new HashMap<String, ArrayList<String>>();
	/** HashMap used for postal code and value of property by postal code*/
	HashMap<String, ArrayList<Double>> postalValMap = new HashMap<String, ArrayList<Double>>();
	/** HashMap used for postal code and age of property by postal code*/
	HashMap<String, ArrayList<Double>> postalAgeMap = new HashMap<String, ArrayList<Double>>();
	/** HashMap used for postal code and total value changed by postal code*/
	HashMap<String, ArrayList<Double>> postalChangeMap = new HashMap<String, ArrayList<Double>>();
	/** HashMap used for postal code and house type by postal code*/
	HashMap<String, ArrayList<String>> housePostalMap = new HashMap<String, ArrayList<String>>();
	
	/**Holds standard deviation of house age */
	double standDevAge;
	/** Holds standard deviation of house value */
	double standDevVal;
	/** Used in calculating standard deviation of value changes*/
	ArrayList<Double> standChange = new ArrayList<Double>();
	/** Holds standard deviation of value changed*/
	double standDevChange;
	/** Holds average house age  */
	double avgAge;
	/** Holds average house value */
	double avgVal;
	/** Holds total house value change */
	double valChangedTotal;
	/** sum used for calculations*/
	double sum = 0.0;
	/** Temporary List*/
	List<String> tempList;
	/** ArrayList of Rows from the csv file*/
	ArrayList<Row> list = new ArrayList<Row>(
			10);/* ArrayList of Row objects that hold */
	
	/*Decimal Formats*/
	DecimalFormat df = new DecimalFormat("#");
	DecimalFormat year = new DecimalFormat("#");
	DecimalFormat currency = new DecimalFormat("'$'0.00");
	DecimalFormat largeCurrency = new DecimalFormat("'$'0");
	/** rangeList for calculating 25k range*/
	ArrayList<ArrayList<Double>> rangeList = new ArrayList<ArrayList<Double>>();
}/*-------------------------------------------------------------------------------------END OF CLASS--------------------------------------------------------------------------------------------------*/
