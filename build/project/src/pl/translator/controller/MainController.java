package pl.translator.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.translator.app.FlashCard;
import pl.translator.app.Translate;

public class MainController  implements Initializable{

    @FXML
    private TextArea TxtAWritedText;

    @FXML
    private Button BtnTranslate;

    @FXML
    private Button BtnReverse;

    @FXML
    private Button BtnDelete;

    @FXML
    private TextArea TxtATranslatedText;

    @FXML
    private MenuButton MBtnTranslatedText;

    @FXML
    private MenuButton MBtnTextToTranslate;

    @FXML
    private TableView<FlashCard> TVwordList = new TableView<FlashCard>() ;

    ObservableList<FlashCard> data = FXCollections.observableArrayList();
    List<FlashCard> FlashCardList = new ArrayList<>();
    Map<String , String> languageMap = new HashMap<String, String>(){{
        put("Polski", "pl");
        put("Niemiecki", "de");
        put("Angielski", "en");
    }};;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		 // Set a default text and language in menuButton
		 MBtnTextToTranslate.setText("Polski");
		 MBtnTranslatedText.setText("Angielski");
		 // We are creating new object to translate text and giving a default languages like a parameters
		 // We take languages from default text on buttons
		 Translate tr = new Translate(languageMap.get(MBtnTextToTranslate.getText()), languageMap.get(MBtnTranslatedText.getText()));

		 // Initialization for  columns
		 // In field PropertyValueFactory name of variable from FlashCard class
		 TableColumn<FlashCard,String> firstNameCol = new TableColumn<FlashCard,String>("Tekst");
  		 firstNameCol.setCellValueFactory(new PropertyValueFactory("firstWord"));
  		 firstNameCol.setMinWidth(150.0);
  		 TableColumn<FlashCard,String> lastNameCol = new TableColumn<FlashCard,String>("Tlumaczenie");
  		 lastNameCol.setCellValueFactory(new PropertyValueFactory("secondWord"));
  		 lastNameCol.setMinWidth(150.0);
  		 TVwordList.getColumns().setAll(firstNameCol, lastNameCol);
  		 TVwordList.setItems(data);



		 BtnTranslate.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		          try {

		        	String textToTranslate =  TxtAWritedText.getText();
					String translatedText = tr.translate(textToTranslate);
					FlashCard temp = new FlashCard(textToTranslate,translatedText);
					FlashCardList.add(temp);
					data.clear();
					data.addAll(FlashCardList);
					TxtATranslatedText.setText(translatedText);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		        }
		    });

		 // Action to reverse language, textaArea and text in menuButton.
		 BtnReverse.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		        tr.setSourceLanguage(languageMap.get(MBtnTranslatedText.getText()));
		        tr.setDestinationLanguage(languageMap.get(MBtnTextToTranslate.getText()));
		        String tempLang = MBtnTranslatedText.getText();
		        String tempArea = TxtATranslatedText.getText();
		        MBtnTranslatedText.setText(MBtnTextToTranslate.getText());
		        MBtnTextToTranslate.setText(tempLang);
		        TxtATranslatedText.setText(TxtAWritedText.getText());
		        TxtAWritedText.setText(tempArea);
		        }
		    });

		 BtnDelete.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		        	FlashCard selectedItem  = TVwordList.getSelectionModel().getSelectedItem();
		        	TVwordList.getItems().remove(selectedItem);
		        }
		    });

		 // Setting action for every item in MenuButton  MBtnTextToTranslate
		 for(MenuItem item : MBtnTextToTranslate.getItems() ){
			 item.setOnAction(new EventHandler<ActionEvent>() {
			        @Override
			        public void handle(ActionEvent event) {
			        	MBtnTextToTranslate.setText(item.getText());
			        	// We changed source languaged
			        	tr.setSourceLanguage(languageMap.get(item.getText()));
			        }
			    });
		 }

		 // Setting action for every item in MenuButton  MBtnTranslatedText
		 for(MenuItem item : MBtnTranslatedText.getItems() ){
			 item.setOnAction(new EventHandler<ActionEvent>() {
			        @Override
			        public void handle(ActionEvent event){
			        	MBtnTranslatedText.setText(item.getText());
			        	// we changed destination language
			        	tr.setDestinationLanguage(languageMap.get(item.getText()));
			        }
			    });
		 }




	}

}
