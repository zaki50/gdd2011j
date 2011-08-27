var cards = document.getElementsByClassName('card');
var colorsToNode = {};
var clickEvent = document.createEvent('MouseEvents');
clickEvent.initEvent('click', false, true);
var isFirst = false;
var click = function(card) {
    card.dispatchEvent(clickEvent);
    isFirst = !isFirst;
}

for (var index = 0; index < cards.length; index++) {
    var card = cards[index];
    click(card);
    var color = card.style.backgroundColor;
    var pair = colorsToNode[color];
    if (!pair) { // 相方が不明の場合
        colorsToNode[color] = card;
        continue;
    }
    // ここからは相方が見つかった場合
    if (isFirst) {
        click(pair);
    } else {
        // たまたま直前のカードとペアになっている可能性があるが気にしない
        click(pair);
        click(card);
    }
}
