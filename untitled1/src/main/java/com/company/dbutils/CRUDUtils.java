package com.company.dbutils;

import com.company.Currencies;
import com.company.Items;
import com.company.Player;
import com.company.Progresses;

import java.sql.*;
import java.util.*;

public class CRUDUtils {

    // private static String insertPlayers = "INSERT INTO players(playerId, nickname) VALUES (?, ?);";
    private static String[] insertPlayers = new String[]{"INSERT INTO players(playerId, nickname) VALUES (?, ?);",
            "INSERT INTO progress(id, playerId, resourceId, score, maxScore) VALUES (?, ?, ?, ?, ?);",
            "INSERT INTO currencies(id, playerId, resourceId, name, count) VALUES (?, ?, ?, ?, ?);",
            "INSERT INTO items(id, playerId, resourceId, count, level) VALUES (?, ?, ?, ?, ?);"};

    public static List<Player> getPlayerData(String zap) {
        List<Player> player = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(zap)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int playerId = rs.getInt("playerId");
                String nickname = rs.getString("nickname");

                player.add(new Player(playerId, nickname));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return player;
    }

    public static List<Player> savePlayer(Player[] player) {
        List<Player> players = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPlayers[0]);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertPlayers[1]);
             PreparedStatement preparedStatement3 = connection.prepareStatement(insertPlayers[2]);
             PreparedStatement preparedStatement4 = connection.prepareStatement(insertPlayers[3])) {
            for (int i = 0; i < player.length; i++) {
                preparedStatement.setInt(1, player[i].getPlayerId());
                preparedStatement.setString(2, player[i].getNickname());
                Progresses[] prog = player[i].getProgresses();
                if (prog.length != 0) {
                    for (int j = 0; j < prog.length; j++) {
                        preparedStatement2.setInt(1, prog[j].getId());
                        preparedStatement2.setInt(2, prog[j].getPlayerId());
                        preparedStatement2.setInt(3, prog[j].getResourceId());
                        preparedStatement2.setInt(4, prog[j].getScore());
                        preparedStatement2.setInt(5, prog[j].getMaxScore());
                        preparedStatement2.executeUpdate();
                    }
                } else {
                    preparedStatement2.setInt(1, 1);
                    preparedStatement2.setInt(2, player[i].getPlayerId());
                    preparedStatement2.setInt(3, 1);
                    preparedStatement2.setInt(4, 1);
                    preparedStatement2.setInt(5, 1);
                    preparedStatement2.executeUpdate();
                }

                Currencies[] curr = player[i].getCurrencies();
                if (curr.length != 0) {
                    for (int j = 0; j < curr.length; j++) {
                        preparedStatement3.setInt(1, curr[j].getId());
                        preparedStatement3.setInt(2, curr[j].getPlayerId());
                        preparedStatement3.setInt(3, curr[j].getResourceId());
                        preparedStatement3.setString(4, curr[j].getName());
                        preparedStatement3.setInt(5, curr[j].getCount());
                        preparedStatement3.executeUpdate();
                    }
                } else {
                    preparedStatement3.setInt(1, 1);
                    preparedStatement3.setInt(2, player[i].getPlayerId());
                    preparedStatement3.setInt(3, 1);
                    preparedStatement3.setString(4, "1");
                    preparedStatement3.setInt(5, 1);
                    preparedStatement3.executeUpdate();
                }
                Items[] it = player[i].getItems();
                if (it.length != 0) {
                    for (int j = 0; j < it.length; j++) {
                        preparedStatement4.setInt(1, it[j].getId());
                        preparedStatement4.setInt(2, it[j].getPlayerId());
                        preparedStatement4.setInt(3, it[j].getResourceId());
                        preparedStatement4.setInt(4, it[j].getCount());
                        preparedStatement4.setInt(5, it[j].getLevel());
                        preparedStatement4.executeUpdate();
                    }
                } else {
                    preparedStatement4.setInt(1, 1);
                    preparedStatement4.setInt(2, player[i].getPlayerId());
                    preparedStatement4.setInt(3, 1);
                    preparedStatement4.setInt(4, 1);
                    preparedStatement4.setInt(5, 1);
                    preparedStatement4.executeUpdate();
                }
                preparedStatement.executeUpdate();
                /*preparedStatement3.executeUpdate();
                preparedStatement4.executeUpdate();*/
            }

            PreparedStatement allPlayer = connection.prepareStatement("SELECT * FROM players");
            ResultSet rs = allPlayer.executeQuery();
            PreparedStatement allProgress = connection.prepareStatement("SELECT * FROM progress");
            ResultSet rs2 = allProgress.executeQuery();
            PreparedStatement allCurrencies = connection.prepareStatement("SELECT * FROM currencies");
            ResultSet rs3 = allCurrencies.executeQuery();
            PreparedStatement allItems = connection.prepareStatement("SELECT * FROM items");
            ResultSet rs4 = allItems.executeQuery();

            HashMap<Integer, Progresses[]> playerIdAndProgress = new HashMap<>();
            List<Progresses> progres = new ArrayList<>();
            int dopPlayerId2 = 0;
            while (rs2.next()) {
                int playerId2 = rs2.getInt("playerId");
                if (dopPlayerId2 == 0 || dopPlayerId2 == playerId2) {
                    int id = rs2.getInt("id");
                    if (id != 1) {
                        int resourceId = rs2.getInt("resourceId");
                        int score = rs2.getInt("score");
                        int maxScore = rs2.getInt("maxScore");
                        dopPlayerId2 = rs2.getInt("playerId");
                        progres.add(new Progresses(id, playerId2, resourceId, score, maxScore));
                    } else {
                        progres.add(new Progresses());
                    }
                } else {
                    playerIdAndProgress.put(dopPlayerId2, ProgresToArray(progres));
                    progres.clear();
                    int id = rs2.getInt("id");
                    if (id != 1) {
                        int resourceId = rs2.getInt("resourceId");
                        int score = rs2.getInt("score");
                        int maxScore = rs2.getInt("maxScore");
                        progres.add(new Progresses(id, playerId2, resourceId, score, maxScore));
                    }
                    dopPlayerId2 = rs2.getInt("playerId");

                }
                /*int id = rs2.getInt("id");
                int playerId2 = rs2.getInt("playerId");
                int resourceId = rs2.getInt("resourceId");
                int score = rs2.getInt("score");
                int maxScore = rs2.getInt("maxScore");
                progres.add(new Progresses(id, playerId2, resourceId, score, maxScore));*/
            }

            HashMap<Integer, Currencies[]> playerIdAndCurrencies = new HashMap<>();
            List<Currencies> curr = new ArrayList<>();
            int dopPlayerId3 = 0;
            while (rs3.next()) {
                int playerId3 = rs3.getInt("playerId");
                if (dopPlayerId3 == 0 || dopPlayerId3 == playerId3) {
                    int id = rs3.getInt("id");
                    if (id != 1) {
                        int resourceId = rs3.getInt("resourceId");
                        String name = rs3.getString("name");
                        int count = rs3.getInt("count");
                        dopPlayerId3 = rs3.getInt("playerId");
                        curr.add(new Currencies(id, playerId3, resourceId, name, count));
                    } else {
                        curr.add(new Currencies());
                    }
                } else {
                    playerIdAndCurrencies.put(dopPlayerId3, CurrToArray(curr));
                    curr.clear();
                    int id = rs3.getInt("id");
                    if (id != 1) {
                        int resourceId = rs3.getInt("resourceId");
                        String name = rs3.getString("name");
                        int count = rs3.getInt("count");
                        curr.add(new Currencies(id, playerId3, resourceId, name, count));
                    }
                    dopPlayerId3 = rs3.getInt("playerId");
                }
            }

            HashMap<Integer, Items[]> playerIdAndItems = new HashMap<>();
            List<Items> item = new ArrayList<>();
            int dopPlayerId4 = 0;
            while (rs4.next()) {
                int playerId4 = rs4.getInt("playerId");
                if (dopPlayerId4 == 0 || dopPlayerId4 == playerId4) {
                    int id = rs4.getInt("id");
                    if (id != 1) {
                        int resourceId = rs4.getInt("resourceId");
                        int count = rs4.getInt("count");
                        int level = rs4.getInt("level");
                        dopPlayerId4 = rs4.getInt("playerId");
                        item.add(new Items(id, playerId4, resourceId, count, level));
                    } else {
                        item.add(new Items());
                    }
                } else {
                    playerIdAndItems.put(dopPlayerId4, ItemToArray(item));
                    item.clear();
                    int id = rs4.getInt("id");
                    if (id != 1) {
                        int resourceId = rs4.getInt("resourceId");
                        int count = rs4.getInt("count");
                        int level = rs4.getInt("level");
                        item.add(new Items(id, playerId4, resourceId, count, level));
                    }
                    dopPlayerId4 = rs4.getInt("playerId");
                }
            }
            while (rs.next()) {
                int playerId = rs.getInt("playerId");
                String nickname = rs.getString("nickname");
                Progresses[] prog = playerIdAndProgress.get(playerId);
                Currencies[] currr = playerIdAndCurrencies.get(playerId);
                Items[] itemm = playerIdAndItems.get(playerId);
                players.add(new Player(playerId, nickname, prog, currr, itemm));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return players;
    }

    public static Progresses[] ProgresToArray(List<Progresses> list) {
        Progresses[] pl1 = new Progresses[list.size()];
        Iterator itr = list.iterator();
        int i = 0;
        while (itr.hasNext()) {
            pl1[i] = (Progresses) itr.next();
            i++;
        }
        return pl1;
    }

    public static Currencies[] CurrToArray(List<Currencies> list) {
        Currencies[] pl1 = new Currencies[list.size()];
        Iterator itr = list.iterator();
        int i = 0;
        while (itr.hasNext()) {
            pl1[i] = (Currencies) itr.next();
            i++;
        }
        return pl1;
    }

    public static Items[] ItemToArray(List<Items> list) {
        Items[] pl1 = new Items[list.size()];
        Iterator itr = list.iterator();
        int i = 0;
        while (itr.hasNext()) {
            pl1[i] = (Items) itr.next();
            i++;
        }
        return pl1;
    }
}






















