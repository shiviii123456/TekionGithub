package com.example.demo;

import Connection.DatabaseConnection;
import Controller.MatchController;
import com.fasterxml.jackson.core.JsonProcessingException;


public class DemoApplication {

    public static void main(String[] args){

        MatchController matchController = new MatchController();
        matchController.start();

    }

}
