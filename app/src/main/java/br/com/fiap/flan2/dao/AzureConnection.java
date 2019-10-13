package br.com.fiap.flan2.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.fiap.flan2.dto.ItemRevisao;
import br.com.fiap.flan2.dto.Manutencao;
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
            "\tAND R.LIMITE_QUILOMETRAGEM > ?\n" +
            "ORDER BY R.LIMITE_QUILOMETRAGEM;\n";
    private static final String QUERY_CONSULTA_REVISOES_POR_VEICULO = "SELECT R.*, CASE WHEN (VR.CODIGO_REVISAO IS NULL) THEN 'N' ELSE 'S' END AS INDICADOR_REVISAO_REALIZADA\n" +
            "FROM REVISAO R\n" +
            "\tLEFT JOIN VEICULO_REVISAO VR ON VR.CODIGO_REVISAO = R.CODIGO AND VR.CODIGO_VEICULO = ?\n" +
            "WHERE R.CODIGO_MODELO = ?\n" +
            "ORDER BY R.LIMITE_QUILOMETRAGEM;";

    private static final String QUERY_CONSULTA_MANUTENCOES_POR_VEICULO = "SELECT M.CODIGO AS CODIGO_MANUTENCAO, M.DESCRICAO AS DESCRICAO_MANUTENCAO, M.DATA_REALIZACAO, I.CODIGO AS CODIGO_ITEM, I.DESCRICAO AS DESCRICAO_ITEM\n" +
            "FROM MANUTENCAO M\n" +
            "\tINNER JOIN VEICULO_MANUTENCAO VM ON VM.CODIGO_MANUTENCAO = M.CODIGO\n" +
            "\tINNER JOIN ITEM_MANUTENCAO IM ON IM.CODIGO_MANUTENCAO = M.CODIGO\n" +
            "\tINNER JOIN ITEM I ON I.CODIGO = IM.CODIGO_ITEM\n" +
            "WHERE VM.CODIGO_VEICULO = ?\n" +
            "ORDER BY M.CODIGO, I.CODIGO;";

    private static final String COMANDO_INSERT_REVISAO = "INSERT INTO VEICULO_REVISAO\n" +
            "(CODIGO_VEICULO, CODIGO_REVISAO, DATA_REALIZACAO)\n" +
            "VALUES\n" +
            "(?, ?, ?)";

    private static final String COMANDO_INSERT_MANUTENCAO = "INSERT INTO MANUTENCAO (DESCRICAO, DATA_REALIZACAO) VALUES (?, ?);\n" +
            "SELECT @@IDENTITY AS 'Identity';  ";

    private static final String COMANDO_INSERT_MANUTENCAO_ITEM = "INSERT INTO ITEM_MANUTENCAO (CODIGO_ITEM, CODIGO_MANUTENCAO, QUANTIDADE) VALUES (?, ?, 1);";

    private static final String COMANDO_INSERT_MANUTENCAO_VEICULO = "INSERT INTO VEICULO_MANUTENCAO (CODIGO_VEICULO, CODIGO_MANUTENCAO) VALUES (?, ?);";

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

    public static Revisao consultarProximaRevisao(String chassi, int codigoModelo, float quilometragem) {
        Revisao revisao = null;

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(QUERY_CONSULTA_PROXIMA_REVISAO);
            comando.setString(1, chassi);
            comando.setInt(2, codigoModelo);
            comando.setFloat(3, quilometragem);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
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

    public static List<Manutencao> consultarManutencoes(String chassi) {
        List<Manutencao> manutencoes = new ArrayList<>();

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(QUERY_CONSULTA_MANUTENCOES_POR_VEICULO);
            comando.setString(1, chassi);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int indice = -1;
                Manutencao manutencao = new Manutencao(resultado.getInt("CODIGO_MANUTENCAO"));
                ItemRevisao item = new ItemRevisao();

                item.setCodigo(resultado.getInt("CODIGO_ITEM"));
                item.setDescricao(resultado.getString("DESCRICAO_ITEM"));
                //item.setPreco(resultado.getFloat("PRECO_ITEM"));
                //item.setQuantidade(resultado.getInt("QUANTIDADE_ITEM"));

                indice = manutencoes.indexOf(manutencao);

                if (indice > -1) {
                    manutencao = manutencoes.get(indice);
                } else {
                    manutencao.setDescricao(resultado.getString("DESCRICAO_MANUTENCAO"));
                    manutencao.setDataRealizacao(new Date(resultado.getDate("DATA_REALIZACAO").getTime()));

                    manutencoes.add(manutencao);
                }

                manutencao.getItens().add(item);
            }
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return manutencoes;
    }

    public static boolean realizarRevisao(String chassi, int codigoRevisao) {
        boolean sucesso = false;

        try (Connection conexao = getConnection()) {
            PreparedStatement comando = conexao.prepareStatement(COMANDO_INSERT_REVISAO);
            comando.setString(1, chassi);
            comando.setInt(2, codigoRevisao);
            comando.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
            comando.executeUpdate();
            comando.close();

            sucesso = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }

    public static long realizarManutencao(String chassi, String descricao, Collection<ItemRevisao> itens) {
        long idManutencao = -1;

        try (Connection conexao = getConnection()) {
            PreparedStatement comandoManutencao = conexao.prepareStatement(COMANDO_INSERT_MANUTENCAO);
            comandoManutencao.setString(1, descricao);
            comandoManutencao.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            ResultSet resultado = comandoManutencao.executeQuery();

            if (resultado.next()) {
                idManutencao = resultado.getLong(1);
            }

            for (ItemRevisao item : itens) {
                PreparedStatement comandoItem = conexao.prepareStatement(COMANDO_INSERT_MANUTENCAO_ITEM);
                comandoItem.setInt(1, item.getCodigo());
                comandoItem.setLong(2, idManutencao);
                comandoItem.executeUpdate();
            }

            PreparedStatement comandoVeiculo = conexao.prepareStatement(COMANDO_INSERT_MANUTENCAO_VEICULO);
            comandoVeiculo.setString(1, chassi);
            comandoVeiculo.setLong(2, idManutencao);
            comandoVeiculo.executeUpdate();

            comandoManutencao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idManutencao;
    }
}