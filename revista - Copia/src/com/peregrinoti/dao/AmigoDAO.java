package com.peregrinoti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.peregrinoti.entity.Amigo;

public class AmigoDAO implements DAO<Amigo>{


		@Override
		public Amigo get(Long id) {
			Amigo amigo = null;
			String sql = "select * from amigo where id = ?";

			// Recupera a conexão com o banco
			Connection conexao = null;

			// Criar uma preparação da consulta
			PreparedStatement stm = null;

			// Criar uma classe que guarde o retorno da operação
			ResultSet rset = null;

			try {

				conexao = new Conexao().getConnection();

				stm = conexao.prepareStatement(sql);
				stm.setInt(1, id.intValue());
				rset = stm.executeQuery();

				while (rset.next()) {
					amigo = new Amigo();

					// atribui campo para atributo
					amigo.setId(rset.getLong("id"));
					amigo.setNome(rset.getString("nome"));
					amigo.setNomeResponsavel(rset.getString("nome_responsavel"));
					amigo.setEndereco(rset.getString("endereco"));
					amigo.setTelefone(rset.getString("telefone"));
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stm != null) {
						stm.close();
					}

					if (conexao != null) {
						conexao.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return amigo;
		}

		@Override
		public List<Amigo> getAll() {
			List<Amigo> amigos = new ArrayList<Amigo>();

			String sql = "select * from amigo";

			// Recupera a conexão com o banco
			Connection conexao = null;

			// Criar uma preparação da consulta
			PreparedStatement stm = null;

			// Criar uma classe que guarde o retorno da operação
			ResultSet rset = null;

			try {

				conexao = new Conexao().getConnection();

				stm = conexao.prepareStatement(sql);
				rset = stm.executeQuery();

				while (rset.next()) {
					Amigo amigo = new Amigo();

					// atribui campo para atributo
					amigo.setId(rset.getLong("id"));
					amigo.setNome(rset.getString("nome"));
					amigo.setNomeResponsavel(rset.getString("nome_responsavel"));
					amigo.setEndereco(rset.getString("endereco"));
					amigo.setTelefone(rset.getString("telefone"));
					
					amigos.add(amigo);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stm != null) {
						stm.close();
					}

					if (conexao != null) {
						conexao.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return amigos;
		}

		@Override
		public int save(Amigo amigo) {
			String sql = "insert into amigo (nome, nome_responsavel, endereco, telefone)" + " values (?, ?, ?, ?)";

			// Recupera a conexão com o banco
			Connection conexao = null;

			// Criar uma preparação da consulta
			PreparedStatement stm = null;

			try {

				conexao = new Conexao().getConnection();

				stm = conexao.prepareStatement(sql);
				stm.setString(1, amigo.getNome());
				stm.setString(2, amigo.getNomeResponsavel());
				stm.setString(3, amigo.getEndereco());
				stm.setString(4, amigo.getTelefone());

				stm.execute();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stm != null) {
						stm.close();
					}

					if (conexao != null) {
						conexao.close();
					}
					return 1;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return 0;
		}

		@Override
		public boolean update(Amigo amigo, String[] params) {
			String sql = "update amigo set cor = ?, codigo_etiqueta = ? where id = ?";

			// Recupera a conexão com o banco
			Connection conexao = null;

			// Criar uma preparação da consulta
			PreparedStatement stm = null;

			try {
				conexao = new Conexao().getConnection();

				stm = conexao.prepareStatement(sql);
				stm.setString(1, amigo.getNome());
				stm.setString(2, amigo.getNomeResponsavel());
				stm.setString(3, amigo.getEndereco());
				stm.setString(4, amigo.getTelefone());
				stm.setLong(3, amigo.getId());

				stm.execute();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stm != null) {
						stm.close();
					}

					if (conexao != null) {
						conexao.close();
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		}

		@Override
		public boolean delete(Amigo amigo) {
			String sql = "delete from amigo where id = ?";

			// Recupera a conexão com o banco
			Connection conexao = null;

			// Criar uma preparação da consulta
			PreparedStatement stm = null;

			try {
				conexao = new Conexao().getConnection();

				stm = conexao.prepareStatement(sql);
				stm.setLong(1, amigo.getId());
				stm.execute();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stm != null) {
						stm.close();
					}

					if (conexao != null) {
						conexao.close();
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		}
}
