package com.company;

import com.company.dbutils.CRUDUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("C:/Games/MyTassk1/src/players.json");
        Player[] players = (objectMapper.readValue(file, Player[].class));

        List<Player> dop = CRUDUtils.savePlayer(players);
        /*for (int i = 0; i < players.length; i++) {
            System.out.println(players[i]);
        }
        */
        Iterator itr = dop.iterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
       // List<Player> playersBD = CRUDUtils.getPlayerData("SELECT * FROM players");/*, progress, currencies, items*/

    }
}
