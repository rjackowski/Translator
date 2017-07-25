package pl.translator.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.stage.Stage;
import pl.translator.app.FileTool;
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
    private MenuItem MItSaveFile;
    
    @FXML
    private MenuItem MItOpenFile;
    
    @FXML
    private MenuItem MItClose;
    
    @FXML
    private MenuButton MBtnTranslatedText;

    @FXML
    private MenuButton MBtnWritedText;

    @FXML
    private TableView<FlashCard> TVwordList = new TableView<FlashCard>() ;

    private Stage primaryStage;
    private FileTool fileTool;
    
    public void setStage(Stage stage) {
    	this.primaryStage = stage;
    }
    
    public Stage getStage() {
    	return primaryStage;
    }
        
    
    ObservableList<FlashCard> data = FXCollections.observableArrayList();
    HashMap<String, String> languageMap = new HashMap<String, String>(){{
        put("Polski", "pl");
        put("Niemiecki", "de");
        put("Angielski", "en");
    }};;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Create object to save and open files.
		 fileTool = new FileTool(getStage());
		
		// Set a default text and language in menuButton
		 MBtnWritedText.setText("Polski");
		 MBtnTranslatedText.setText("Angielski");
		 // We are creating new object to translate text and giving a default languages like a parameters
		 // We take languages from default text on buttons
		 Translate tr = new Translate(languageMap.get(MBtnWritedText.getText()), languageMap.get(MBtnTranslatedText.getText()));
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
		        	String writedText =  TxtAWritedText.getText();
					String translatedText = tr.translate(writedText);
					FlashCard temp = new FlashCard(writedText,translatedText);
					data.add(temp);
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
		        tr.setDestinationLanguage(languageMap.get(MBtnWritedText.getText()));
		        String tempLang = MBtnTranslatedText.getText();
		        String tempArea = TxtATranslatedText.getText();
		        MBtnTranslatedText.setText(MBtnWritedText.getText());
		        MBtnWritedText.setText(tempLang);
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

		 // Setting action for every item in MenuButton  MBtnWritedText
		 for(MenuItem item : MBtnWritedText.getItems() ){
			 item.setOnAction(new EventHandler<ActionEvent>() {
			        @Override
			        public void handle(ActionEvent event) {
			        	MBtnWritedText.setText(item.getText());
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

		 MItClose.setOnAction(x -> System.exit(0));

		 
		 MItOpenFile.setOnAction(new EventHandler<ActionEvent>(){
			 @Override
			 public void handle(ActionEvent event) {
			 try {
				// Clean current observable list and add new items from file
				data.clear();
				data.addAll(fileTool.openFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }
		 });
		 
		 MItSaveFile.setOnAction(new EventHandler<ActionEvent>(){
			 @Override
			 public void handle(ActionEvent event) {
			 try {
				fileTool.saveFile(data);
			 } catch (IOException e) {
				System.out.println("File saving failed");
			 }
			 }
		 });
	}
	
	 
}