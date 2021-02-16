package simple.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.os.FileUtils.*;
import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.io.FileUtils.writeLines;

public class MainActivity extends AppCompatActivity {
    ArrayList items;

    Button btnadd;
    EditText editem;
    RecyclerView rvitems;
    itemsAdaptor itemsAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd = findViewById(R.id.btnadd);
        editem = findViewById(R.id.editem);
        rvitems = findViewById(R.id.rvitems);


        loadItems();

        itemsAdaptor.OnLongClickListener onLongClickListener = new itemsAdaptor.OnLongClickListener(){

            @Override
            public void onItemLongClicked(int position) {
            items.remove(position);
            itemsAdaptor.notifyItemRemoved(position);
            Toast.makeText(getApplicationContext(),"item was removed", Toast.LENGTH_SHORT).show();
            saveItems();
            }
        };
        itemsAdaptor itemsAdaptor = new itemsAdaptor(items, onLongClickListener);
        rvitems.setAdapter(itemsAdaptor);
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editem.getText().toString();
                items.add(todoItem);
                itemsAdaptor.notifyItemInserted(items.size()-1);
                editem.setText("");
                Toast.makeText(getApplicationContext(),"item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    private void loadItems(){
        try{
        items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));
        }catch (IOException e){
            Log.e("mainAcitvity", "error reading items", e);
            items = new ArrayList<>();
        }

    }
    private void saveItems(){
        try {
            writeLines(getDataFile(), items);
        }catch (IOException e){
            Log.e("mainAcitvity", "error writing items", e);
        }
    }
}