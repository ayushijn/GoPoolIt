package com.example.gopoolit.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.gopoolit.R;
import com.example.gopoolit.Retrofit.ApiClient;
import com.example.gopoolit.Retrofit.GetDataService;
import com.example.gopoolit.adapters.RcAdapter;
import com.example.gopoolit.database.DBHelper;
import com.example.gopoolit.databinding.FragmentTabTwoBinding;
import com.example.gopoolit.model.ApiResponse;
import com.example.gopoolit.model.ArticlesItem;
import com.example.gopoolit.util.CheckInternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabTwoFragment extends Fragment {
    private FragmentTabTwoBinding binding;
    ProgressDialog progressDialog;
    RcAdapter adapter;
    private DBHelper mydb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_two, container, false);
        mydb = new DBHelper(getActivity());
        if (new CheckInternetConnection().isInternetConnected(getActivity())) {
            getData();
        } else {
            Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

        return binding.getRoot();
    }

    private void getData() {
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        GetDataService service = ApiClient.getRetrofitInstance().create(GetDataService.class);
        Call<ApiResponse> call = service.getDataList("bitcoin", "2020-09-22", "publishedAt", "c91df5738c174ae7bc9d28691c542966");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("ok")) {
                    if (response.body() != null && response.body().getArticles() != null) {
                        mydb.insertContact(response.body().getArticles());
                        getDataFromDb();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromDb() {
        generateDataList(mydb.getAllContacts());
    }

    private void generateDataList(ArrayList<ArticlesItem> dataList) {
        adapter = new RcAdapter(this.getActivity(), dataList);
        binding.recyclerView.setAdapter(adapter);

    }
}