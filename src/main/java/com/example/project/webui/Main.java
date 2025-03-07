package com.example.project.webui;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

import com.example.project.Card;
import com.example.project.Deck;
import com.example.project.Game;
import com.example.project.Player;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.http.Context;


public class Main {
    private static Javalin app;
    private static ArrayList<FullGame> games;
    private static ObjectMapper objectMapper;

    public static void main(String[] args) {
        objectMapper = new ObjectMapper();
        games = new ArrayList<>();

        app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        })
            .error(404, ctx -> {
                if (!ctx.path().startsWith("/api/games/")) {
                    ctx.status(404)
                        .json(objectMapper.valueToTree(Map.of("error", "Endpoint not found")));
                }
            });
             
        addAPIEndpoints();
        app.start(7070);
    }

    private static void addAPIEndpoints() {
        app.post("/api/games", ctx -> {
            FullGame game = new FullGame();
            games.add(game);
            ctx.status(201).json(objectMapper.valueToTree(game));
        })
            .get("/api/games", ctx -> {
                ctx.status(200).json(objectMapper.valueToTree(games));
            })
            .get("/api/games/{id}", ctx -> {
                FullGame game = findGame(ctx);
                if (game != null) {
                    ctx.status(200).json(objectMapper.valueToTree(game));
                }
            })
            .post("/api/games/{id}/deal", ctx -> {
                FullGame game = findGame(ctx);
                if (game != null) {
                    game.dealCards();
                    ctx.status(200).json(objectMapper.valueToTree(game));
                }
            })
            .get("/api/games/{id}/hand-ranks", ctx -> {
                FullGame game = findGame(ctx);
                if (game != null) {
                    ArrayList<Card> community = game.getCommunity();
                    ctx.status(200).json(objectMapper.valueToTree(Map.of(
                        "player", game.getPlayer().playHand(community),
                        "bot", game.getBot().playHand(community)
                    )));
                }
            })
            .get("/api/games/{id}/evaluate", ctx -> {
                FullGame game = findGame(ctx);
                if (game != null) {
                    Player player = game.getPlayer();
                    Player bot = game.getBot();
                    String playerHand = player.playHand(game.getCommunity());
                    String botHand = bot.playHand(game.getCommunity());
                    String result = Game.determineWinner(player, bot, playerHand, botHand, game.getCommunity());
                    if (result.startsWith("Error: ")) {
                        ctx.status(500)
                            .json(objectMapper.valueToTree(Map.of("error", result.substring(7))));
                    } else {
                        Map<String, String> response = null;
                        if (result.equals("Player 1 wins!")) {
                            response = Map.of(
                                "winner", "Player"
                            );
                        } else if (result.equals("Player 2 wins!")) {
                            response = Map.of(
                                "winner", "Bot"
                            );
                        } else {
                            response = Map.of(
                                "winner", "None"
                            );
                        }
                        ctx.status(200)
                            .json(objectMapper.valueToTree(response));
                    }
                }
            })
            .post("/api/games/{id}/reset", ctx -> {
                FullGame game = findGame(ctx);
                if (game != null) {
                    game.getPlayer().getHand().clear();
                    game.getPlayer().getAllCards().clear();
                    game.getBot().getHand().clear();
                    game.getBot().getAllCards().clear();
                    game.getCommunity().clear();
                    game.getDeck().initializeDeck();
                    game.getDeck().shuffleDeck();
                    game.dealCards();
                    ctx.status(200)
                        .json(objectMapper.valueToTree(game));
                }
            });
    }

    // Find the game based on the game ID from the ctx, returning null with a 404/400 error if invalid
    private static FullGame findGame(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if (id >= 0 && id < games.size()) {
                return games.get(id);
            } else {
                ctx.status(404)
                    .json(objectMapper.valueToTree(Map.of("error", "Game not found")));
                return null;
            }
        } catch (NumberFormatException e) {
            ctx.status(400)
                .json(objectMapper.valueToTree(Map.of("error", "Invalid game ID")));
            return null;
        }
    }
}
