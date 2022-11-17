package com.peregrinoti.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.peregrinoti.entity.TipoColecao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TipoColecaoEditController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private GridPane pnlDados;

	@FXML
	private Label lblNome;

	@FXML
	private TextField txtNome;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private Button btnOK;

	@FXML
	private Button btnCancela;

	private Stage janelaTipoColecaoEdit;

	private TipoColecao tipoColecao;

	private boolean okClick = false;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaTipoColecaoEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.tipoColecao.setNome(this.txtNome.getText());

			this.okClick = true;
			this.getJanelaTipoColecaoEdit().close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Stage getJanelaTipoColecaoEdit() {
		return janelaTipoColecaoEdit;
	}

	public void setJanelaTipoColecaoEdit(Stage janelaTipoColecaoEdit) {
		this.janelaTipoColecaoEdit = janelaTipoColecaoEdit;
	}

	public void populaTela(TipoColecao tipoColecao) {
		this.tipoColecao = tipoColecao;

		this.txtNome.setText(tipoColecao.getNome());
	}

	public boolean isOkClick() {
		return okClick;
	}

	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.txtNome.getText() == null || this.txtNome.getText().trim().length() == 0) {
			mensagemErros += "Informe a cor!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaTipoColecaoEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}

}
