package com.example.docsscanning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docsscanning.RecyclerView.RecyclerView_DocumentList;

import java.util.ArrayList;
import java.util.List;

import MODEL.MODEL_DocmentList;

public class DocumentListFragment extends Fragment {

    RecyclerView document_list_recycler_view;
    public static  RecyclerView.Adapter mAdapter_document_lists  ;
    public static  List<MODEL_DocmentList> Model_docmentList = new ArrayList<>();;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DocumentListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DocumentListFragment newInstance(String param1, String param2) {
        DocumentListFragment fragment = new DocumentListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_document_list, container, false);
        MODEL_DocmentList model_docmentList = new MODEL_DocmentList("Divanshu","");
        Model_docmentList.add(model_docmentList);
        document_list_recycler_view =  v.findViewById(R.id.document_list_recycler_view);
        mAdapter_document_lists = new RecyclerView_DocumentList(getListData());
        document_list_recycler_view.setAdapter(mAdapter_document_lists);
        document_list_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter_document_lists.notifyDataSetChanged();
        instantiate(v);
        return  v;
    }

    public void instantiate(View v){

        //recyclerView = v.findViewById(R.id.)
       // mAdapter_document_lists = ArrayAdapter<>
    }
    //GET LIST
    private List<MODEL_DocmentList> getListData() {
        return Model_docmentList;
    }

    static class Viewmodel extends ArrayAdapter<String> {
        String[] name;
        String[] date;
        Context context;

        Viewmodel(@NonNull Context context, String[] name, String[] phone, String[] address ,int[] image) {
            super(context, R.layout.documentlistview,R.id.documentList_name,name);
            this.context = context;
            this.name = name;
            this.date  = phone;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder")
            View row = layoutInflater.inflate(R.layout.documentlistview,parent,false);
            TextView textviewname = row.findViewById(R.id.documentList_name);
            TextView textviewdate = row.findViewById(R.id.documentList_date);
            textviewname.setText(name[position]);
           // textviewdate.setText(phone[position]);
           // textviewaddress.setText(address[position]);
            return row;
        }
    }
}