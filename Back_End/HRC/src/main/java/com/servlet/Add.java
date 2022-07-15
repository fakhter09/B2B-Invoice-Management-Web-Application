package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.crud.CrudOperation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pojo.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder();
		BufferedReader br = null;
		CrudOperation co = new CrudOperation();
		JsonParser par = new JsonParser();
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
			
			JsonObject jsonBody = (JsonObject)par.parse(body);
			
			Customer c = new Customer();
			
			int slno=co.getSlno();
			System.out.println(slno);
			
			c.setSl_no(slno+1);
			c.setBusiness_code(jsonBody.get("business_code").getAsString());
			c.setCust_number(jsonBody.get("cust_number").getAsInt());
			c.setClear_date(jsonBody.get("clear_date").getAsString());
			c.setBuisness_year(jsonBody.get("buisness_year").getAsString());
			c.setDoc_id(jsonBody.get("doc_id").getAsString());
			c.setPosting_date(jsonBody.get("posting_date").getAsString());
			c.setDocument_create_date(jsonBody.get("document_create_date").getAsString());
			c.setDue_in_date(jsonBody.get("due_in_date").getAsString());
			c.setInvoice_currency(jsonBody.get("invoice_currency").getAsString());
			c.setDocument_type(jsonBody.get("document_type").getAsString());
			c.setPosting_id(jsonBody.get("posting_id").getAsInt());
			c.setTotal_open_amount(jsonBody.get("total_open_amount").getAsDouble());
			c.setBaseline_create_date(jsonBody.get("baseline_create_date").getAsString());
			c.setCust_payment_terms(jsonBody.get("cust_payment_terms").getAsString());
			c.setInvoice_id(jsonBody.get("invoice_id").getAsInt());
			
			try {
				int numRowEffected = co.InsertCustomer(c);
				//System.out.println(numRowEffected);
				res = "{Sucess:\"Sucessfully added " + numRowEffected + " invoice(s)\"}";
				
				response.setStatus(201);
				JsonObject finalRes = (JsonObject) par.parse(res);
				out.print(finalRes);
				
			} catch(Exception e) {
				e.printStackTrace();
				response.setStatus(400);
				res = "{Error:\"" + e.getLocalizedMessage() + "\"}";
				JsonObject finalRes = (JsonObject) par.parse(res);
				out.print(finalRes);
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			res = "{Error:\"Internal Server Error\"}";
			JsonObject finalRes = (JsonObject) par.parse(res);
			out.print(finalRes);
		}
	}


}