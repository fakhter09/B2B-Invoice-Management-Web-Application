package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.crud.CrudOperation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pojo.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Customer> NewList = new ArrayList<Customer>();
		String final_cust_list = "";
		StringBuilder str = new StringBuilder();
		BufferedReader br = null;
		CrudOperation cd = new CrudOperation();
		JsonParser parser = new JsonParser();
		try {

			response.getWriter();
			response.setContentType("application/json");
			try {
				InputStream inputStream = request.getInputStream();
				br = new BufferedReader(new InputStreamReader(inputStream));
				char[] ch = new char[128];
				int bytesRead = -1;

				while ((bytesRead = br.read(ch)) > 1) {
					str.append(ch, 0, bytesRead);
				}

				String body = str.toString();

				JsonObject jsonBody = (JsonObject) parser.parse(body);
				int cust_number = jsonBody.get("cust_number").getAsInt();

				NewList = cd.Search(cust_number);
				Gson gs = new Gson();
				final_cust_list = gs.toJson(NewList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.setContentType("application/json");
			response.getWriter().print(final_cust_list);

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().print(final_cust_list);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
