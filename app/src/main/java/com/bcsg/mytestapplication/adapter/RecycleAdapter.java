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
import com.bcsg.mytestapplication.dao.AzureConnection;
import com.bcsg.mytestapplication.dto.ItemRevisao;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.PersonViewHolder> {

    List<ItemRevisao> itemRevisaos;

    public RecycleAdapter(List<ItemRevisao> itemRevisaos){
        this.itemRevisaos = itemRevisaos;
    }

    //método é chamado quando o ViewHolder personalizado precisa ser inicializado.
    //especifica o layout que cada item do RecyclerView deve usar
    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent,false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.item.setText(AzureConnection.consutlarItens().get(position).getDescricao());
        holder.cb.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return AzureConnection.consutlarItens().size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView item;
        CheckBox cb;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            item = (TextView)  itemView.findViewById(R.id.txtV);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
        }
    }

}
