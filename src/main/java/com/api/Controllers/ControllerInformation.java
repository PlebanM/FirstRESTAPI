package com.api.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;

@WebServlet(urlPatterns = "/info", loadOnStartup = 1)
public class ControllerInformation extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().println("<html><body>" +
                "REST API OPTIONS:<BR><BR>" +
                "localhost:8080<BR>" +
                "<BR>TIME<BR>" +
                "<ul>" +
                "<li>/api/time/get - get all players times</li>" +
                "<li>/api/time/get/{id} - get player times by id</li>" +
                "<li>/api/time/contest/{id} - get players and times in one contest by  contest id</li>" +
                "<li>/api/time/new - POST -   create new time </li>" +
                "</ul>" +
                "<BR>PLAYERS<BR>" +
                "<ul>" +
                "<li>/api/players/get - get all players" +
                "<li>/api/players/get/{id} - get player by id" +
                "<li>/api/players/new - POST - add new player " +
                "<li>/api/players/update/{id} - update player by his id" +
                "<li>/api/players/get/best/ - get best players (by gender and contest year)" +
                "<li>/api/players/get/best/ - get best players (by gender and contest year)" +
                "<li>/api/players/get/best/age/{sign} - get best players (under/above age and contest year)" +
                "</ul>" +
                "<BR>CONTESTS<BR>" +
                "<ul>" +
                "<li>/api/contests/get/ - get all contests" +
                "<li>/api/contests/get/{id} - get contest by id" +
                "</ul>" +

                "</body></html>");

    }


}
