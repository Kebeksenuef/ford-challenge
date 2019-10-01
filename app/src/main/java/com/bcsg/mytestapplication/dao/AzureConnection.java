package com.bcsg.mytestapplication.dao;

import android.util.Log;

import com.bcsg.mytestapplication.dto.ItemRevisao;
import com.bcsg.mytestapplication.dto.Modelo;
import com.bcsg.mytestapplication.dto.Revisao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class AzureConnection {

    private static final String HOST_NAME = "fiap-challenge.database.windows.net";
    private static final String DATABASE = "Flan2";
    private static final String USERNAME = "fiap-user";
    private static final String PASSWORD = "ChallengeP@$$w0rd";
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://%s:1433/%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private static final String QUERY_CONSULTA_MODELOS = "SELECT [CODIGO],[NOME] FROM [dbo].[MODELO]";
    private static final String QUERY_CONSULTA_ITENS = "SELECT [CODIGO],[DESCRICAO],[PRECO] FROM [dbo].[ITEM]";
    private static final String QUERY_CONSULTA_REVISOES = "SELECT R.[CODIGO] AS CODIGO_REVISAO,R.[DESCRICAO],R.[CODIGO_VEICULO],R.[DATA_HORA_AGENDA],R.[DATA_HORA_INICIO],R.[DATA_HORA_FIM], I.[CODIGO] AS CODIGO_ITEM, I.[DESCRICAO] as DESCRICAO_ITEM, I.[PRECO] AS PRECO_ITEM, IR.[QUANTIDADE] AS QUANTIDADE_ITEM\n" +
            "FROM [dbo].[REVISAO] R\n" +
            "INNER JOIN [dbo].[ITEM_REVISAO] IR ON IR.CODIGO_REVISAO = R.CODIGO\n" +
            "INNER JOIN [dbo].[ITEM] I ON I.CODIGO = IR.CODIGO_ITEM\n" +
            "WHERE R.[CODIGO_VEICULO] = ?\n" +
            "ORDER BY CODIGO_REVISAO;";

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

    public static List<ItemRevisao> consutlarItens() {
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

    public static List<Revisao> consutlarRevisoes(String codigoVeiculo) {
        List<Revisao> revisoes = new ArrayList<>();

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(QUERY_CONSULTA_REVISOES);
            comando.setString(1, codigoVeiculo);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int indice = -1;
                Revisao revisao = new Revisao(resultado.getInt("CODIGO_REVISAO"));
                ItemRevisao item = new ItemRevisao();

                item.setCodigo(resultado.getInt("CODIGO_ITEM"));
                item.setDescricao(resultado.getString("DESCRICAO_ITEM"));
                item.setPreco(resultado.getFloat("PRECO_ITEM"));
                item.setQuantidade(resultado.getInt("QUANTIDADE_ITEM"));

                indice = revisoes.indexOf(revisao);

                if (indice > -1) {
                    revisao = revisoes.get(indice);
                } else {
                    revisao.setDescricao(resultado.getString("DESCRICAO"));
                    revisao.setCodigoVeiculo(resultado.getString("CODIGO_VEICULO"));

                    revisoes.add(revisao);
                }
                revisao.getItens().add(item);
            }
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revisoes;
    }
}
