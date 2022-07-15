package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

@WebServlet("/AdvanceSearch")
public class AdvanceSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

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
				int invoice_id = jsonBody.get("invoice_id").getAsInt();
				int cust_number = jsonBody.get("cust_number").getAsInt();
				String doc_id = jsonBody.get("doc_id").getAsString();
				String buisness_year = jsonBody.get("buisness_year").getAsString();

				NewList = cd.SearchCustomer(doc_id, invoice_id, buisness_year, cust_number);
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
