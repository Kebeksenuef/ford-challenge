package com.bcsg.mytestapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dto.ItemRevisao;
import com.bcsg.mytestapplication.dto.Modelo;

import java.util.ArrayList;
import java.util.List;

public class ItensManutencaoAdapter extends RecyclerView.Adapter<ItensManutencaoAdapter.PersonViewHolder> {

    List<ItemRevisao> itensRevisao;

    public ItensManutencaoAdapter(List<ItemRevisao> itensRevisao){
        this.itensRevisao = itensRevisao;
        if (this.itensRevisao == null){
            this.itensRevisao = new ArrayList<>();
        }
    }
    
    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_manutencao, parent,false);
        ItensManutencaoAdapter.PersonViewHolder pvh = new ItensManutencaoAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.cb.setChecked(false);
        holder.item.setText(itensRevisao.get(position).getDescricao());
    }

    @Override
    public int getItemCount() {
        return itensRevisao.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder{
        //CardView cv;
        TextView item;
        CheckBox cb;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            //cv = (CardView) itemView.findViewById(R.id.cv);
            item = (TextView)  itemView.findViewById(R.id.txtItem);
            cb = (CheckBox) itemView.findViewById(R.id.chbx);
        }
    }


}
