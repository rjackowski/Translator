package pl.translator.app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FlashCard {

private SimpleStringProperty firstWord = new SimpleStringProperty("");
private SimpleStringProperty secondWord = new SimpleStringProperty("");

public FlashCard(String writedText, String translatedText) {
	firstWord = new SimpleStringProperty(); 
	secondWord = new SimpleStringProperty();
	setFirstWord(writedText);
	setSecondWord(translatedText);
}

public String getFirstWord() {
	return firstWord.get();
}
public void setFirstWord(String writedText) {
	this.firstWord.setValue(writedText);
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
