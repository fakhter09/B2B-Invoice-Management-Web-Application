package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.crud.CrudOperation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		StringBuilder str = new StringBuilder();
		BufferedReader br = null;
		JsonParser par = new JsonParser();
		String res = "";
		PrintWriter out = response.getWriter();
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
			JsonObject jsonBody = (JsonObject) par.parse(body);
			JsonArray arr = (JsonArray) jsonBody.get("sl_no").getAsJsonArray();
			ArrayList<Integer> sl_no = new ArrayList<Integer>();

			for (int i = 0; i < arr.size(); ++i) {
				sl_no.add(arr.get(i).getAsInt());
			}

			try {
				CrudOperation co = new CrudOperation();
				int numberOfRowDeleted = co.deleteCustomer(sl_no);

				res = "{Sucess:\"Successfully deleted " + numberOfRowDeleted + " invoice(s)\"}";
				JsonObject resp = (JsonObject) par.parse(res);
				response.setStatus(200);
				out.print(resp);
			} catch (Exception e) {
				response.setStatus(400);
				res = "{Error:\"" + e.getLocalizedMessage() + "\"}";
				JsonObject resp = (JsonObject) par.parse(res);
				out.print(resp);
			}
		} catch (Exception e) {
			response.setStatus(500);
			res = "{Error:\"Internal Server Error\"}";
			JsonObject resp = (JsonObject) par.parse(res);
			out.print(resp);
		}
	}

	protected void doTrace(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}