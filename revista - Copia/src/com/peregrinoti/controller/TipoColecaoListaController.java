package com.peregrinoti.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.peregrinoti.dao.TipoColecaoDAO;
import com.peregrinoti.entity.TipoColecao;

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

public class TipoColecaoListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<TipoColecao> tbvTipoColecoes;

	@FXML
	private TableColumn<TipoColecao, Long> tbcCodigo;

	@FXML
	private TableColumn<TipoColecao, String> tbcNome;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetalhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblNome;

	@FXML
	private Label lblNomeValor;

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

	private List<TipoColecao> listaTipoColecoes;
	private ObservableList<TipoColecao> observableListaTipoColecoes = FXCollections.observableArrayList();
	private TipoColecaoDAO tipoColecaoDAO;

	public static final String TIPOCOLECAO_EDITAR = " - Editar";
	public static final String TIPOCOLECAO_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {
		TipoColecao tipoColecao = this.tbvTipoColecoes.getSelectionModel().getSelectedItem();

		if (tipoColecao != null) {
			boolean btnConfirmarClic = this.onShowTelaTipoColecaoEditar(tipoColecao,
					TipoColecaoListaController.TIPOCOLECAO_EDITAR);

			if (btnConfirmarClic) {
				this.getTipoColecaoDAO().update(tipoColecao, null);
				this.carregarTableViewTipoColecoes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um tipo de coleção na tabela!");
			alerta.show();
		}

	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {
		TipoColecao tipoColecao = this.tbvTipoColecoes.getSelectionModel().getSelectedItem();

		if (tipoColecao != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do tipo de coleção?\n" + tipoColecao.getNome());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getTipoColecaoDAO().delete(tipoColecao);
				this.carregarTableViewTipoColecoes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um tipo de coleção na tabela!");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		TipoColecao tipoColecao = new TipoColecao();

		boolean btnConfirmarClic = this.onShowTelaTipoColecaoEditar(tipoColecao,
				TipoColecaoListaController.TIPOCOLECAO_INCLUIR);

		if (btnConfirmarClic) {
			this.getTipoColecaoDAO().save(tipoColecao);
			this.carregarTableViewTipoColecoes();
		}
	}

	public List<TipoColecao> getListaTipoColecoes() {
		return listaTipoColecoes;
	}

	public void setListaTipoColecoes(List<TipoColecao> listaTipoColecoes) {
		this.listaTipoColecoes = listaTipoColecoes;
	}

	public ObservableList<TipoColecao> getObservableListaTipoColecoes() {
		return observableListaTipoColecoes;
	}

	public void setObservableListaTipoColecoes(ObservableList<TipoColecao> observableListaTipoColecoes) {
		this.observableListaTipoColecoes = observableListaTipoColecoes;
	}

	public TipoColecaoDAO getTipoColecaoDAO() {
		return tipoColecaoDAO;
	}

	public void setTipoColecaoDAO(TipoColecaoDAO tipoColecaoDAO) {
		this.tipoColecaoDAO = tipoColecaoDAO;
	}

	public void initialize(URL location, ResourceBundle resources) {
		this.setTipoColecaoDAO(new TipoColecaoDAO());
		this.carregarTableViewTipoColecoes();
		this.selecionarItemTableViewTipoColecoes(null);

		this.tbvTipoColecoes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewTipoColecoes(newValue));

	}

	public void carregarTableViewTipoColecoes() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaTipoColecoes(this.getTipoColecaoDAO().getAll());
		this.setObservableListaTipoColecoes(FXCollections.observableArrayList(this.getListaTipoColecoes()));
		this.tbvTipoColecoes.setItems(this.getObservableListaTipoColecoes());
	}

	public void selecionarItemTableViewTipoColecoes(TipoColecao tipoColecao) {
		if (tipoColecao != null) {
			this.lblNomeValor.setText(tipoColecao.getNome());
		} else {
			this.lblNomeValor.setText("");
		}
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de tipo de coleção?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public boolean onShowTelaTipoColecaoEditar(TipoColecao tipoColecao, String operacao) {
		try {
			// carregando o loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/peregrinoti/view/TipoColecaoEdit.fxml"));
			Parent tipoColecaoEditXML = loader.load();

			// criando uma janela nova
			Stage janelaTipoColecaoEditar = new Stage();
			janelaTipoColecaoEditar.setTitle("Cadastro de tipo de coleção" + operacao);
			janelaTipoColecaoEditar.initModality(Modality.APPLICATION_MODAL);
			janelaTipoColecaoEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene tipoColecaoEditLayout = new Scene(tipoColecaoEditXML);
			janelaTipoColecaoEditar.setScene(tipoColecaoEditLayout);

			// carregando o controller e a scene
			TipoColecaoEditController tipoColecaoEditController = loader.getController();
			tipoColecaoEditController.setJanelaTipoColecaoEdit(janelaTipoColecaoEditar);
			tipoColecaoEditController.populaTela(tipoColecao);

			// mostrando a tela
			janelaTipoColecaoEditar.showAndWait();

			return tipoColecaoEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<TipoColecao> retornaListagemTipoColecao() {
		if (this.getTipoColecaoDAO() == null) {
			this.setTipoColecaoDAO(new TipoColecaoDAO());
		}
		return this.getTipoColecaoDAO().getAll();
	}

}
