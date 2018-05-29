package com.example.dmorales.firebaseprueba;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmorales.firebaseprueba.pojos_firebase.Curso;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoHolder>{

    List<Curso> cursos;
    Context contexto;
    FirebaseFirestore firebaseFirestore;

    public CursoAdapter(List<Curso> cursos, Context contexto, FirebaseFirestore firebaseFirestore) {
        this.cursos = cursos;
        this.contexto = contexto;
        this.firebaseFirestore = firebaseFirestore;

    }

    @Override
    public CursoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent, false);
        return new CursoHolder(view);
    }

    @Override
    public void onBindViewHolder(CursoHolder holder, final int position) {
        holder.txtEap.setText(cursos.get(position).getEap());
        holder.txtCiclo.setText(cursos.get(position).getCiclo()+"");
        holder.txtNombre.setText(cursos.get(position).getNombre());
        holder.cvCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contexto, "ccurso: " + cursos.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contexto, SilabusActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("curso", cursos.get(position).getId());
                contexto.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    static class CursoHolder extends RecyclerView.ViewHolder{
        TextView txtEap;
        TextView txtCiclo;
        TextView txtNombre;
        CardView cvCurso;
        public CursoHolder(View itemView) {
            super(itemView);

            cvCurso =  (CardView) itemView.findViewById(R.id.item_curso_cvCurso);
            txtEap = (TextView) itemView.findViewById(R.id.item_curso_txtEap);
            txtCiclo = (TextView) itemView.findViewById(R.id.item_curso_txtCiclo);
            txtNombre = (TextView) itemView.findViewById(R.id.item_curso_txtNombre);
        }
    }
}
