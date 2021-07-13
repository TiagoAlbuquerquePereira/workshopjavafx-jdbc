package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.Person;

public class ViewController implements Initializable {

	@FXML
	private ComboBox<Person> ComboBoxPerson;
	private ObservableList<Person> ObservableListPerson;
	private Button bt;

	@FXML
	private void onComboboxAction() {
		Person person = ComboBoxPerson.getSelectionModel().getSelectedItem();
		System.out.println(person);
	}

	@FXML
	private void onButtonAction() {
		for (Person person : ComboBoxPerson.getItems()) {

			System.out.println(person);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Person> list = new ArrayList<Person>();
		list.add(new Person(1, "Tiago", "Tiago@gmail.com"));
		list.add(new Person(2, "Severino", "sev@gmail.com"));
		list.add(new Person(3, "bio", "sev@gmail.com"));
		list.add(new Person(4, "chico", "chico.com"));

		ObservableListPerson = FXCollections.observableArrayList(list);

		ComboBoxPerson.setItems(ObservableListPerson);

		Callback<ListView<Person>, ListCell<Person>> factory = lv -> new ListCell<Person>() {
			@Override
			protected void updateItem(Person item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		ComboBoxPerson.setCellFactory(factory);
		ComboBoxPerson.setButtonCell(factory.call(null));

	}

}
