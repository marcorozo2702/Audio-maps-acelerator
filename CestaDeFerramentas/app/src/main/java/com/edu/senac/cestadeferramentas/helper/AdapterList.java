package com.edu.senac.cestadeferramentas.helper;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.model.Produto;

import java.util.List;

public class AdapterList extends BaseAdapter{

    private final List<Produto> produtos;
    private final Activity activity;

    public AdapterList(List<Produto> produtos, Activity activity){
        this.produtos = produtos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.produto_item,parent,false);
        Produto produto = produtos.get(position);
        TextView txNome = view.findViewById(R.id.nomeProduto);

        txNome.setText(produto.getNome());

        return view;
    }

    public void atualizarProdutos(List<Produto> novosProdutos){
        this.produtos.clear();
        this.produtos.addAll(novosProdutos);
        notifyDataSetChanged();
    }
}