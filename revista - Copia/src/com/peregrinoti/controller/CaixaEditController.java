package com.peregrinoti.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.peregrinoti.entity.Caixa;

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

public class CaixaEditController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private GridPane pnlDados;

	@FXML
	private Label lblCor;

	@FXML
	private TextField txtCor;

	@FXML
	private Label lblEtiqueta;

	@FXML
	private TextField txtEtiqueta;

	@FXML
	private HBox pnlBotoes;

	@FXML
	private Button btnOK;

	@FXML
	private Button btnCancela;

	private Stage janelaCaixaEdit;

	private Caixa caixa;

	private boolean okClick = false;

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaCaixaEdit().close();
	}

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.caixa.setCor(this.txtCor.getText());
			this.caixa.setCodigoEtiqueta(this.txtEtiqueta.getText());

			this.okClick = true;
			this.getJanelaCaixaEdit().close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Stage getJanelaCaixaEdit() {
		return janelaCaixaEdit;
	}

	public void setJanelaCaixaEdit(Stage janelaCaixaEdit) {
		this.janelaCaixaEdit = janelaCaixaEdit;
	}

	public void populaTela(Caixa caixa) {
		this.caixa = caixa;

		this.txtCor.setText(caixa.getCor());
		this.txtEtiqueta.setText(caixa.getCodigoEtiqueta());
	}

	public boolean isOkClick() {
		return okClick;
	}

	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.txtCor.getText() == null || this.txtCor.getText().trim().length() == 0) {
			mensagemErros += "Informe a cor!\n";
		}

		if (this.txtEtiqueta.getText() == null || this.txtEtiqueta.getText().trim().length() == 0) {
			mensagemErros += "Informe a etiqueta!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaCaixaEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}
}
