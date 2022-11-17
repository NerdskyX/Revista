package com.peregrinoti.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.peregrinoti.dao.CaixaDAO;
import com.peregrinoti.entity.Caixa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CaixaListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Caixa> tbvCaixas;

	@FXML
	private TableColumn<Caixa, Long> tbcCodigo;

	@FXML
	private TableColumn<Caixa, String> tbcCor;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetalhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblCor;

	@FXML
	private Label lblEtiqueta;

	@FXML
	private Label lblCorValor;

	@FXML
	private Label lblEtiquetaValor;

	@FXML
	private ButtonBar barBotoes;

	@FXML
	private Button btnInclur;

	@FXML
	private Tooltip tlpIncluir;

	@FXML
	private Button btnEditar;

	@FXML
	private Tooltip tlpEditar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Tooltip tlpExcluir;

	private List<Caixa> listaCaixas;
	private ObservableList<Caixa> observableListaCaixas = FXCollections.observableArrayList();
	private CaixaDAO caixaDAO;

	public static final String CAIXA_EDITAR = " - Editar";
	public static final String CAIXA_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		Caixa caixa = this.tbvCaixas.getSelectionModel().getSelectedItem();

		if (caixa != null) {
			boolean btnConfirmarClic = this.onShowTelaCaixaEditar(caixa, CaixaListaController.CAIXA_EDITAR);

			if (btnConfirmarClic) {
				this.getCaixaDAO().update(caixa, null);
				this.carregarTableViewCaixas();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha uma caixa na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		Caixa caixa = this.tbvCaixas.getSelectionModel().getSelectedItem();

		if (caixa != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão da caixa?\n" + caixa.getCor());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getCaixaDAO().delete(caixa);
				this.carregarTableViewCaixas();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha uma caixa na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Caixa caixa = new Caixa();

		boolean btnConfirmarClic = this.onShowTelaCaixaEditar(caixa, CaixaListaController.CAIXA_INCLUIR);

		if (btnConfirmarClic) {
			this.getCaixaDAO().save(caixa);
			this.carregarTableViewCaixas();
		}
	}

	public CaixaDAO getCaixaDAO() {
		return caixaDAO;
	}

	public void setCaixaDAO(CaixaDAO caixaDAO) {
		this.caixaDAO = caixaDAO;
	}

	public List<Caixa> getListaCaixas() {
		return listaCaixas;
	}

	public void setListaCaixas(List<Caixa> listaCaixas) {
		this.listaCaixas = listaCaixas;
	}

	public ObservableList<Caixa> getObservableListaCaixas() {
		return observableListaCaixas;
	}

	public void setObservableListaCaixas(ObservableList<Caixa> observableListaCaixa) {
		this.observableListaCaixas = observableListaCaixa;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setCaixaDAO(new CaixaDAO());
		this.carregarTableViewCaixas();
		this.selecionarItemTableViewCaixas(null);

		this.tbvCaixas.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewCaixas(newValue));
	}

	public void carregarTableViewCaixas() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

		this.setListaCaixas(this.getCaixaDAO().getAll());
		this.setObservableListaCaixas(FXCollections.observableArrayList(this.getListaCaixas()));
		this.tbvCaixas.setItems(this.getObservableListaCaixas());
	}

	public void selecionarItemTableViewCaixas(Caixa caixa) {
		if (caixa != null) {
			this.lblCorValor.setText(caixa.getCor());
			this.lblEtiquetaValor.setText(caixa.getCodigoEtiqueta());
		} else {
			this.lblCorValor.setText("");
			this.lblEtiquetaValor.setText("");
		}
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de caixa?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public boolean onShowTelaCaixaEditar(Caixa caixa, String operacao) {
		try {
			// carregando o loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/peregrinoti/view/CaixaEdit.fxml"));
			Parent caixaEditXML = loader.load();

			// criando uma janela nova
			Stage janelaCaixaEditar = new Stage();
			janelaCaixaEditar.setTitle("Cadastro de caixa" + operacao);
			janelaCaixaEditar.initModality(Modality.APPLICATION_MODAL);
			janelaCaixaEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene caixaEditLayout = new Scene(caixaEditXML);
			janelaCaixaEditar.setScene(caixaEditLayout);

			// carregando o controller e a scene
			CaixaEditController caixaEditController = loader.getController();
			caixaEditController.setJanelaCaixaEdit(janelaCaixaEditar);
			caixaEditController.populaTela(caixa);

			// mostrando a tela
			janelaCaixaEditar.showAndWait();

			return caixaEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Caixa> retornaListagemCaixa() {
		if (this.getCaixaDAO() == null) {
			this.setCaixaDAO(new CaixaDAO());
		}
		return this.getCaixaDAO().getAll();
	}
}
