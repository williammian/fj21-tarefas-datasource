package br.com.caelum.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.modelo.Tarefa;

@Repository
public class JdbcTarefaDao{
	
	private Connection connection;
	
	@Autowired
	public JdbcTarefaDao(DataSource dataSource){
		try{
			this.connection = dataSource.getConnection();
		}catch(SQLException err){
			throw new RuntimeException(err);
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void adiciona(Tarefa tarefa){
		String sql = "insert into tarefas " +
				 	 "(descricao, finalizado, dataFinalizacao) " +
				 	 "values(?,?,?)";
	
		try{
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			
			stmt.setString(1, tarefa.getDescricao());
			stmt.setInt(2, 0);
			stmt.setDate(3, null);
						
			stmt.execute();
			stmt.close();
			
		}catch(SQLException err){
			throw new RuntimeException(err);
		}
	}
	
	public List<Tarefa> getLista(){
		try{
			List<Tarefa> tarefas = new ArrayList<Tarefa>();
			PreparedStatement stmt = getConnection().prepareStatement("select * from tarefas order by descricao");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getInt("finalizado") == 1 ? true : false);
				tarefa.setDataFinalizacao(rs.getDate("dataFinalizacao"));
				
				tarefas.add(tarefa);
			}
			
			rs.close();
			stmt.close();
			return tarefas;
		}catch(SQLException err){
			throw new RuntimeException(err);
		}
	}
	
	public Tarefa buscaPorId(Long id){
		try{
			PreparedStatement stmt = getConnection().prepareStatement("select * from tarefas where id=?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
						
			Tarefa tarefa = new Tarefa();
			
			while(rs.next()){
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getInt("finalizado") == 1 ? true : false);
				tarefa.setDataFinalizacao(rs.getDate("dataFinalizacao"));
			}
			
			rs.close();
			stmt.close();
			return tarefa;
		}catch(SQLException err){
			throw new RuntimeException(err);
		}
	}
	
	public void remove(Tarefa tarefa){
		try{
			PreparedStatement stmt = getConnection().prepareStatement("delete from Tarefas where id=?");
			stmt.setLong(1, tarefa.getId());
			
			stmt.execute();
			stmt.close();
		}catch(SQLException err){
			throw new RuntimeException(err);
		}
	}
	
	public void altera(Tarefa tarefa){
		String sql = "update tarefas set descricao=?, finalizado=?, dataFinalizacao=? where id=?";
		try{
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			stmt.setString(1, tarefa.getDescricao());
			stmt.setInt(2, tarefa.isFinalizado() ? 1 : 0);
			stmt.setDate(3, tarefa.getDataFinalizacao() == null ? null : new Date(tarefa.getDataFinalizacao().getTime()));
			stmt.setLong(4, tarefa.getId());
			
			stmt.execute();
			stmt.close();
		}catch(SQLException err){
			throw new RuntimeException(err);
		}
	}
	
	public void finaliza(Long id){
		String sql = "update tarefas set finalizado=?, dataFinalizacao=? where id=?";
		try{
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.setDate(2, new Date(new java.util.Date().getTime()));
			stmt.setLong(3, id);
			
			stmt.execute();
			stmt.close();
		}catch(SQLException err){
			throw new RuntimeException(err);
		}
	}
	
}
