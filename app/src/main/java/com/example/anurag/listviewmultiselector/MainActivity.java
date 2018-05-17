package com.example.anurag.listviewmultiselector;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity" ;
    ListView list;
    ListViewAdapter listviewadapter;
    List<WorldPopulation> worldpopulationlist = new ArrayList<WorldPopulation>();
    String[] rank;
    String[] country;
    String[] population;
    int[] flag;
    private ArrayList list_item = new ArrayList<>();
    public int checkedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);

        rank = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        country = new String[]{"China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};

        population = new String[]{"1,354,040,000", "1,210,193,422",
                "315,761,000", "237,641,326", "193,946,886", "182,912,000",
                "170,901,000", "152,518,015", "143,369,806", "127,360,000"};

        flag = new int[]{R.drawable.football, R.drawable.cricket,
                R.drawable.basketball, R.drawable.cycling,
                R.drawable.football, R.drawable.volley, R.drawable.skating,
                R.drawable.basketball, R.drawable.rugby, R.drawable.hockey};

        for (int i = 0; i < rank.length; i++) {
            WorldPopulation worldpopulation = new WorldPopulation(flag[i],
                    rank[i], country[i], population[i]);
            worldpopulationlist.add(worldpopulation);
        }

        list = (ListView) findViewById(R.id.listview);

        listviewadapter = new ListViewAdapter(this, R.layout.listview_item,
                worldpopulationlist);

        list.setAdapter(listviewadapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                //Inflate the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.contextual_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                final int deleteSize = list_item.size();
                int itemId = item.getItemId();
//                if (itemId == R.id.item_delete) {
//
//                    for (Object ids : list_item) {
//                        // Make proper check, if needed, before deletion
//                        String whereDelId = DBOpenHelper.COL_ID + "=" + ids;
//                        int res = getContentResolver().delete(TripProvider.CONTENT_URI_START, whereDelId, null);
//                        if (res == -1) {
//                            Log.d(TAG, "onActionItemClicked: Delete Failed for ID = " + ids);
//                        }
//                    }
//
//                }
                Toast.makeText(getApplicationContext(), deleteSize + " Items deleted", Toast.LENGTH_SHORT).show();
                checkedCount = 0;
                list_item.clear();
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
//                displayDataList();
            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                checkedCount = list.getCheckedItemCount();

                //setting CAB title
                mode.setTitle(checkedCount + " Selected");

                //list_item.add(id);
                if (checked) {
                    list_item.add(id);     // Add to list when checked ==  true
                } else {
                    list_item.remove(id);
                }
            }

            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                final int checkedCount = list.getCheckedItemCount();
                mode.setTitle(checkedCount + " Selected");
                listviewadapter.toggleSelection(position);
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        SparseBooleanArray selected = listviewadapter
                                .getSelectedIds();
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                WorldPopulation selecteditem = listviewadapter
                                        .getItem(selected.keyAt(i));
                                listviewadapter.remove(selecteditem);
                            }
                        }
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main, menu);
                return true;
            }

            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                listviewadapter.removeSelection();
            }

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }
}