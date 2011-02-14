var finished = false;

function update(json) {
    var colors = ["#DCDCDC", "#0000FF", "#228B22", "#B22222", "#191970", "#A0522D", "#20B2AA", "#800080", "#000000"];
    
    $(".square").each(function(i, e) {
        var row = $(e).data("row");
        var column = $(e).data("column");
        var text = json.squares[column][row]
        $(this).text(text);
        if (text == "F") {
            $(this).css("background-color", "#696969");
            $(this).text("⚑");
            $(this).css("color", "#FF0000");
            $(this).css("font-size", "36");
            $(this).css("line-height", "42px");
        } if (text >= "0" && text < "9") {
            $(this).css("background-color", "#DCDCDC");
            $(this).css("color", colors[text]);
            $(this).css("font-size", "20");
            $(this).css("line-height", "30px");
        } else if (text == "*") {
            $(this).css("background-color", "#ff0000");
            $(this).css("color", "black");
            $(this).css("font-size", "40");
            $(this).css("line-height", "42px");
        } else if (text == "") {
            $(this).css("background-color", "#696969");
        }
        
        if (!finished && json.finished) {
            finished = true;
            if (json.won) {
                alert("Congratulations, you WON!");
            } else {
                alert("Loooooooooser!");
            }
        }
    });
}

$(function () {
    var leftClick = 1, middleClick = 2, rightClick = 3;
    
    $(".square").mousedown(function (ev) {
        if (!finished) {
            var button = $(this);
            var row = button.data("row");
            var column = button.data("column");
        
            var action = "";
            switch (ev.which) {
                case leftClick:                
                    action = "reveal";
                    break;
                case middleClick:
                    action = "reveal-neighbours";
                    break;
                case rightClick:
                    action = "flag";
                    break;
                default:
                    alert('You have a strange mouse');
            }
            var url = window.location + "/" + action +"/" + column + "/" + row;
            $.post(url, null, update, "json");
        }
        ev.preventDefault();
        return false;
   });
   
   var url = window.location + "/status";
   $.get(url, null, update, "json");
   
});
