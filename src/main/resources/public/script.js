const API_URL = "https://glorious-space-broccoli-97j9ww79xv67f7gj-7070.app.github.dev/api";

window.gameState = {
    gameID: -1,
    deck: [],
    playerHand: [],
    botHand: [],
    communityCards: [],
    playerWins: 0,
    botWins: 0
}

/**
 * @type {HTMLButtonElement}
 */
const playHandButton = document.getElementById("play-hand");
/**
 * @type {HTMLImageElement}
 */
const botCard1Img = document.querySelector("#bot-card-1 > img");
/**
 * @type {HTMLImageElement}
 */
const botCard2Img = document.querySelector("#bot-card-2 > img");
const resultsContainer = document.getElementById("results-container");


function cardImageName(rank, suit) {
    const suits = {
        "♠": "Spades",
        "♥": "Hearts",
        "♣": "Clubs",
        "♦": "Diamonds"
    };
    return `card${suits[suit]}${rank}.png`;
}


async function main() {
    let gamesResponse = await fetch(`${API_URL}/games`, {
        method: "POST"
    });
    let game = await gamesResponse.json();
    window.gameState.gameID = game.gameID;
    gamesResponse = await fetch(`${API_URL}/games/${game.gameID}/deal`, {
        method: "POST"
    });
    game = await gamesResponse.json();
    updateGameState(game);
}

async function updateGameState(game) {
    window.gameState.deck = game.deck.cards;
    window.gameState.playerHand = game.player.hand;
    window.gameState.botHand = game.bot.hand;
    window.gameState.communityCards = game.community;
    window.gameState.gameID = game.gameID;
    updateCardImages();
    return window.gameState;
}

function updateCardImages() {
    document.querySelector("#community-card-1 > img").src = `./assets/cards/${cardImageName(gameState.communityCards[0].rank, gameState.communityCards[0].suit)}`;
    document.querySelector("#community-card-2 > img").src = `./assets/cards/${cardImageName(gameState.communityCards[1].rank, gameState.communityCards[1].suit)}`;
    document.querySelector("#community-card-3 > img").src = `./assets/cards/${cardImageName(gameState.communityCards[2].rank, gameState.communityCards[2].suit)}`;

    document.querySelector("#player-card-1 > img").src = `./assets/cards/${cardImageName(gameState.playerHand[0].rank, gameState.playerHand[0].suit)}`;
    document.querySelector("#player-card-2 > img").src = `./assets/cards/${cardImageName(gameState.playerHand[1].rank, gameState.playerHand[1].suit)}`;
}

main();


playHandButton.addEventListener("click", () => {
    botCard1Img.src = `./assets/cards/${cardImageName(gameState.botHand[0].rank, gameState.botHand[0].suit)}`;
    botCard2Img.src = `./assets/cards/${cardImageName(gameState.botHand[1].rank, gameState.botHand[1].suit)}`;

    playHandButton.style.backgroundColor = "#777777";
    playHandButton.style.borderColor = "#5d5d5d";
    playHandButton.style.color = "rgba(255, 255, 255, 0.5)";
    playHandButton.style.cursor = "default";
    playHandButton.disabled = true;

    findResult();
});

async function findResult() {
    const resultResponse = await fetch(`${API_URL}/games/${gameState.gameID}/evaluate`, {
        method: "GET"
    });
    const result = await resultResponse.json();
    switch (result.winner) {
        case "Player":
            document.getElementById("winner-text").innerText = `You win!`;
            document.getElementById("player-wins").innerText = ++gameState.playerWins;
        break;
        case "Bot":
            document.getElementById("winner-text").innerText = `The bot wins!`;
            document.getElementById("bot-wins").innerText = ++gameState.botWins;
        break;
        case "None":
            document.getElementById("winner-text").innerText = `Tie!`;
    }
    resultsContainer.style.display = "flex";
}

async function nextRound() {
    const gameResponse = await fetch(`${API_URL}/games/${gameState.gameID}/reset`, {
        method: "POST"
    });
    const game = await gameResponse.json();
    updateGameState(game);
    botCard1Img.src = "./assets/cards/cardBack_blue5.png";
    botCard2Img.src = "./assets/cards/cardBack_blue5.png";

    playHandButton.disabled = false;
    playHandButton.removeAttribute("style");
}

document.getElementById("next-round").addEventListener("click", () => {
    resultsContainer.style.display = "none";
    nextRound();
})