package com.example.docsscanning;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentListFragment extends Fragment {

    //Button document_list_recycler_view;
    RecyclerView document_list_recycler_view;
    public static  RecyclerView.Adapter mAdapter_document_lists ;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_document_list, container, false);
        return  v;
    }

    public void instantiate(View v){
        document_list_recycler_view =  v.findViewById(R.id.document_list_recycler_view);
        //recyclerView = v.findViewById(R.id.)
       // mAdapter_document_lists = ArrayAdapter<>
    }
}