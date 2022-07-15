package com.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.pojo.Customer;

public class CrudOperation {

	DataBaseConnection dbc = new DataBaseConnection();

	// Fetch_Data

	public int getSlno() {
		int max = 0;
		try {

			Connection connect = dbc.getconnection();
			String sqlQuery = "SELECT MAX(sl_no) AS sl_no from winter_internship";
			PreparedStatement p = connect.prepareStatement(sqlQuery);

			ResultSet rs = p.executeQuery();

			rs.next();
			max = rs.getInt("sl_no");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return max;
	}

	public ArrayList<Customer> FetchCustomer() {

		ArrayList<Customer> customerList = new ArrayList<Customer>();

		try {
			Connection connect = dbc.getconnection();
			if (connect != null) {
				String sqlQuery = "select * from winter_internship";

				PreparedStatement p = connect.prepareStatement(sqlQuery);
				ResultSet rs = p.executeQuery();

				while (rs.next()) {
					Customer c = new Customer();
					c.setSl_no(rs.getInt("sl_no"));
					c.setBusiness_code(rs.getString("business_code"));
					c.setCust_number(rs.getInt("cust_number"));
					c.setClear_date(rs.getString("clear_date"));
					c.setBuisness_year(rs.getString("buisness_year"));
					c.setDoc_id(rs.getString("doc_id"));
					c.setPosting_date(rs.getString("posting_date"));
					c.setDocument_create_date(rs.getString("document_create_date"));
					c.setDocument_create_date1(rs.getString("document_create_date1"));
					c.setDue_in_date(rs.getString("due_in_date"));
					c.setInvoice_currency(rs.getString("invoice_currency"));
					c.setDocument_type(rs.getString("document_type"));
					c.setPosting_id(rs.getInt("posting_id"));
					c.setArea_business(rs.getString("area_business"));
					c.setTotal_open_amount(rs.getDouble("total_open_amount"));
					c.setBaseline_create_date(rs.getString("baseline_create_date"));
					c.setCust_payment_terms(rs.getString("cust_payment_terms"));
					c.setInvoice_id(rs.getInt("invoice_id"));
					c.setIsOpen(rs.getInt("isOpen"));
					c.setAging_bucket(rs.getString("aging_bucket"));
					c.setIs_deleted(rs.getInt("is_deleted"));

					customerList.add(c);
				}
				p.close();
				connect.close();
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}

	// Add_Data
	public int InsertCustomer(Customer c) {
		DataBaseConnection dbc = new DataBaseConnection();
		int numberOfRowUpdated = 0;
		try {
			Connection connect = dbc.getconnection();

			if (connect != null) {
				String sqlQuery = "insert into winter_internship(`sl_no`,`business_code`,`cust_number`,`clear_date`,`buisness_year`,`doc_id`,`posting_date`,`document_create_date`,`due_in_date`,`invoice_currency`,`document_type`,`posting_id`,`total_open_amount`,`baseline_create_date`,`cust_payment_terms`,`invoice_id`) values"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

				PreparedStatement p = connect.prepareStatement(sqlQuery);

				p.setInt(1, c.getSl_no());
				p.setString(2, c.getBusiness_code());
				p.setInt(3, c.getCust_number());
				p.setString(4, c.getClear_date());
				p.setString(5, c.getBuisness_year());
				p.setString(6, c.getDoc_id());
				p.setString(7, c.getPosting_date());
				p.setString(8, c.getDocument_create_date());
				p.setString(9, c.getDue_in_date());
				p.setString(10, c.getInvoice_currency());
				p.setString(11, c.getDocument_type());
				p.setInt(12, c.getPosting_id());
				p.setDouble(13, c.getTotal_open_amount());
				p.setString(14, c.getBaseline_create_date());
				p.setString(15, c.getCust_payment_terms());
				p.setInt(16, c.getInvoice_id());

				numberOfRowUpdated = p.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return numberOfRowUpdated;
	}

	// Update_Data
	public int updateCustomer(String cust_payment_terms, String invoice_currency, int sl_no) {
		DataBaseConnection dbc = new DataBaseConnection();
		int numberOfRowUpdated = 0;
		try {
			Connection connect = dbc.getconnection();
			StringBuilder builder = new StringBuilder();

			if (!(cust_payment_terms.equals(""))) {
				builder.append("cust_payment_terms = \"" + cust_payment_terms + "\",");
			}

			if (!(invoice_currency.equals(""))) {
				builder.append("invoice_currency = \"" + invoice_currency + "\",");
			}

			String updatedList = builder.substring(0, builder.length() - 1).toString();
			String sqlQuery = "update winter_internship set " + updatedList + " where sl_no = " + sl_no;

			PreparedStatement p = connect.prepareStatement(sqlQuery);
			numberOfRowUpdated = p.executeUpdate();
			p.close();
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numberOfRowUpdated;
	}

	// Delete_Data
	public int deleteCustomer(ArrayList<Integer> sl_no) {
		DataBaseConnection dbc = new DataBaseConnection();
		int numberOfRowUpdated = 0;
		try {
			Connection connect = dbc.getconnection();
			StringBuilder build = new StringBuilder();
			for (int i = 0; i < sl_no.size(); ++i) {
				build.append("?,");
			}

			String params = build.substring(0, build.length() - 1).toString();
			String sqlQuery = "delete from winter_internship where sl_no in (" + params + ");";
			PreparedStatement p = connect.prepareStatement(sqlQuery);

			for (int i = 0; i < sl_no.size(); ++i) {
				p.setInt(i + 1, sl_no.get(i));
			}

			numberOfRowUpdated = p.executeUpdate();

			p.close();
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numberOfRowUpdated;
	}

	// Search_Data
	public ArrayList<Customer> Search(int cust_number) {
		DataBaseConnection dbc = new DataBaseConnection();

		ArrayList<Customer> customerList = new ArrayList<Customer>();

		try {
			Connection connect = dbc.getconnection();

			if (connect != null) {
				String sqlQuery = "select * from winter_internship where cust_number = ?";

				PreparedStatement p = connect.prepareStatement(sqlQuery);

				p.setInt(1, cust_number);

				ResultSet rs = p.executeQuery();
				while (rs.next()) {
					Customer c = new Customer();
					c.setSl_no(rs.getInt("sl_no"));
					c.setBusiness_code(rs.getString("business_code"));
					c.setCust_number(rs.getInt("cust_number"));
					c.setClear_date(rs.getString("clear_date"));
					c.setBuisness_year(rs.getString("buisness_year"));
					c.setDoc_id(rs.getString("doc_id"));
					c.setPosting_date(rs.getString("posting_date"));
					c.setDocument_create_date(rs.getString("document_create_date"));
					c.setDocument_create_date1(rs.getString("document_create_date1"));
					c.setDue_in_date(rs.getString("due_in_date"));
					c.setInvoice_currency(rs.getString("invoice_currency"));
					c.setDocument_type(rs.getString("document_type"));
					c.setPosting_id(rs.getInt("posting_id"));
					c.setArea_business(rs.getString("area_business"));
					c.setTotal_open_amount(rs.getDouble("total_open_amount"));
					c.setBaseline_create_date(rs.getString("baseline_create_date"));
					c.setCust_payment_terms(rs.getString("cust_payment_terms"));
					c.setInvoice_id(rs.getInt("invoice_id"));
					c.setIsOpen(rs.getInt("isOpen"));
					c.setAging_bucket(rs.getString("aging_bucket"));
					c.setIs_deleted(rs.getInt("is_deleted"));

					customerList.add(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerList;
	}
	

	// Advanced_Search_Data
	public ArrayList<Customer> SearchCustomer(String doc_id, int invoice_id, String buisness_year, int cust_number) {
		DataBaseConnection dbc = new DataBaseConnection();

		ArrayList<Customer> customerList = new ArrayList<Customer>();

		try {
			Connection connect = dbc.getconnection();

			if (connect != null) {
				String sqlQuery = "select * from winter_internship where doc_id = ? and invoice_id = ? and cust_number = ? and buisness_year = ?";

				PreparedStatement p = connect.prepareStatement(sqlQuery);

				p.setString(1, doc_id);
				p.setInt(2, invoice_id);
				p.setInt(3, cust_number);
				p.setString(4, buisness_year);

				ResultSet rs = p.executeQuery();
				while (rs.next()) {
					Customer c = new Customer();
					c.setSl_no(rs.getInt("sl_no"));
					c.setBusiness_code(rs.getString("business_code"));
					c.setCust_number(rs.getInt("cust_number"));
					c.setClear_date(rs.getString("clear_date"));
					c.setBuisness_year(rs.getString("buisness_year"));
					c.setDoc_id(rs.getString("doc_id"));
					c.setPosting_date(rs.getString("posting_date"));
					c.setDocument_create_date(rs.getString("document_create_date"));
					c.setDocument_create_date1(rs.getString("document_create_date1"));
					c.setDue_in_date(rs.getString("due_in_date"));
					c.setInvoice_currency(rs.getString("invoice_currency"));
					c.setDocument_type(rs.getString("document_type"));
					c.setPosting_id(rs.getInt("posting_id"));
					c.setArea_business(rs.getString("area_business"));
					c.setTotal_open_amount(rs.getDouble("total_open_amount"));
					c.setBaseline_create_date(rs.getString("baseline_create_date"));
					c.setCust_payment_terms(rs.getString("cust_payment_terms"));
					c.setInvoice_id(rs.getInt("invoice_id"));
					c.setIsOpen(rs.getInt("isOpen"));
					c.setAging_bucket(rs.getString("aging_bucket"));
					c.setIs_deleted(rs.getInt("is_deleted"));

					customerList.add(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerList;
	}

}