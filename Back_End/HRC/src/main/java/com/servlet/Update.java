package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.crud.CrudOperation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		StringBuilder str = new StringBuilder();
		BufferedReader br = null;
		CrudOperation cd = new CrudOperation();
		JsonParser parser = new JsonParser();
		String res = "";
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		try {
			InputStream inputStream = request.getInputStream();
			br = new BufferedReader(new InputStreamReader(inputStream));
			char[] ch = new char[128];
			int bytesRead = -1;
			
			while((bytesRead = br.read(ch)) > 1){
				str.append(ch, 0, bytesRead);
			}
			
			String body = str.toString();
			JsonObject jsonBody = (JsonObject)parser.parse(body);
			int sl_no = jsonBody.get("sl_no").getAsInt();
			String cust_payment_terms = jsonBody.get("cust_payment_terms").getAsString();
			String invoice_currency = jsonBody.get("invoice_currency").getAsString();
			
			try {
				int numberOfRowUpdated = cd.updateCustomer(cust_payment_terms, invoice_currency, sl_no);
				res = "{Sucess:\"Successfully updated " + numberOfRowUpdated + " invoice(s)\"}";
				JsonObject finalRes = (JsonObject) parser.parse(res);
				response.setStatus(200);
				out.print(finalRes);
			} catch(Exception e) {
				e.printStackTrace();
				response.setStatus(400);
				res = "{Error:\"" + e.getLocalizedMessage() + "\"}";
				JsonObject finalRes = (JsonObject) parser.parse(res);
				out.print(finalRes);
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			res = "{Error:\"Internal Server Error\"}";
			JsonObject finalRes = (JsonObject) parser.parse(res);
			out.print(finalRes);
		}
	}

}