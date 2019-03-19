package com.example.xpeng.greendaodemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private View addNoteButton;

    private CollectionDao mCollectionDao;
    private Query<Collection> mCollectionQuery;
    private CollectionAdapter mCollectionAdapter;

    RecyclerView recyclerView;

    private int count;

    CollectionAdapter.CollectionClickListener mCollectionClickListener = new CollectionAdapter.CollectionClickListener() {
        @Override
        public void onCollectionClick(int position) {
            Collection collection = mCollectionAdapter.getCollection(position);
            Long collectionId = collection.getId();

            mCollectionDao.deleteByKey(collectionId);
            Log.d("DaoExample", "Deleted collection, ID: " + collectionId);
            updateNotes();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        DaoSession daoSession = ((App)getApplication()).getDaoSession();
        mCollectionDao = daoSession.getCollectionDao();

        mCollectionQuery = mCollectionDao.queryBuilder().orderAsc(CollectionDao.Properties.Id).build();
        updateNotes();
    }

    protected void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCollectionAdapter = new CollectionAdapter(mCollectionClickListener);
        recyclerView.setAdapter(mCollectionAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        addNoteButton.setEnabled(false);

        mEditText = (EditText)findViewById(R.id.editTextNote);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    addCollection();
                    return true;
                }
                return false;
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void updateNotes(){
        List<Collection> collections = mCollectionQuery.list();
        mCollectionAdapter.setCollections(collections);
        mCollectionAdapter.notifyDataSetChanged();
    }

    private void addCollection(){
        String collectionName = mEditText.getText().toString();
        String uuid = "S000" + count;
        String type = "";
        switch (count%4){
            case 0:
                type = "room";
                break;
            case 1:
                type = "floor";
                break;
            case 2:
                type = "building";
                break;
            case 3:
                type = "project";
                break;
                default:
        }
        Collection collection = new Collection();
        collection.setName(collectionName);
        collection.setUuid(uuid);
        collection.setType(type);
        mCollectionDao.insert(collection);
        Log.d("DaoExample", "Inserted new collection, ID: " + collection.getId());

        updateNotes();
    }

    public void onAddButtonClick(View view) {
        addCollection();
    }
}
