import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table  class='gridtable'><tr><td>Customer Name:</td><td>");
		pw.print(userName);
		pw.print("</td></tr>");
		// for each order iterate and display the order name price
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<tr><td> Product Purchased:</td><td style='font-weight:bold';>");
			pw.print(oi.getMatchName()+"</td></tr>");
			pw.print("<tr><td> Seat Number:</td><td>");
			pw.print(oi.getSeat()+"</td></tr>");
			pw.print("<tr><td> Row:</td><td>");
			pw.print(oi.getRow()+"</td></tr>");
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getZoneName()+"'>");
			pw.print("<td>Product Price:</td><td>"+ oi.getPrice());
			pw.print("</td></tr>");
		}
		pw.print("<tr><td>");
        pw.print("Total Order Cost</td><td style='font-weight:bold'>"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
		pw.print("</td></tr></table><br><br><table><tr></tr><tr></tr>");	
		pw.print("<tr style='border:white;font-weight:bold;padding:10px'><td style='border:white;font-weight:bold;padding:4px'>");
     	pw.print("Credit/accountNo</td>");
		pw.print("<td style='border:white;font-weight:bold;padding:10px;'><input type='text' name='creditCardNo'  style='background-color: white;color: black;'>");
		pw.print("</td></tr>");
		pw.print("<tr style='border:white;font-weight:bold;padding:10px'><td style='border:white;background-color: white;color: black;padding:10px;'>");
     	pw.print("Email</td>");
		pw.print("<td style='border:white;background-color: white;color: black;padding:10px;'><input type='text' name='email' style='background-color: white;color: black;'>");
		pw.print("</td></tr>");
		pw.print("<tr style='border:white;font-weight:bold;padding:10px'><td style='border:white;background-color: white;color: black;padding:10px'>");
	    pw.print("Customer Address</td>");
		pw.print("<td style='border:white;background-color: white;color: black;padding:10px;'><input type='text' name='userAddress' style='background-color: white;color: black;'>");
        pw.print("</td></tr>");
		pw.print("<tr style='border:white;font-weight:bold;padding:10px'><td colspan='2' style='border:white;padding:10px;'>");
		pw.print("<input type='submit' name='submit' class='btnbuy'>");
        pw.print("</td></tr></table></form>");
		pw.print("</div></div></div><div class='clear'></div>");		
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
