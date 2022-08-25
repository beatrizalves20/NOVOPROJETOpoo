package javaapplication1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ContaDAO {
    public void adicionarCliente(Cliente cliente) {
        ConnectionPostgreSQL postgres = new ConnectionPostgreSQL();
        PreparedStatement stmt = null;
        Connection conexao = null;
        try {
            conexao = postgres.getConection();
            stmt = conexao.prepareStatement("INSERT INTO Cliente(idCliente, cpf, saldo, limite) VALUES(?,?,?,?,?)");
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setInt(4, cliente.getCpf());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            postgres.close(stmt, conexao);
        }
    }

    public List<Cliente> listarCliente() {
        List<Cliente> listaRetorno = new LinkedList<>();

        ConnectionPostgreSQL postgres = new ConnectionPostgreSQL();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conexao = null;
        try {
            conexao = postgres.getConection();
            stmt = conexao.prepareStatement("SELECT * FROM CONTA ORDER BY CODIGO");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente conta = new Cliente(rs.getInt("idCliente"),
                        //String nome, String telefone, String endereco, int cpf
                        rs.getString("nomeCliente"), rs.getString("Telefone"), rs.getString("Endereço"), rs.getInt("Cpf");
                listaRetorno.add(conta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            postgres.close(rs, stmt, conexao);
        }

        return listaRetorno;
    }

    public void deposito(int codigoDaConta, float valor) throws Exception {
        ContaCorrente conta = getContaPeloCodigo(codigoDaConta);
        if (conta != null) {
            conta.deposito(valor);
            updateConta(conta);
        } else {
            throw new Exception("Conta inválida!");
        }
    }

    public ContaCorrente getContaPeloCodigo(int codigoDaConta) {
        ConnectionPostgreSQL postgres = new ConnectionPostgreSQL();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conexao = null;
        try {
            conexao = postgres.getConection();
            stmt = conexao.prepareStatement("SELECT * FROM CONTA WHERE CODIGO=?");
            stmt.setInt(1, codigoDaConta);
            rs = stmt.executeQuery();

            if (rs.next()) {
                ContaCorrente conta = new ContaCorrente(rs.getInt("codigo"),
                        rs.getString("nomeCliente"), rs.getDouble("saldo"), rs.getFloat("limite"));
                return conta;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            postgres.close(rs, stmt, conexao);
        }
        return null;
    }

    public void saque(int codigoDaConta, float valor) throws Exception {
         ContaCorrente conta = getContaPeloCodigo(codigoDaConta);
        if (conta != null) {
            conta.saque(valor);
            updateConta(conta);
        } else {
            throw new Exception("Conta inválida!");
        }
    }

    public double getSaldo(int codigoDaConta) throws Exception {
        ContaCorrente conta = getContaPeloCodigo(codigoDaConta);
        if (conta != null) {
            return conta.getSaldo();
        } else {
            throw new Exception("Conta inválida!");
        }
    }

     public void updateConta(ContaCorrente conta) {
        ConnectionPostgreSQL postgres = new ConnectionPostgreSQL();
        PreparedStatement stmt = null;
        Connection conexao = null;
        try {
            conexao = postgres.getConection();
            stmt = conexao.prepareStatement("UPDATE CONTA SET nomeCliente=?, saldo=?, limite=? WHERE CODIGO=?");
            stmt.setString(1, conta.getNomeCliente());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setFloat(3, conta.getLimite());
            stmt.setInt(4, conta.getCodigo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            postgres.close(stmt, conexao);
        }
    }
    public void deletarConta(int codigo) {

        ConnectionPostgreSQL postgres = new ConnectionPostgreSQL();
        PreparedStatement stmt = null;
        Connection conexao = null;
        try {
            conexao = postgres.getConection();
            stmt = conexao.prepareStatement("DELETE FROM CONTA WHERE CODIGO=?");
            stmt.setInt(1, codigo);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            postgres.close(null, stmt, conexao);
        }
    }
}
