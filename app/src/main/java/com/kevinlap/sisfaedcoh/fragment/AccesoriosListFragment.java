

/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.kevinlap.sisfaedcoh.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevinlap.sisfaedcoh.R;
import com.kevinlap.sisfaedcoh.activity.MainActivity;
import com.kevinlap.sisfaedcoh.adapter.AccesoriosAdapter;
import com.kevinlap.sisfaedcoh.constants.Constans;
import com.kevinlap.sisfaedcoh.database.DatabaseHelper;
import com.kevinlap.sisfaedcoh.generics.base.MyApplication;
import com.kevinlap.sisfaedcoh.model.Accesorio;
import com.kevinlap.sisfaedcoh.session.SessionManager;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 */

public class AccesoriosListFragment extends Fragment implements MainActivity.ItemScanned {

    public static RecyclerView mRecyclerView;
    private AccesoriosAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;
    ArrayList<Object> productArrayList;
    private RelativeLayout mainLayout, emptyLayout;
    DatabaseHelper db;
    private SessionManager session;

    ArrayList<Accesorio> accesorioArrayList;

    public AccesoriosListFragment() {

    }

    private static AccesoriosListFragment mInstance;

    public static Activity activityFragmentProduct;
    public Context contextFragment;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mInstance = this;
        super.onActivityCreated(savedInstanceState);
    }

    public static synchronized AccesoriosListFragment getInstance() {
        return mInstance;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prduct_list, container, false);

        session = new SessionManager(getContext());


        contextFragment = getContext();
        mRecyclerView = view.findViewById(R.id.product_list_recycler_view);
        swipeRefresh = view.findViewById(R.id.swipe_refresh_layout);
        mainLayout = view.findViewById(R.id.main_layout);
        emptyLayout = view.findViewById(R.id.empty_layout);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.green), getResources().getColor(R.color.blue), getResources().getColor(R.color.orange));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAccesorios();
            }
        });

        accesorioArrayList = new ArrayList<>();

        activityFragmentProduct = getActivity();
//        loadProductList();
        loadAccesorios();
        return view;
    }

    private void loadAccesorios() {
        accesorioArrayList.clear();
        if (session.isLoggedIn()) {

            // making volley's json request
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constans.obtenerIp(contextFragment) + Constans.OBTENERACCESORIOS
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Type type = new TypeToken<ArrayList<Accesorio>>() {
                    }.getType();
                    accesorioArrayList = new Gson().fromJson(response.toString(), type);

                    if (!accesorioArrayList.isEmpty()) {


                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setHasFixedSize(true);

                        mAdapter = new AccesoriosAdapter(getContext(), accesorioArrayList);

                        mRecyclerView.setAdapter(mAdapter);
                        swipeRefresh.setRefreshing(false);
                        emptyLayout.setVisibility(View.GONE);


                    } else {
                        emptyLayout.setVisibility(View.VISIBLE);
                        swipeRefresh.setRefreshing(false);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swipeRefresh.setRefreshing(false);
                    emptyLayout.setVisibility(View.VISIBLE);
                }
            });


            MyApplication.getInstance().addToRequestQueue(stringRequest);

        } else {
            //mRecyclerView.setAdapter(null);

            accesorioArrayList.clear();

            mAdapter = new AccesoriosAdapter(getContext(), accesorioArrayList);
            mAdapter.notifyDataSetChanged();

            emptyLayout.setVisibility(View.VISIBLE);
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getActivity(), "Inicie Sesion para ver los Registros ", Toast.LENGTH_SHORT).show();

        }
    }


//    private void loadProductList() {
//        db= new DatabaseHelper(getContext());
//        productArrayList = db.getAllProduct();
////        if(Utils.isNetworkAvailable(getContext())){
////            //addNativeExpressAd();
////        }
//
//        if(!productArrayList.isEmpty()){
//
//
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            mRecyclerView.setHasFixedSize(true);
//
//            mAdapter = new ProductAdapter(getContext(), productArrayList);
//
//            mRecyclerView.setAdapter(mAdapter);
//            swipeRefresh.setRefreshing(false);
//            emptyLayout.setVisibility(View.GONE);
//
//
//
//        }
//        else{
//            emptyLayout.setVisibility(View.VISIBLE);
//            swipeRefresh.setRefreshing(false);
//        }
//
//    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loadAccesorios();
    }

    @Override
    public void itemUpdated() {
        loadAccesorios();
    }
}
