package com.bcsg.mytestapplication.dao;

import android.util.Log;

import com.bcsg.mytestapplication.TelematicsCollector;
import com.bcsg.mytestapplication.dto.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class AzureConnection {
    private static final String HOST_NAME = "ford-challenge.database.windows.net";
    private static final String DATABASE = "Flan2";
    private static final String USERNAME = "ford-user";
    private static final String PASSWORD = "FiapChallengeP@$$w0rd";
    private static final String CONNECTION_STRING = "jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private static final String QUERY_CONSULTA_MODELOS = "SELECT [CODIGO],[NOME] FROM [dbo].[MODELOS]";

    private static final String TAG = "CONEXÃO";

    public static Connection getConnection() {
        Connection conexao;
        try {
            conexao = DriverManager.getConnection(String.format(CONNECTION_STRING, HOST_NAME, DATABASE, USERNAME, PASSWORD));
            Log.i(TAG,"CONEXÃO FUNCIONANDO");
        } catch (SQLException e) {
            conexao = null;
            e.printStackTrace();
            Log.i(TAG,"CONEXÃO NÃO ESTABELECIDA!");
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
}
