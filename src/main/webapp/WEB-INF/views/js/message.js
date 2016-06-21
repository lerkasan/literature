function showMessage() {
    var msg = ${"message"};
    if ((msg != null) && (msg != '')) {
	alert(msg);
    }
} 

window.onload = showMessage();