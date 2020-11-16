package com.example.gopoolit.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ApiResponse{

	@SerializedName("totalResults")
	private int totalResults;

	@SerializedName("articles")
	private ArrayList<ArticlesItem> articles;

	@SerializedName("status")
	private String status;

	public int getTotalResults(){
		return totalResults;
	}

	public ArrayList<ArticlesItem> getArticles(){
		return articles;
	}

	public String getStatus(){
		return status;
	}
}