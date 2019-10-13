package br.com.fiap.flan2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.fiap.flan2.R;
import br.com.fiap.flan2.dto.ItemRevisao;

public class ItensManutencaoAdapter extends RecyclerView.Adapter<ItensManutencaoAdapter.PersonViewHolder> {

    List<ItemRevisao> itensRevisao;
    Set<ItemRevisao> itensSelecionados;

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
        ItemRevisao item = itensRevisao.get(position);

        holder.item.setText(item.getDescricao());
        holder.cb.setChecked(false);
        holder.cb.setTag(item);

        holder.cb.setOnClickListener(x -> {
            if (holder.cb.isChecked()) {
                itensSelecionados.add(item);
            } else {
                itensSelecionados.remove(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itensRevisao.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public Set<ItemRevisao> getItensSelecionados() {
        return itensSelecionados;
    }

    public void setItensSelecionados(Set<ItemRevisao> itensSelecionados) {
        this.itensSelecionados = itensSelecionados;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder{
        //CardView cv;
        TextView item;
        CheckBox cb;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            item = (TextView)  itemView.findViewById(R.id.txtItem);
            cb = (CheckBox) itemView.findViewById(R.id.chbx);
        }
    }
}
