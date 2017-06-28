package pl.translator.app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FlashCard {

private SimpleStringProperty firstWord = new SimpleStringProperty("");
private SimpleStringProperty secondWord = new SimpleStringProperty("");

public FlashCard(String textToTranslate, String translatedText) {
	firstWord = new SimpleStringProperty(); 
	secondWord = new SimpleStringProperty();
	setFirstWord(textToTranslate);
	setSecondWord(translatedText);
}
public String getFirstWord() {
	return firstWord.get();
}
public void setFirstWord(String textToTranslate) {
	this.firstWord.setValue(textToTranslate);
}
public String getSecondWord() {
	return secondWord.get();
}
public void setSecondWord(String translatedText) {
	this.secondWord.setValue(translatedText);
}

@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getFirstWord() + " " + getSecondWord();
	}
}
