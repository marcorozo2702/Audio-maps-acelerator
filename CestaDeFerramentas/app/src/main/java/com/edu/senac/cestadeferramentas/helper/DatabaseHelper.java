package com.edu.senac.cestadeferramentas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edu.senac.cestadeferramentas.model.Auto;
import com.edu.senac.cestadeferramentas.model.Produto;
import com.edu.senac.cestadeferramentas.model.Usuario;
import com.edu.senac.cestadeferramentas.model.Produto;
import com.edu.senac.cestadeferramentas.model.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ferramentas.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Usuario, Integer> usuarioDao = null;
    private Dao<Produto, Integer> produtoDao = null;
    private Dao<Auto, Integer> autoDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Produto.class);
            TableUtils.createTable(connectionSource, Auto.class);


            /*Usuario usuario = new Usuario();
            usuario.setLogin("marco@senac.br");
            usuario.setSenha("123");
            getUsuarioDao().create(usuario);*/

        } catch (Exception e) {
            Log.e("banco", "Erro aso criar banco");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        try {
            TableUtils.dropTable(connectionSource, Usuario.class, true);
            TableUtils.dropTable(connectionSource, Produto.class, true);
            TableUtils.dropTable(connectionSource, Auto.class, true);

            onCreate(sqLiteDatabase, connectionSource);
        } catch (Exception e) {
            Log.e("banco", "Erro aso criar banco");
        }

    }

    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Usuario validarUsuario(String email, String senha) {
        try {
            Usuario usuario = getUsuarioDao().queryBuilder().where().eq("email", email).and().eq("senha", senha).queryForFirst();
            return usuario;
        } catch (Exception e) {
            Log.e("banco", "Falha ao recuperar senha");
        }
        return null;
    }

    public Dao<Produto, Integer> getProdutoDao() throws SQLException {
        if (produtoDao == null) {
            produtoDao = getDao(Produto.class);
        }
        return produtoDao;
    }

    public void   salvarProduto(Produto produto) {

        try {
            getProdutoDao().create(produto);
        } catch (Exception e) {
            Log.e("banco", "Erro ao salvar produto");
        }
    }

    public void removerProduto(Produto produto){
        try {
            getProdutoDao().delete(produto);
        } catch (Exception e) {
            Log.e("banco", "Erro ao remover produto");
        }
    }

    public void update(Produto produto){
        try {
            getProdutoDao().update(produto);
        } catch (Exception e) {
            Log.e("banco", "Erro ao atualizar produto");
        }
    }

    public List<Produto> buscarTodos(){

        List<Produto> produtos=null;
        try {
            return getProdutoDao().queryBuilder().query();
        } catch (Exception e) {
            Log.e("banco", "Erro ao buscar produto");
        }
        return new ArrayList<>();
    }


    //---------------------------------------------- atividade


    public Dao<Auto, Integer> getAutoDao() throws SQLException {
        if (autoDao == null) {
            autoDao = getDao(Auto.class);
        }
        return autoDao;
    }

    public void salvarPeca(Auto auto) {
        try {
            getAutoDao().create(auto);
            Log.e("banco", ""+getAutoDao().countOf());
        } catch (Exception e) {
            Log.e("banco", "Erro ao salvar pc");
        }
}

    public void removerPeca(Auto auto){
        try {
            getAutoDao().delete(auto);
        } catch (Exception e) {
            Log.e("banco", "Erro ao remover pc");
        }
    }

    public void updatePeca(Auto auto){
        try {
            getAutoDao().update(auto);
        } catch (Exception e) {
            Log.e("banco", "Erro ao atualizar pc");
        }
    }

    public List<Auto> buscarTodasPecas(){

        List<Auto> autos=null;
        try {
            return getAutoDao().queryBuilder().query();
        } catch (Exception e) {
            Log.e("banco", "Erro ao buscar pc AAAAAAAAA");
        }
        return new ArrayList<>();
    }

    public void salvarUsuario(Usuario usuario){
        try {
            getUsuarioDao().create(usuario);
        } catch (Exception e){
            Log.e("banco","Falha ao salvar usuario");
        }
    }
}