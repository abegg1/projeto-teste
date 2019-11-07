package com.ftec.poa.myapp10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ListActivity {
    private ListaDAO listaDAO;
    private EditText edtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDAO = new ListaDAO(this);
        listaDAO.abrir();

        List<Item> itens = listaDAO.lerItens();
        ArrayAdapter<Item> adaptador = new ArrayAdapter<
                Item>(this,
                android.R.layout.simple_list_item_checked, itens);
        this.setListAdapter(adaptador);

        this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        edtDescricao = (EditText)findViewById(R.id.editText1);

        Toast.makeText(this, "Toque rapidamente em um item " +
                        "para marcá-lo. Toque novamente para excluí-lo.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        listaDAO.fechar();
        super.onPause();
    }

    public void onClick(View v) {
        String desc = edtDescricao.getText().toString();
        if (!desc.equals("")) {
            Item item = listaDAO.criarItem(desc);
            ArrayAdapter<Item> adaptador =
                    (ArrayAdapter<Item>)this.getListAdapter();
            adaptador.add(item);
            adaptador.notifyDataSetChanged();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v,
                                   final int position, long id) {
        if (!l.getCheckedItemPositions().get(position)) {
            ArrayAdapter<Item> adaptador =
                    (ArrayAdapter<Item>)this.getListAdapter();
            Item i = adaptador.getItem(position);
            listaDAO.removerItem(i);
            adaptador.remove(i);
            adaptador.notifyDataSetChanged();
        }
    }

}
