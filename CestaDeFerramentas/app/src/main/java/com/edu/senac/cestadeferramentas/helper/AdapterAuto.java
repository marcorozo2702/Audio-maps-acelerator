package com.edu.senac.cestadeferramentas.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.model.Auto;
import com.edu.senac.cestadeferramentas.model.Produto;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterAuto extends BaseAdapter {

        private final List<Auto> autoList;
        private final Activity activity;

        public AdapterAuto(List<Auto> autoList, Activity activity){
            this.autoList = autoList;
            this.activity = activity;
        }

    @Override
    public int getCount(){return autoList.size();}

    @Override
    public Object getItem(int position) {
        return autoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return autoList.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
            View view = activity.getLayoutInflater().inflate(R.layout.auto_item,parent,false);
            Auto auto = autoList.get(position);
        TextView txNome= view.findViewById(R.id.nomePeca);
        TextView referencia= view.findViewById(R.id.referenciaPeca);
        TextView quantidade=view.findViewById(R.id.quantidadePeca);
        TextView unidade= view.findViewById(R.id.unidadePeca);
        TextView valor= view.findViewById(R.id.valorPeca);
        ImageView imagem=view.findViewById(R.id.imagemPeca);

        byte[] decodedString = Base64.decode(auto.getImagem(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imagem.setImageBitmap(decodedByte);
        txNome.setText(auto.getNome());
        referencia.setText(auto.getReferencia());
        quantidade.setText(Integer.toString(auto.getQuantidade()));
        if(auto.getUnidade() == 0){
            unidade.setText("JG");
        } if (auto.getUnidade()==1){
            unidade.setText("PC");
        } else {
            unidade.setText("CX");
        }
        valor.setText(Float.toString(auto.getValor()));

        return view;
    }

    public void atualizarAutos(List<Auto> novosAutos){
        this.autoList.clear();
        this.autoList.addAll(novosAutos);
        notifyDataSetChanged();
    }
}
