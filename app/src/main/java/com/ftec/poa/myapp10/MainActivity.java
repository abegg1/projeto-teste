package com.ftec.poa.myapp10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ListActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private ListaDAO listaDAO;
    private EditText edtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Toolbar Title");
        getSupportActionBar().setSubtitle("Toolbar Subtitle");

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Infla o menu com os botões da action bar
            getMenuInflater().inflate(R.menu.menu_main, menu);

            // SearchView
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnQueryTextListener(onSearch());

            // ShareActionProvider
            MenuItem shareItem = menu.findItem(R.id.action_share);
            ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
            share.setShareIntent(getDefaultIntent());

            return true;
        }
        // Intent que define o conteúdo que será compartilhado
        private Intent getDefaultIntent() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/*");
            intent.putExtra(Intent.EXTRA_TEXT, "Texto para compartilhar");
            return intent;
        }

        private SearchView.OnQueryTextListener onSearch() {
            return new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextSubmit(String query) {
                    toast("Buscar o texto: " + query);
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            };
        }
        /*
    Método para indicar o que deve ser feito ao se clicar
    em algum item do menu
     */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_search) {
                toast("Clicou em Search!");
                return true;
            } else if (id == R.id.action_refresh) {
                toast("Clicou em Refresh!");
                return true;
            }else if (id == R.id.action_settings) {
                toast("Clicou em Settings!");
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void toast(String msg) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

        listaDAO = new ListaDAO(this);
        listaDAO.abrir();

        List<Item> itens = listaDAO.lerItens();
        ArrayAdapter<Item> adaptador = new ArrayAdapter<
                Item>(this,
                android.R.layout.simple_list_item_checked, itens);
        this.setListAdapter(adaptador);

        this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        edtDescricao = (EditText)findViewById(R.id.editText);

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
