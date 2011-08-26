var cards = document.getElementsByClassName('card');

// color value to node
var colorsToNode = {};

var myevent = document.createEvent('MouseEvents');
myevent.initEvent('click', false, true);

var prevColor;
var isFirst = false;
for (var index = 0; index < cards.length; index++) {
    var card = cards[index];
    card.dispatchEvent(myevent);
    isFirst = !isFirst;
    var color = card.style.backgroundColor;
    var pair = colorsToNode[color];
    if (!pair) {
        // 相方が不明の場合は連想配列にいれて後で処理する
        colorsToNode[color] = card;
        continue;
    }
    // ここからは相方が見つかった場合
    if (isFirst) {
        // もう一回開けるので、相方を開く
        pair.dispatchEvent(myevent);
        isFirst = !isFirst;
    } else {
        // 新たに両方を開く。たまたま直前のカードとペアになっている可能性があるが気にしない
        pair.dispatchEvent(myevent);
        card.dispatchEvent(myevent);
    }
}
