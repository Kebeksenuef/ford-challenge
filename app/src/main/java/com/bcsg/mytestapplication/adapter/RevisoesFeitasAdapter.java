package com.bcsg.mytestapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dto.ItemRevisao;
import com.bcsg.mytestapplication.dto.Revisao;

import java.util.ArrayList;
import java.util.List;

public class RevisoesFeitasAdapter extends RecyclerView.Adapter<RevisoesFeitasAdapter.PersonViewHolder>{

    List<Revisao> revisoes;

    public RevisoesFeitasAdapter(List<Revisao> revisoes){
        this.revisoes = revisoes;
        if (this.revisoes == null){
            this.revisoes = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public RevisoesFeitasAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_revisao_feita, parent,false);
        RevisoesFeitasAdapter.PersonViewHolder pvh = new RevisoesFeitasAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.txtRevisao.setText(revisoes.get(position).getDescricao());
        holder.imageView.setImageResource(R.drawable.ic_correct);
    }

    @Override
    public int getItemCount() {
        return revisoes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder{
        TextView txtRevisao;
        AppCompatImageView imageView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRevisao = (TextView) itemView.findViewById(R.id.txtRevisaoDesc);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.img_revisao);
        }
    }



}
