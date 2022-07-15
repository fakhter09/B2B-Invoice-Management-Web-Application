package com.servlet;
import java.io.IOException;
import java.util.ArrayList;
import com.crud.CrudOperation;
import com.google.gson.Gson;
import com.pojo.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Fetch")
public class Fetch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CrudOperation cd = new CrudOperation();
		
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		String finalCustomerList = "";
		response.setContentType("application/json");
		try {
			customerList = cd.FetchCustomer();
			Gson gs = new Gson();
			finalCustomerList = gs.toJson(customerList);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		response.getWriter().print(finalCustomerList);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}