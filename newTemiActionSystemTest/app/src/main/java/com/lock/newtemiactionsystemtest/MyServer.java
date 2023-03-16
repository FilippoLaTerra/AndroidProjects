package com.lock.newtemiactionsystemtest;

import android.util.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyServer {

    public static final int SERVERPORT = 1234;


    Handler handler = new AbstractHandler()
    {
        //@Override
        public void handle(String target, Request request, HttpServletRequest MainRequestObject,
                           HttpServletResponse response) throws IOException, ServletException
        {
            try
            {
                //How to get Query String/
                Log.i("Query String", target);

                //URI format
                //http://127.0.0.1:1234/Function/para1/para2

                //Http Request Type: GET/POST/PUT/DELETE
                Log.i("HTTP Verb", MainRequestObject.getMethod());

                BufferedReader in = new BufferedReader(new InputStreamReader(MainRequestObject.getInputStream()));
                String line = null;

                StringBuilder PostedData = new StringBuilder();

                while ((line = in.readLine()) != null)
                {
                    Log.i("ReceivedMessage", line);
                    PostedData.append(line);
                }

                //Http Request Data Type
                Log.i("Posted Data Type", MainRequestObject.getContentType());

                //Http Request Type: GET/POST/PUT/DELETE
                Log.i("Posted Data", PostedData.toString());

                //How To Send Responce Back
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<h1>Hello</h1>");
                ((Request)MainRequestObject).setHandled(true);
            }
            catch (Exception ex)
            {
                Log.i("Error", ex.getMessage());
            }
        }
    };

}

