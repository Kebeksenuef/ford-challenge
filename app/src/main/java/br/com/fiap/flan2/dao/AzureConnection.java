package br.com.fiap.flan2.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.flan2.dto.ItemRevisao;
import br.com.fiap.flan2.dto.Modelo;
import br.com.fiap.flan2.dto.Revisao;

public class AzureConnection {

    private static final String HOST_NAME = "fiap-challenge.database.windows.net";
    private static final String DATABASE = "Flan2";
    private static final String USERNAME = "fiap-user";
    private static final String PASSWORD = "ChallengeP@$$w0rd";
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://%s:1433/%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private static final String QUERY_CONSULTA_MODELOS = "SELECT [CODIGO],[NOME] FROM [dbo].[MODELO] ORDER BY [NOME] DESC";
    private static final String QUERY_CONSULTA_ITENS = "SELECT [CODIGO],[DESCRICAO],[PRECO] FROM [dbo].[ITEM]";
    private static final String QUERY_CONSULTA_ITENS_POR_REVISAO = "SELECT I.*\n" +
            "FROM REVISAO R\n" +
            "\tINNER JOIN ITEM_REVISAO IR ON IR.CODIGO_REVISAO = R.CODIGO\n" +
            "\tINNER JOIN ITEM I ON I.CODIGO = IR.CODIGO_ITEM\n" +
            "WHERE R.CODIGO = ?;";
    private static final String QUERY_CONSULTA_PROXIMA_REVISAO = "SELECT TOP 1 R.*\n" +
            "FROM REVISAO R\n" +
            "\tLEFT JOIN VEICULO_REVISAO VR ON VR.CODIGO_REVISAO = R.CODIGO AND VR.CODIGO_VEICULO = ?\n" +
            "WHERE VR.CODIGO_REVISAO IS NULL\n" +
            "\tAND R.CODIGO_MODELO = ?\n" +
            "ORDER BY R.LIMITE_QUILOMETRAGEM;\n";
    private static final String QUERY_CONSULTA_REVISOES_POR_VEICULO = "SELECT R.*, CASE WHEN (VR.CODIGO_REVISAO IS NULL) THEN 'N' ELSE 'S' END AS INDICADOR_REVISAO_REALIZADA\n" +
            "FROM REVISAO R\n" +
            "\tLEFT JOIN VEICULO_REVISAO VR ON VR.CODIGO_REVISAO = R.CODIGO AND VR.CODIGO_VEICULO = ?\n" +
            "WHERE R.CODIGO_MODELO = ?\n" +
            "ORDER BY R.LIMITE_QUILOMETRAGEM;";
    private static final String COMANDO_INSERT_REVISAO = "INSERT INTO VEICULO_REVISAO\n" +
            "(CODIGO_VEICULO, CODIGO_REVISAO, DATA_REALIZACAO)\n" +
            "VALUES\n" +
            "(?, ?, GETDATE())";

    private static final String TAG = "CONEXÃO";

    public static Connection getConnection() {
        Connection conexao = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conexao = DriverManager.getConnection(String.format(CONNECTION_STRING, HOST_NAME, DATABASE, USERNAME, PASSWORD));
            Log.i(TAG,"CONEXÃO FUNCIONANDO");
        } catch (SQLException e) {
            conexao = null;
            e.printStackTrace();
            Log.i(TAG,"CONEXÃO NÃO ESTABELECIDA!");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conexao;
    }

    public static List<Modelo> consultarModelos() {
        List<Modelo> modelos = new ArrayList<Modelo>();

        try (Connection conexao = getConnection()) {
            Statement comando = conexao.createStatement();
            ResultSet resultado = comando.executeQuery(QUERY_CONSULTA_MODELOS);

            while (resultado.next()) {
                Modelo modelo = new Modelo(resultado.getInt("CODIGO"), resultado.getString("NOME"));

                modelos.add(modelo);
            }
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelos;
    }

    public static List<ItemRevisao> consultarItens() {
        List<ItemRevisao> itens = new ArrayList<>();

        try (Connection conexao = getConnection()) {
            Statement comando = conexao.createStatement();
            ResultSet resultado = comando.executeQuery(QUERY_CONSULTA_ITENS);

            while (resultado.next()) {
                ItemRevisao item = new ItemRevisao(resultado.getInt("CODIGO"), resultado.getString("DESCRICAO"), resultado.getFloat("PRECO"));

                itens.add(item);
            }

            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itens;
    }

    public static List<ItemRevisao> consultarItens(int codigoRevisao) {
        List<ItemRevisao> itens = new ArrayList<>();

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(QUERY_CONSULTA_ITENS_POR_REVISAO);
            comando.setInt(1, codigoRevisao);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                ItemRevisao item = new ItemRevisao(resultado.getInt("CODIGO"), resultado.getString("DESCRICAO"), resultado.getFloat("PRECO"));

                itens.add(item);
            }

            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itens;
    }

    public static Revisao consultarProximaRevisao(String chassi, int codigoModelo) {
        Revisao revisao = null;

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(QUERY_CONSULTA_PROXIMA_REVISAO);
            comando.setString(1, chassi);
            comando.setInt(2, codigoModelo);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                revisao = new Revisao();
                revisao.setCodigo(resultado.getInt("CODIGO"));
                revisao.setCodigoModelo(resultado.getInt("CODIGO_MODELO"));
                revisao.setDescricao(resultado.getString("DESCRICAO"));
                revisao.setPrazoMeses(resultado.getInt("PRAZO_MESES"));
                revisao.setLimiteQuilometragem(resultado.getInt("LIMITE_QUILOMETRAGEM"));
                revisao.setValorVista(resultado.getFloat("VALOR_VISTA"));
                revisao.setValorParcela(resultado.getFloat("VALOR_PARCELA"));
                revisao.setQuantidadeParcelas(resultado.getInt("QUANTIDADE_PARCELAS"));
                revisao.setItens(consultarItens(revisao.getCodigo()));
            }
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return revisao;
    }

    public static List<Revisao> consultarRevisoes(String chassi, int codigoModelo) {
        List<Revisao> revisoes = new ArrayList<>();

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(QUERY_CONSULTA_REVISOES_POR_VEICULO);
            comando.setString(1, chassi);
            comando.setInt(2, codigoModelo);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                Revisao revisao = new Revisao();
                revisao.setCodigo(resultado.getInt("CODIGO"));
                revisao.setCodigoModelo(resultado.getInt("CODIGO_MODELO"));
                revisao.setDescricao(resultado.getString("DESCRICAO"));
                revisao.setPrazoMeses(resultado.getInt("PRAZO_MESES"));
                revisao.setLimiteQuilometragem(resultado.getInt("LIMITE_QUILOMETRAGEM"));
                revisao.setValorVista(resultado.getFloat("VALOR_VISTA"));
                revisao.setValorParcela(resultado.getFloat("VALOR_PARCELA"));
                revisao.setQuantidadeParcelas(resultado.getInt("QUANTIDADE_PARCELAS"));
                revisao.setRealizada("S".equals(resultado.getString("INDICADOR_REVISAO_REALIZADA")));
                revisao.setItens(consultarItens(revisao.getCodigo()));

                revisoes.add(revisao);
            }
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return revisoes;
    }

    public static boolean realizarRevisao(String chassi, int codigoRevisao) {
        boolean sucesso = false;

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(COMANDO_INSERT_REVISAO);
            comando.setString(1, chassi);
            comando.setInt(2, codigoRevisao);
            comando.executeUpdate();
            comando.close();

            sucesso = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }
}