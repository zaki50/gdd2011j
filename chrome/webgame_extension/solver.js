var cards = document.getElementsByClass('card')
alert("" + cards.length);

var element = document.getElementById('card0');
if (element == null) {
  alert('Card element is not found. Check element id.');
} else {
  var myevent = document.createEvent('MouseEvents');
  myevent.initEvent('click', false, true);
  element.dispatchEvent(myevent);
  alert('Card color is "' + element.style.backgroundColor + '".');
}
