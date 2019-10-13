package br.com.fiap.flan2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.flan2.R;
import br.com.fiap.flan2.dto.Manutencao;

public class ManutencoesFeitasAdapter extends RecyclerView.Adapter<ManutencoesFeitasAdapter.ManutencaoViewHolder>{

    private List<Manutencao> manutencoes;

    private final SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

    public ManutencoesFeitasAdapter(List<Manutencao> manutencoes){
        this.manutencoes = manutencoes;
        if (this.manutencoes == null){
            this.manutencoes = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public ManutencoesFeitasAdapter.ManutencaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_manutencao_feita, parent,false);
        ManutencoesFeitasAdapter.ManutencaoViewHolder pvh = new ManutencoesFeitasAdapter.ManutencaoViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ManutencaoViewHolder holder, int position) {
        Manutencao manutencao = manutencoes.get(position);

        holder.txtManutencaoDescricao.setText(manutencao.getDescricao());
        holder.txtManutencaoData.setText(formatador.format(manutencao.getDataRealizacao()));
    }

    @Override
    public int getItemCount() {
        return manutencoes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ManutencaoViewHolder extends RecyclerView.ViewHolder{
        TextView txtManutencaoDescricao;
        TextView txtManutencaoData;

        public ManutencaoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtManutencaoDescricao = (TextView) itemView.findViewById(R.id.txtManutencaoDescricao);
            txtManutencaoData = (TextView) itemView.findViewById(R.id.txtManutencaoData);
        }
    }
}
